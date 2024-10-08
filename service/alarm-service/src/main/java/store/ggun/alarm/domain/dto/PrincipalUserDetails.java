package store.ggun.alarm.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import store.ggun.alarm.domain.model.UserModel;

import java.io.Serializable;
import java.util.Map;


@Getter
@NoArgsConstructor
@Component
@Setter
public class PrincipalUserDetails implements Serializable {
    private UserModel user;
    private Map<String, Object> attributes;

    public PrincipalUserDetails(UserModel admin) {
        this.user = admin;
    }

    public PrincipalUserDetails(UserModel admin, Map<String, Object> attributes) {
        this.user = admin;
        this.attributes = attributes;
    }

}
