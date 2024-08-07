package store.ggun.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name="users")
@Builder
public class UserModel extends BaseEntity implements UserDetails, Serializable {
    @Id
    @Column(name = "users_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String age;
    private String sex;
    private String email;
    private String ssnF;
    private String ssnS;
    private String address;
    private String phone;
    private Long asset;
    private String color;
    private String investmentPropensity;
    private String token;
    private String role;

    @OneToMany(mappedBy = "writerId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleModel> articles;

    public UserModel(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String UserModelEmail(){
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role));
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}