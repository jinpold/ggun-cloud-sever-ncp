package store.ggun.admin.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.dto.LoginDto;
import store.ggun.admin.domain.dto.PrincipalUserDetails;
import store.ggun.admin.domain.model.AdminModel;
import store.ggun.admin.domain.model.UserModel;
import store.ggun.admin.domain.vo.ExceptionStatus;
import store.ggun.admin.domain.vo.Role;
import store.ggun.admin.handler.LoginException;
import store.ggun.admin.repository.jpa.AdminRepository;
import store.ggun.admin.service.AdminLoginService;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminLoginServiceImpl implements AdminLoginService {
    private final AdminRepository adminRepository;

    @Override
    public PrincipalUserDetails login(LoginDto dto) {
        // username으로 관리자 검색 / email로 수정 할 수도 있음 (Oauth2 & local login 구분
        AdminModel admin = adminRepository.findAdminByUsername(dto.getUsername())
                .orElseThrow(() -> new LoginException(ExceptionStatus.UNAUTHORIZED, "존재하지 않는 관리자입니다."));

        log.info("Username: {}, Password: {}", dto.getUsername(), dto.getPassword());

        // 비밀번호 일치 여부 확인
        if (admin.getPassword().equals(dto.getPassword())) {
            log.info("admin.getPassword: {}, dto.Password: {}", admin.getPassword(), dto.getPassword());

            // UserDetails 객체 생성
            PrincipalUserDetails userDetails = new PrincipalUserDetails(UserModel.builder()
                    .id(String.valueOf(admin.getId()))
                    .name(admin.getName())
                    .username(admin.getUsername())
                    .email(admin.getEmail())
                    .roles(List.of(Role.ROLE_ADMIN))
                    .build());

            log.info("PrincipalUserDetails: {}", userDetails);
            return userDetails;
        } else {
            throw new LoginException(ExceptionStatus.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다.");
        }
    }
}
