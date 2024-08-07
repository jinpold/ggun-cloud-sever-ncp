package store.ggun.admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.ggun.admin.domain.vo.Registration;
import store.ggun.admin.domain.vo.Role;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String password;
    private List<Role> role;
    private AdminDto Admin;
    private Registration registration;
}


