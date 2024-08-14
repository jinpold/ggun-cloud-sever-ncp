package store.ggun.user.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name="articles")
@Builder
public class ArticleModel extends BaseEntity {
    @Id
    @Column(name = "articles_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserModel writerId;
    @ManyToOne
    @JoinColumn(name = "boards_id")
    private BoardModel boardId;
    private String answer;
}
