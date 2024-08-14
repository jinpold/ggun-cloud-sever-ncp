//package store.ggun.gateway.service.impl;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class PrincipalOauthUserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {
//    private final WebClient webClient;
//    public String client;
//
//    @Override
//    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        log.info("여기는 loaduser입니다 {}",userRequest.getClientRegistration().getClientName());
//        log.info("여기는 loaduser입니다 {}",userRequest.getClientRegistration().getClientId());
//        log.info("여기는 loaduser입니다 {}",userRequest.getAccessToken());
//        log.info("여기는 loaduser입니다 {}",userRequest.getAdditionalParameters());
//
//        this.client = userRequest.getClientRegistration().getClientName();
//        return null;
//    }
//}
