package store.ggun.admin.domain.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString(exclude = {"id"})
@Entity(name="adminBoards")

public class AdminBoardModel extends AdminBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String description;

    @OneToMany(mappedBy = "adminBoardModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AdminArticleModel> adminArticleModel;

    @Builder(builderMethodName = "builder")
    public AdminBoardModel(Long id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }
}
