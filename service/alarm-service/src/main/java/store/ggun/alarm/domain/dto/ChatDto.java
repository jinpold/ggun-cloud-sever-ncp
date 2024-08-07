package store.ggun.alarm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {
    private String id;
    private String roomId;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String message;
    private LocalDateTime createdAt;
}