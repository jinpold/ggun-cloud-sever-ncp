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

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
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



    @GetMapping("/receive/{roomId}")
    public Flux<ServerSentEvent<ChatDto>> subscribeByRoomId(@PathVariable("roomId")String roomId) {
        log.info("subscribe chat by room id {}", roomId);
        return roomService.subscribeByRoomId(roomId)
                .switchIfEmpty(Flux.error(new ChatException("Room not found")));
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