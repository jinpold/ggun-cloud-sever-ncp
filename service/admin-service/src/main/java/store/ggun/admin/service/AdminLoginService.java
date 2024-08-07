package store.ggun.admin.service;

import store.ggun.admin.domain.dto.LoginDto;
import store.ggun.admin.domain.dto.PrincipalUserDetails;


public interface AdminLoginService {

    PrincipalUserDetails login(LoginDto dto);
}
