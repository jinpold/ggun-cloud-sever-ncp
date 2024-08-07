package store.ggun.admin.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import store.ggun.admin.domain.model.UserModel;

import java.util.Map;

@Getter
@NoArgsConstructor
@Component
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrincipalUserDetails {

    @JsonProperty("user")
    private UserModel user;

    @JsonProperty("attributes")
    private Map<String, Object> attributes;

    public PrincipalUserDetails(UserModel user) {
        this.user = user;
    }

    public PrincipalUserDetails(UserModel user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "PrincipalUserDetails{" +
                "user=" + user +
                ", attributes=" + attributes +
                '}';
    }
}
