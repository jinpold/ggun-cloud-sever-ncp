package store.ggun.user.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayUser {
    private Long id;
    private String username;
    private String email;
    private String name;
    private List<Role> roles;
    private Registration registration;
}