package store.ggun.gateway.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
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
import store.ggun.gateway.domain.model.UserModel;
import store.ggun.gateway.service.provider.JwtTokenProvider;
import java.net.URI;
import java.util.Map;


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
            attributes = principal.getAttribute("email");
            email = (String)attributes.get("email");
        } else if (principal.getAttribute("User Attributes")!=null){
            Map<String,Long> kakaoAtribbutes = principal.getAttribute("id");
            email = kakaoAtribbutes.get("id").toString();
        } else {
            attributes = principal.getAttribute("response");
            email = (String) attributes.get("email");
        }
        return webClient.post()
                .uri("lb://user-service/auth/oauth")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(email)
                .retrieve()
                .bodyToMono(UserModel.class)
                .flatMap(userModel -> {
                    PrincipalUserDetails principalUserDetails = new PrincipalUserDetails(userModel);
                    return Mono.just(principalUserDetails);
                })
                .flatMap(response -> {
                    ServerHttpResponse serverResponse = webFilterExchange.getExchange().getResponse();
                    URI redirectUri = URI.create("http://localhost:3000/join");
                    if (response != null) {
                        String accessToken = String.valueOf(jwtTokenProvider.generateToken(response, false));
                        String refreshToken = String.valueOf(jwtTokenProvider.generateToken(response, true));
                        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                                .path("/")
                                .maxAge(jwtTokenProvider.getAccessTokenExpired())
                                .httpOnly(true)
                                .build();
                        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                                .path("/")
                                .maxAge(jwtTokenProvider.getRefreshTokenExpired())
                                .httpOnly(true)
                                .build();
                        redirectUri = URI.create("http://localhost:3000");
                        serverResponse.addCookie(accessTokenCookie);
                        serverResponse.addCookie(refreshTokenCookie);
                    }
                    serverResponse.setStatusCode(HttpStatus.SEE_OTHER);
                    serverResponse.getHeaders().setLocation(redirectUri);
                    return serverResponse.setComplete();
                });
    }


    private byte[] writeValueAsBytes(MessengerDto messengerDto) {
        try {
            return objectMapper.writeValueAsBytes(messengerDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}