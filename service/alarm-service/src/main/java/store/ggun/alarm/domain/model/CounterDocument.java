package store.ggun.alarm.domain.model;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Document
public class CounterDocument {
    @Id
    private String id;
    private long seq;
}
