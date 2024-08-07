package store.ggun.admin.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import store.ggun.admin.domain.model.AdminArticleModel;
import store.ggun.admin.domain.vo.Role;

import java.util.List;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class AdminDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String number;
    private String department;
    private String position;
    private String job;
    @NotNull
    private String email;
    private String phone;
    @NotNull
    private String profile;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String token;
    private List<AdminArticleModel> adminArticleModels;

}