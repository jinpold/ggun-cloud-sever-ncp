package store.ggun.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import store.ggun.gateway.domain.vo.ExceptionStatus;
import store.ggun.gateway.domain.vo.Role;
import store.ggun.gateway.exception.GatewayException;
import store.ggun.gateway.service.provider.JwtTokenProvider;

import java.util.List;
import java.util.Objects;


@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>{

    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationHeaderFilter(JwtTokenProvider jwtTokenProvider){
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Data
    public static class Config {
        private List<Role> roles;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) ->
                Mono.just(exchange)
                        .filter(i -> exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                        .flatMap(i -> Mono.just(Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION))))
                        .flatMap(i -> Mono.just(i.get(0)))
                        .switchIfEmpty(Mono.error(new GatewayException(ExceptionStatus.UNAUTHORIZED,"No Authorization Header")))
                        .filterWhen(i -> Mono.just(i.startsWith("Bearer ")))
                        .flatMap(i -> Mono.just(jwtTokenProvider.removeBearer(i)))
                        .filterWhen(i -> Mono.just(jwtTokenProvider.isTokenValid(i, false)))
                        .switchIfEmpty(Mono.error(new GatewayException(ExceptionStatus.UNAUTHORIZED,"Invalid Token")))
                        .flatMap(i -> {
                            List<Role> roles = jwtTokenProvider.extractRoles(i).stream().map(Role::valueOf).toList();
                            if (roles.stream().noneMatch(role -> config.getRoles().contains(role))) {
                                return Mono.error(new GatewayException(ExceptionStatus.NO_PERMISSION, "No Permission"));
                            }
                            String userId = jwtTokenProvider.extractId(i);
                            ServerHttpRequest request = exchange.getRequest().mutate()
                                    .header("id", userId)  // ID를 헤더에 추가
                                    .build();

                            log.info("g확인 : {} ", request.getHeaders().get("id"));
                            return chain.filter(exchange.mutate().request(request).build());
                        })
                        .onErrorResume(GatewayException.class, e -> onError(exchange, HttpStatusCode.valueOf(e.getStatus().getStatus().value()), e.getMessage()))
                        .log()
        );
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatusCode httpStatusCode, String message){
        log.error("Error Occured : {}, {}, {}", exchange.getRequest().getURI(), httpStatusCode, message);
        exchange.getResponse().setStatusCode(httpStatusCode);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(message.getBytes());
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}