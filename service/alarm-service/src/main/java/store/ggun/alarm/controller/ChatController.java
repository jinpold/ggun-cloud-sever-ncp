package store.ggun.alarm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.dto.ChatDto;
import store.ggun.alarm.domain.dto.ChatIdRequest;
import store.ggun.alarm.domain.dto.RoomDto;
import store.ggun.alarm.domain.dto.RoomIdRequest;
import store.ggun.alarm.domain.model.ChatModel;
import store.ggun.alarm.domain.model.Messenger;
import store.ggun.alarm.domain.model.RoomModel;
import store.ggun.alarm.exception.ChatException;
import store.ggun.alarm.service.RoomService;

import java.time.Duration;

@Slf4j
@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {
    private final RoomService roomService;

    @GetMapping("/checkServer")
    public Mono<String> getMethodName() {
        log.info("Check server status");
        return roomService.countConnection()
                .flatMap(count -> Mono.just("Server is running. Total connection: " + count));
    }

    @PostMapping("/save")
    public Mono<RoomModel> saveRoom(@RequestBody RoomDto dto) {
        log.info("Save room");
        return roomService.save(dto);
    }

    @GetMapping("/list")
    public Flux<RoomModel> findAllRooms() {
        log.info("Find all rooms");
        return roomService.findAll();
    }

    @GetMapping("ChatList")
    public Flux<ChatModel> findAllChats() {
        log.info("Find all chats");
        return roomService.findAllChats();
    }

    @GetMapping(value = "/receive/{roomId}", produces = "text/event-stream")
    public Flux<ServerSentEvent<ChatDto>> subscribeByRoomId(@PathVariable("roomId") String roomId) {
        log.info("Subscribe chat by room id {}", roomId);

        // 채팅 메시지 스트림
        Flux<ServerSentEvent<ChatDto>> chatFlux = roomService.subscribeByRoomId(roomId)
                .map(chatDto -> ServerSentEvent.<ChatDto>builder()
                        .data(chatDto.data())
                        .build())
                .switchIfEmpty(Flux.error(new ChatException("Room not found")));

        // Keep-alive 스트림
        Flux<ServerSentEvent<ChatDto>> keepAliveFlux = Flux.interval(Duration.ofSeconds(15))
                .map(seq -> ServerSentEvent.<ChatDto>builder()
                        .comment("keep-alive")  // 빈 데이터
                        .build());

        return Flux.merge(chatFlux, keepAliveFlux)
                .doOnCancel(() -> log.info("SSE connection closed for roomId: {}", roomId));
    }

    @PostMapping("/send")
    public Mono<ChatDto> sendChat(@RequestBody ChatDto chatDTO) {
        log.info("Send chat {}", chatDTO.toString());
        return roomService.saveChat(chatDTO)
                .switchIfEmpty(Mono.error(new ChatException("Room not found")));
    }

    @PostMapping("/create")
    public Mono<RoomModel> create(@RequestBody String roomId) {
        log.info("Create room");
        return roomService.createRoom(roomId);
    }

    @DeleteMapping("/delete")
    public Mono<Messenger> deleteRoom(@RequestBody RoomIdRequest request) {
        log.info("Delete room with ID: " + request.getRoomId());
        return roomService.deleteRoom(request.getRoomId());
    }

    @DeleteMapping("/deleteChat")
    public Mono<Messenger> deleteChat(@RequestBody ChatIdRequest request) {
        log.info("Delete chat with ID: " + request.getChatId());
        return roomService.deleteChat(request.getChatId());
    }
}
