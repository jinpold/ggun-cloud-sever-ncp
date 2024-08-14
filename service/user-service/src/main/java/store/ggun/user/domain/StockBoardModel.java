package store.ggun.user.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Setter
@Entity(name="StockBoards")
@Builder
@AllArgsConstructor
public class StockBoardModel extends BaseEntity {
    @Id
    @Column(name = "boards_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String content;
    private String description;

    @OneToMany(mappedBy = "stockBoards", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StockArticleModel> StockArticles;
}
