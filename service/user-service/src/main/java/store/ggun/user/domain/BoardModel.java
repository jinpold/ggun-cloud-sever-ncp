package store.ggun.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity(name="boards")
@Builder
@AllArgsConstructor
public class BoardModel extends BaseEntity {
    @Id
    @Column(name = "boards_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String description;

    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ArticleModel> articles;
}
