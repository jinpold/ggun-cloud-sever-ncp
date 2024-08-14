package store.ggun.gateway.domain.model;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class PrincipalUserDetails implements UserDetails, OAuth2User {
    private UserModel user;
    private Map<String, Object> attributes;

    public PrincipalUserDetails(UserModel user) {
        this.user = user;
    }

    public PrincipalUserDetails(UserModel user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        if (user != null) {
            return attributes.get(user.getRegistration().name()).toString();
        }
        return null; // user가 null일 경우 null 반환
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user != null) {
            log.info("getAuthorities: {}", user.getRoles());
            return user.getRoles().stream().map(i -> new SimpleGrantedAuthority(i.name())).toList();
        }
        return List.of(); // user가 null일 경우 빈 리스트 반환
    }
    @Override
    public String getPassword() {
        return null;
    }
    @Override
    public String getUsername() {
        if (user != null) {
            return user.getUsername();
        }
        return null; // user가 null일 경우 null 반환
    }
}