package store.ggun.admin.domain.model;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString(exclude = {"id"})
@Entity(name = "adminArticles")
@AllArgsConstructor
@Builder

public class AdminArticleModel extends AdminBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private AdminModel writer;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private AdminBoardModel adminBoardModel;

    public static AdminArticleModel of(String title, String content){
        AdminArticleModel adminArticleModel = new AdminArticleModel();
        adminArticleModel.title = title;
        adminArticleModel.content = content;
        return adminArticleModel;
    }
}
