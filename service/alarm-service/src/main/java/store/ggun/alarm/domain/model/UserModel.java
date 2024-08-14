package store.ggun.alarm.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import store.ggun.alarm.domain.vo.Role;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class UserModel {

    @Id
    private String id ;
    private String firstName ;
    private String lastName ;
    private String username ;
    private String name;
    private String email;
    private String password ;
    private String profile;
    private Boolean enabled;
    List <RoleModel> role ;
    List <Role> roles;

}