package store.ggun.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name="StockArticles")
@Builder
public class StockArticleModel extends BaseEntity {
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
    private StockBoardModel stockBoards;

    @OneToMany(mappedBy = "stockArticles", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StockCommentModel> comments;
}