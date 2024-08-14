package store.ggun.user.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
@Builder
public class ArticleDto2 {
    private Long id;
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;
    private String answer;

    @QueryProjection
    public ArticleDto2(Long id, String title, String content, Long writerId, Long boardId, String answer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.boardId = boardId;
        this.answer = answer;
    }
}