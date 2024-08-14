package store.ggun.user.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name="StockComment")
@Builder
public class StockCommentModel extends BaseEntity{
    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserModel writerId;
    @ManyToOne
    @JoinColumn(name = "comments")
    private StockArticleModel stockArticles;
}
