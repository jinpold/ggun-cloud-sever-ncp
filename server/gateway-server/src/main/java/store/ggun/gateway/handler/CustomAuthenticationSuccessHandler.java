package store.ggun.gateway.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import store.ggun.gateway.domain.dto.MessengerDto;
import store.ggun.gateway.domain.model.PrincipalUserDetails;
import store.ggun.gateway.service.provider.JwtTokenProvider;
import java.net.URI;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes;
        String email;
        if(principal.getAttribute("email")!=null) {
            attributes = principal.getAttributes();
            email = (String)attributes.get("email");
        } else if (principal.getAttribute("response")!=null){
            attributes = principal.getAttribute("response");
            email = (String) attributes.get("email");
        } else {
            Map<String, Object> kakaoAtribbutes = principal.getAttributes();
            email = kakaoAtribbutes.get("id").toString();
        }
        return webClient.post()
                .uri("lb://user-service/auth/oauth")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(email)
                .retrieve()
                .bodyToMono(PrincipalUserDetails.class)
                .defaultIfEmpty(new PrincipalUserDetails(null))
                .flatMap(response -> {
                    ServerHttpResponse serverResponse = webFilterExchange.getExchange().getResponse();
                    serverResponse.setStatusCode(HttpStatus.SEE_OTHER);
                    serverResponse.getHeaders().setLocation(URI.create("http://localhost:3000"));
                    if (response.getUsername() == null) {
                        MessengerDto messageDTO = MessengerDto.builder().message(email).build();
                        serverResponse.setStatusCode(HttpStatus.OK);
                        serverResponse.setStatusCode(HttpStatus.SEE_OTHER);
                        serverResponse.getHeaders().setLocation(URI.create("http://localhost:3000/join"));
                        return serverResponse.writeWith(Mono.just(serverResponse.bufferFactory().wrap(writeValueAsBytes(messageDTO))));
                    }
                    return jwtTokenProvider.generateToken(response, false)
                            .flatMap(accessToken -> {
                                serverResponse.getCookies().add("accessToken", ResponseCookie.from("accessToken", accessToken)
                                        .path("/")
                                        .maxAge(jwtTokenProvider.getAccessTokenExpired())
                                        .httpOnly(true)
                                                .secure(true)
                                                .domain(".ggun.store")
                                        .build());
                                return jwtTokenProvider.generateToken(response, true);
                            })
                            .flatMap(refreshToken -> {
                                serverResponse.getCookies().add("refreshToken", ResponseCookie.from("refreshToken", refreshToken)
                                        .path("/")
                                        .maxAge(jwtTokenProvider.getRefreshTokenExpired())
                                        .httpOnly(true)
                                                .secure(true)
                                                .domain(".ggun.store")
                                        .build());

                                MessengerDto messageDTO = MessengerDto.builder().message("로그인 성공").build();
                                return Mono.just(serverResponse.bufferFactory().wrap(writeValueAsBytes(messageDTO)));
                            })
                            .flatMap(buffer -> {
                                return serverResponse.writeWith(Mono.just(buffer))
                                        .then(Mono.defer(() -> {
                                            return serverResponse.setComplete();
                                        }));
                            });
                });
    }




    private byte[] writeValueAsBytes(MessengerDto messengerDto) {
        try {
            return objectMapper.writeValueAsBytes(messengerDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing response", e);
        }
    }
}