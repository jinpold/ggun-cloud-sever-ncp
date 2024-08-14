package store.ggun.user.domain;

import lombok.Getter;
import store.ggun.user.domain.vo.GatewayUser;

import java.util.Map;

@Getter
public class PrincipalUserDetails {

    private GatewayUser user;

    private Map<String, Object> attributes;

    public PrincipalUserDetails(GatewayUser user) {
        this.user = user;
    }

    public PrincipalUserDetails(GatewayUser user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }
}
