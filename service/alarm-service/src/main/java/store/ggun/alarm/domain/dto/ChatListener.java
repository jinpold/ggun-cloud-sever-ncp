package store.ggun.alarm.domain.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import store.ggun.alarm.domain.model.ChatModel;

@Component
@RequiredArgsConstructor
public class ChatListener {

    private final Sinks.Many<ChatModel> chatSink = Sinks.many().multicast().onBackpressureBuffer();

    @KafkaListener(topics = "advice", groupId = "chat-group")
    public void listen(ChatModel chatModel) {
        chatSink.tryEmitNext(chatModel);
    }

    public Flux<ChatModel> getChatMessages(String roomId) {
        return chatSink.asFlux()
                .filter(chatMessage -> chatMessage.getRoomId().equals(roomId));
    }
}
