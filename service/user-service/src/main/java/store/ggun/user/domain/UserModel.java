package store.ggun.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import store.ggun.user.domain.vo.Registration;
import store.ggun.user.domain.vo.Role;

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
public class UserModel extends BaseEntity {
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
    @Enumerated(EnumType.STRING)
    private Role roles;
    private Registration registration;

    @OneToMany(mappedBy = "writerId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleModel> articles;

    @OneToMany(mappedBy = "writerId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StockCommentModel> stockComments;

    @OneToMany(mappedBy = "writerId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StockArticleModel> stockArticles;

    public UserModel(Long id, Role roles) {
        this.id = id;
        this.roles = roles;
    }

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String UserModelEmail(){
        return this.email;
    }

}