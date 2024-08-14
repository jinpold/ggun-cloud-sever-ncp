package store.ggun.user.domain;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String writerId;
    private Long boardId;
    private String answer;

    @QueryProjection
    public ArticleDto(Long id, String title, String content, String writerId, Long boardId, String answer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.boardId = boardId;
        this.answer = answer;
    }
}
