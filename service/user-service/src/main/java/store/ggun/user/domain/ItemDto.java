package store.ggun.user.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@NoArgsConstructor
@Data
@Builder
public class ItemDto {
    private Long id;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Float adjClose;
    private int volume;
    private Date date;

    @QueryProjection
    public ItemDto(Long id, Float open, Float high, Float low, Float close, Float adjClose, int volume, Date date) {
        this.id = id;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
        this.date = date;
    }
}