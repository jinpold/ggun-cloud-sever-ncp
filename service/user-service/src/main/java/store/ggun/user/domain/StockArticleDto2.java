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
public class StockArticleDto2 {
    private Long id;
    private String title;
    private String content;
    private String writerId;
    private String stockBoards;

    @QueryProjection
    public StockArticleDto2(Long id, String title, String content, String writerId,String stockBoards) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.stockBoards = stockBoards;
    }
}
