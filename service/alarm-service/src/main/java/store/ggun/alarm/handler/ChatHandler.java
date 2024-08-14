package store.ggun.alarm.handler;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.dto.ChatDto;
import store.ggun.alarm.exception.ChatException;
import store.ggun.alarm.service.RoomService;

@Component
public class ChatHandler {

    private final RoomService roomService;

    public ChatHandler(RoomService roomService) {
        this.roomService = roomService;
    }

    public Mono<ServerResponse> subscribeByRoomId(ServerRequest request) {
        String roomId = request.pathVariable("roomId");

        Flux<ServerSentEvent<ChatDto>> chatEvents = roomService.subscribeByRoomId(roomId)
                .switchIfEmpty(Flux.error(new ChatException("Room not found")));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(chatEvents, ChatDto.class);
    }
}
