package store.ggun.alarm.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.dto.ChatDto;
import store.ggun.alarm.domain.model.ChatModel;
import store.ggun.alarm.domain.model.Messenger;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl {
    private final ReactiveKafkaProducerTemplate<String, ChatModel> reactiveKafkaProducerTemplate;
    private final ReactiveKafkaConsumerTemplate<String, ChatModel> reactiveKafkaConsumerTemplate;

    public Mono<Messenger> send(ChatDto chatFluxModel) {
        return reactiveKafkaProducerTemplate.send(
                        "chats",

                        ChatModel.builder()
                                .roomId(chatFluxModel.getRoomId())
                                .message(chatFluxModel.getMessage())
                                .senderId(chatFluxModel.getSenderId())
                                .senderName(chatFluxModel.getSenderName())
                                .build()
                )
                .log()
                .flatMap(i -> Mono.just(Messenger.builder().message("Send message success").build()));
    }

    public Flux<ServerSentEvent<ChatModel>> recieve(Integer roomId) {
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                .filter(i -> i.partition() == 1)
                .doOnNext(i -> log.info("Received key={}, value={}, topic={}, offset={}", i.key(), i.value(), i.topic(), i.offset()))
                .map(record -> ServerSentEvent.builder(record.value()).build());
    }
}