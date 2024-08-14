//package store.ggun.chat.serviceImpl;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//import store.ggun.chat.repository.TokenRepository;
//
//import java.time.Instant;
//import java.util.Date;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class TokenServiceImpl {
//
//    private final TokenRepository tokenRepository;
//
//    public void saveRefrshToken(String email, String refreshToken, long refreshTokenExpiration){
//
//        TokenModel token = TokenModel.builder()
//                .email(email)
//                .refreshToken(refreshToken)
//                .expiration(Date.from(Instant.now().plusSeconds(refreshTokenExpiration)))
//                .build();
//        tokenRepository.save(token)
//                .flatMap(i -> Mono.just(i.getRefreshToken())).subscribe();
//
//    }
//}
