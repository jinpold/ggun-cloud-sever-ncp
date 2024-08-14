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
public class StockBoardDto {
    private Long id;
    private String title;
    private String content;
    private String description;

    @QueryProjection
    public StockBoardDto(Long id, String title, String content, String description) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.description = description;
    }
}
