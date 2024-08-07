package store.ggun.alarm.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.model.Messenger;
import store.ggun.alarm.domain.model.UserModel;
import store.ggun.alarm.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl {


    private final UserRepository userRepository;

    private final TokenServiceImpl tokenServiceImpl;


    public Flux<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<UserModel> getUserDetailByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<UserModel> getUserDetailById(String id) {
        return userRepository.findById(id);
    }

    public Mono<Messenger> addUser(UserModel user) {
        return userRepository.save(user).flatMap(i -> Mono.just(Messenger.builder().message("SUCCESS").build()))
                .switchIfEmpty(Mono.just(Messenger.builder().message("FAILURE").build()))
                ;
    }

    public Mono<UserModel> updateUser(String id, UserModel user) {
        return userRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optionalUser -> {
                    if (optionalUser.isPresent()) {
                        user.setId((id));
                        return userRepository.save(user);
                    }

                    return Mono.empty();
                });
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(id);
    }

    public Mono<Void> deleteAllUsers() {
        return userRepository.deleteAll();
    }

    public Flux<UserModel> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }
}