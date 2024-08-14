package store.ggun.alarm.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.dto.LoginDto;
import store.ggun.alarm.domain.dto.PrincipalUserDetails;
import store.ggun.alarm.domain.model.UserModel;
import store.ggun.alarm.domain.vo.ExceptionStatus;
import store.ggun.alarm.domain.vo.Role;
import store.ggun.alarm.exception.CustomLoginException;
import store.ggun.alarm.repository.UserRepository;
import store.ggun.alarm.service.LoginService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    public Mono<PrincipalUserDetails> login(LoginDto userModel) {
        log.info("user: AuthService {}", userModel.getEmail());
        return userRepository.findByEmail(userModel.getEmail())
                .switchIfEmpty(Mono.error(new CustomLoginException(ExceptionStatus.UNAUTHORIZED, "존재하지 않는 사용자입니다.")))
                .flatMap(existingUser -> {
                    if (existingUser.getPassword().equals(userModel.getPassword()) && existingUser.getEnabled()) {
                        return Mono.just(new PrincipalUserDetails(UserModel.builder()
                                .id(existingUser.getId())
                                .name(existingUser.getName())
                                .email(existingUser.getEmail())
                                .roles(List.of(Role.ROLE_ADMIN))
                                .build()));
                    } else {
                        return Mono.error(new CustomLoginException(ExceptionStatus.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다."));
                    }
                }).log("adminService login completed {}");
    }
}
