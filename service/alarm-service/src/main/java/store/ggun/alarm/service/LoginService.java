package store.ggun.alarm.service;

import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.dto.LoginDto;
import store.ggun.alarm.domain.dto.PrincipalUserDetails;

public interface LoginService {
    Mono<PrincipalUserDetails> login(LoginDto user);
}
