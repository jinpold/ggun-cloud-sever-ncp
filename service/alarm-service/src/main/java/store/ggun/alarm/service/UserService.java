package store.ggun.alarm.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.dto.UserDto;
import store.ggun.alarm.domain.model.UserModel;

public interface UserService {
    Mono<UserModel> save(UserDto userDto);
    Mono<UserModel> findById(String id);
    Flux<UserModel> findAll();
    Mono<UserModel> update(String id,UserDto userDto);
    Mono<Void> deleteById(String id);
    Mono<String> switching(String id);
    Flux<UserModel> findAllByEnabled();
    Mono<Long> countAdminsEnabledFalse();
    Flux<UserDto> searchByName(String keyword);
}
