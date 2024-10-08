package store.ggun.alarm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import store.ggun.alarm.domain.model.RoleModel;

import java.util.List;

@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private String id;
    private String name;
    private String username ;
    private String email;
    private String password ;
    private String profile;
    private List<RoleModel> roles ;
    @Builder.Default
    private Boolean enabled = false;
}