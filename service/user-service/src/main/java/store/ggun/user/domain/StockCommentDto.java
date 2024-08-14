package store.ggun.user.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Component
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class StockCommentDto {
    private Long id;
    private String comment;
    private String writerId;
    private Long stockArticles;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime commentDate;

    @QueryProjection
    public StockCommentDto(Long id, String comment, String writerId, LocalDateTime commentDate,Long stockArticles) {
        this.id = id;
        this.comment = comment;
        this.writerId = writerId;
        this.commentDate = commentDate;
        this.stockArticles = stockArticles;
    }
}
