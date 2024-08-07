package store.ggun.admin.service;

import store.ggun.admin.domain.dto.LoginDto;
import store.ggun.admin.domain.dto.PrincipalUserDetails;


public interface LoginService {

    PrincipalUserDetails login(LoginDto dto);
}
