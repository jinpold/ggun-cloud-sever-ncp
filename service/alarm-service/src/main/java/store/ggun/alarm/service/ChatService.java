package store.ggun.alarm.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.model.ChatModel;

public interface ChatService {
    Mono<ChatModel> processMessage(ChatModel ChatModel);
    Flux<ChatModel> getChatMessages(String roomId);
    Mono<ChatModel> createChatRoom(String senderId, String receiverId);
    Mono<String> getRoomId(String sender, String receiver);
}
