package store.ggun.alarm.domain.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatIdRequest {

    private String chatId;
}
