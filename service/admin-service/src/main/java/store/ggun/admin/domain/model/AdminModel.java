package store.ggun.admin.domain.model;
import jakarta.persistence.*;
import lombok.*;
import store.ggun.admin.domain.vo.Role;

import java.util.List;




@Entity(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@ToString(exclude = {"id"})
@AllArgsConstructor
public class AdminModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String number;
    private String department;
    private String position;
    private String job;
    private String email;
    private String phone;
    private String token;
    private String profile;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArticleModel> articleModel;

    public void setPassword(String password) {
        this.password = password;
    }
}