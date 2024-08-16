package store.ggun.alarm.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "chats")
public class ChatModel implements Serializable {
    @Id
    private String id; // String 타입으로 설정
    private String roomId;
    private String senderId;
    private String senderName;
    private String message;
    private LocalDateTime createdAt;
}
