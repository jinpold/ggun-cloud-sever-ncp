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
public class StockArticleDto {
    private Long id;
    private String title;
    private String content;
    private String writerId;
    private Long stockBoards;

    @QueryProjection
    public StockArticleDto(Long id, String title, String content, String writerId,Long stockBoards) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.stockBoards = stockBoards;
    }
}
