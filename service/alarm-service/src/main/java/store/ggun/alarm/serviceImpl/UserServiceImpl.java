package store.ggun.alarm.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.dto.UserDto;
import store.ggun.alarm.domain.model.UserModel;
import store.ggun.alarm.repository.UserRepository;
import store.ggun.alarm.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDto userDto;

    public Mono<UserModel> save(UserDto userDto) {
        return userRepository.save(UserModel.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .role(userDto.getRoles())
                .enabled(userDto.getEnabled())
                .build());
    }


    @Override
    public Mono<UserModel> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<UserModel> update(String id, UserDto userDto) {
        return userRepository.findById(id)
                .map(admin -> {
                    admin.setEmail(userDto.getEmail());
                    admin.setPassword(userDto.getPassword());
                    admin.setName(userDto.getName());
                    return admin;
                })
                .flatMap(userRepository::save);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<String> switching(String id) {
        return userRepository.findById(id)
                .map(admin -> {
                    admin.setEnabled(!admin.getEnabled());
                    return admin;
                })
                .flatMap(userRepository::save)
                .flatMap(admin -> Mono.just("Switch Success"))
                .switchIfEmpty(Mono.just("Switch Failure"));
    }


    @Override
    public Flux<UserModel> findAllByEnabled() {
        return userRepository.findAll().filter(admin -> !admin.getEnabled());
    }

    @Override
    public Mono<Long> countAdminsEnabledFalse() {
        return userRepository.findAll().filter(admin -> !admin.getEnabled()).count();
    }

    @Override
    public Flux<UserDto> searchByName(String keyword) {
        return userRepository.findAll()
                .filter(userModel -> userModel.getName().contains(keyword) || userModel.getEmail().contains(keyword))
                .filter(userModel -> userModel.getEnabled())
                .flatMap(userModel -> Flux.just(userDto.builder()
                        .email(userModel.getEmail())
                        .name(userModel.getName())
                        .roles(userModel.getRole())
                        .build()));
    }
}