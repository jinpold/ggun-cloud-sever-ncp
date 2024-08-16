package store.ggun.alarm.domain.dto;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatIdRequest {

    @Id
    private String chatId;
}
