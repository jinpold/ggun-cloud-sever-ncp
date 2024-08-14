package store.ggun.alarm.serviceImpl;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import store.ggun.alarm.domain.dto.ChatDto;
import store.ggun.alarm.domain.dto.RoomDto;
import store.ggun.alarm.domain.model.ChatModel;
import store.ggun.alarm.domain.model.CounterDocument;
import store.ggun.alarm.domain.model.Messenger;
import store.ggun.alarm.domain.model.RoomModel;
import store.ggun.alarm.exception.ChatException;
import store.ggun.alarm.repository.ChatRepository;
import store.ggun.alarm.repository.RoomRepository;
import store.ggun.alarm.service.RoomService;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final Map<String, Sinks.Many<ServerSentEvent<ChatDto>>> chatSinks;
    private final KafkaTemplate<String, ChatModel> kafkaTemplate;
    private final ReactiveMongoTemplate mongoTemplate;

    @PreDestroy
    public void close() {
        chatSinks.values().forEach(Sinks.Many::tryEmitComplete);
    }

    @Override
    public Mono<RoomModel> save(RoomDto dto) {
        return roomRepository.save(RoomModel.builder()
                .title(dto.getTitle())
                .members(dto == null ? new ArrayList<>() : dto.getMembers())
                .build());
    }

    private Mono<Long> getNextSequenceId(String key) {
        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        return mongoTemplate.findAndModify(query, update, options, CounterDocument.class)
                .map(CounterDocument::getSeq);
    }

    @Override
    public Mono<ChatDto> saveChat(ChatDto chatDTO) {
        if (chatDTO == null || chatDTO.getRoomId() == null || chatDTO.getSenderId() == null) {
            log.error("ChatDTO, roomId, and senderId must not be null: {}", chatDTO);
            return Mono.error(new IllegalArgumentException("ChatDTO, roomId, and senderId must not be null"));
        }

        log.info("saveChat called with roomId: {}, senderId: {}", chatDTO.getRoomId(), chatDTO.getSenderId());

        return roomRepository.findById(chatDTO.getRoomId())
                .switchIfEmpty(Mono.defer(() -> {
                    // 방이 없을 경우 방을 생성
                    RoomModel newRoom = new RoomModel();
                    newRoom.setId(chatDTO.getRoomId());
                    newRoom.getMembers().add(chatDTO.getSenderId());

                    return roomRepository.save(newRoom)
                            .doOnSuccess(savedRoom -> {
                                // 방이 생성된 후 sink를 준비
                                chatSinks.putIfAbsent(savedRoom.getId(), Sinks.many().multicast().onBackpressureBuffer());
                                log.info("Created new room and associated sink for roomId: {}", savedRoom.getId());
                            });
                }))
                .flatMap(room -> {
                    if (!room.getMembers().contains(chatDTO.getSenderId())) {
                        // senderId를 방의 멤버로 추가
                        room.getMembers().add(chatDTO.getSenderId());
                        return roomRepository.save(room)
                                .then(Mono.just(room));
                    }
                    return Mono.just(room);
                })
                .flatMap(room -> getNextSequenceId("chatId")
                        .flatMap(seqId -> chatRepository.save(ChatModel.builder()
                                .id(String.valueOf(seqId)) // 순차적인 ID를 설정
                                .roomId(chatDTO.getRoomId())
                                .message(chatDTO.getMessage())
                                .senderId(chatDTO.getSenderId())
                                .senderName(chatDTO.getSenderName())
                                .createdAt(LocalDateTime.now())
                                .build())))
                .flatMap(i -> Mono.just(ChatDto.builder()
                        .roomId(i.getRoomId())
                        .senderId(i.getSenderId())
                        .senderName(i.getSenderName())
                        .message(i.getMessage())
                        .createdAt(i.getCreatedAt())
                        .build()))
                .doOnSuccess(chatDto -> {
                    // Ensure sink exists before emitting
                    Sinks.Many<ServerSentEvent<ChatDto>> sink = chatSinks.get(chatDto.getRoomId());
                    if (sink != null) {
                        sink.tryEmitNext(ServerSentEvent.builder(chatDto).build());
                        log.info("Event emitted to sink for roomId: {}", chatDto.getRoomId());
                    } else {
                        log.warn("No sink found for roomId: {}", chatDto.getRoomId());
                    }
                });
    }


    @Override
    public Mono<RoomModel> update(RoomDto dto) {
        return roomRepository.existsById(dto.getId())
                .flatMap(exists -> roomRepository.save(RoomModel.builder()
                        .id(dto.getId())
                        .title(dto.getTitle())
                        .members(dto.getMembers())
                        .build()));
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return roomRepository.existsById(id)
                .filter(exists -> exists)
                .flatMap(exists -> roomRepository.deleteById(id).thenReturn(exists));
    }

    @Override
    public Mono<RoomModel> findById(String id) {
        return roomRepository.findById(id);
    }

    @Override
    public Mono<ChatModel> findChatById(String id) {
        return chatRepository.findById(id);
    }

    @Override
    public Flux<ChatModel> findChatsByRoomId(String roomId) {
        return roomRepository.existsById(roomId)
                .filter(exists -> exists)
                .flatMapMany(exists -> chatRepository.findByRoomId(roomId));
    }

    @Override
    public Flux<RoomModel> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Mono<Long> count() {
        return roomRepository.count();
    }

    @Override
    public Flux<ServerSentEvent<ChatDto>> subscribeByRoomId(String roomId) {
        return roomRepository.findById(roomId)
                .switchIfEmpty(createRoom(roomId))  // 방이 없을 때 방 생성
                .flatMapMany(room -> {
                    // 이전에 저장된 채팅 메시지들 로딩 및 구독 시작
                    Sinks.Many<ServerSentEvent<ChatDto>> sink = chatSinks.computeIfAbsent(roomId, id ->
                            Sinks.many().multicast().onBackpressureBuffer());

                    // 필요한 경우 senderId를 members에 추가
                    addSenderIdToRoomMembers(room, sink);

                    log.info("User subscribed to room {}", roomId);

                    return sink.asFlux()
                            .doOnNext(event -> log.info("Sending event to client: {}", event.data()))
                            .doOnCancel(() -> {
                                log.info("User unsubscribed from room {}", roomId);
                                chatSinks.remove(roomId);
                            })
                            .doOnError(error -> {
                                log.error("Error in room {}: {}", roomId, error.getMessage(), error);
                                chatSinks.get(roomId).tryEmitError(new ChatException(error.getMessage()));
                            })
                            .doOnComplete(() -> {
                                log.info("Completed room {}", roomId);
                                chatSinks.get(roomId).tryEmitComplete();
                                chatSinks.remove(roomId);
                            });
                });
    }

    private void addSenderIdToRoomMembers(RoomModel room, Sinks.Many<ServerSentEvent<ChatDto>> sink) {
        // 새로운 채팅 메시지를 방에 추가할 때 Room의 members에 senderId를 추가
        sink.asFlux()
                .doOnNext(event -> {
                    ChatDto chatDto = event.data();
                    String senderId = chatDto.getSenderId();

                    if (!room.getMembers().contains(senderId)) {
                        room.getMembers().add(senderId);
                        roomRepository.save(room).subscribe();  // members 업데이트
                        log.info("Added senderId {} to room {}", senderId, room.getId());
                    }
                })
                .subscribe();
    }

    @Override
    public Mono<Integer> countConnection() {
        return Mono.just(chatSinks.size());
    }

    public Mono<RoomModel> createRoom(String roomId) {
        RoomModel newRoom = RoomModel.builder()
                .id(roomId)
                .title("Room " + roomId)
                .members(new ArrayList<>())  // 빈 members 리스트로 초기화
                .build();

        return roomRepository.save(newRoom)
                .doOnSuccess(room -> log.info("Created new room with id: {}", roomId));
    }

    @Override
    public Mono<Messenger> deleteRoom(String roomId) {
        return roomRepository.existsById(roomId)
                .doOnNext(exists -> log.info("Room exists for ID {}: {}", roomId, exists))
                .flatMap(exists -> {
                    if (exists) {
                        return roomRepository.deleteById(roomId)
                                .then(Mono.just(Messenger.builder().message("Room deleted").build()));
                    } else {
                        log.warn("Room does not exist for ID: " + roomId);
                        return Mono.just(Messenger.builder().message("Room does not exist").build());
                    }
                });
    }

    @Override
    public Mono<Messenger> deleteChat(String chatId) {
        return chatRepository.existsById(chatId)
                .flatMap(exists -> {
                    if (exists) {
                        return chatRepository.deleteById(chatId)
                                .then(Mono.just(Messenger.builder().message("Chat deleted").build()));
                    } else {
                        return Mono.just(Messenger.builder().message("Chat does not exist").build());
                    }
                });
    }

    @Override
    public Flux<ChatModel> findAllChats() {
        return chatRepository.findAll();
    }
}