package store.ggun.chat.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.chat.domain.Messenger;
import store.ggun.chat.domain.UserModel;
import store.ggun.chat.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {

//    private final TokenProvider jwtProvider;
//    private final TokenServiceImpl tokenService;
    @Value("${jwt.expiration.access}")
    private long accessTokenExpiration;
    @Value("${jwt.expiration.refresh}")
    private long refreshTokenExpiration;

    private final UserRepository userRepository;

    public Flux<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<UserModel> getUserDetailByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<UserModel> getUserDetailById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<Messenger> addUser(UserModel userModel) {
        return userRepository.save(userModel).flatMap(i -> Mono.just(Messenger.builder().message("SUCCESS").build()))
                .switchIfEmpty(Mono.just(Messenger.builder().message("FAILURE").build()))
                ;
    }

    public Mono<UserModel> updateUser(Long id, UserModel userModel) {
        return userRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optionalUser -> {
                    if (optionalUser.isPresent()) {
                        userModel.setUserId(id);
                        return userRepository.save(userModel);
                    }

                    return Mono.empty();
                });
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }

    public Mono<Void> deleteAllUsers() {
        return userRepository.deleteAll();
    }

    public Flux<UserModel> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

//    public Mono<Messenger> login(UserModel userModel) {
//        var accessToken = jwtProvider.generateToken(null, userModel, "accessToken");
//        var refreshToken = jwtProvider.generateToken(null, userModel, "refreshToken");
//        tokenService.saveRefrshToken(userModel.getEmail(), refreshToken, refreshTokenExpiration);
//        return userRepository.findByEmail(userModel.getEmail())
//                .filter(i -> i.getPassword().equals(userModel.getPassword()))
//                .map(i -> UserDto.builder().email(i.getEmail()).firstName(i.getFirstName()).lastName(i.getLastName()).build())
//                .map(i -> Messenger.builder().message("SUCCESS").data(i)
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .accessTokenExpire(accessTokenExpiration)
//                        .refreshTokenExpire(refreshTokenExpiration)
//                        .build())

//                ;
//    }

}