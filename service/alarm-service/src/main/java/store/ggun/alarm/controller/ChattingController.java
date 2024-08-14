package store.ggun.alarm.controller;//package store.ggun.alarm.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.codec.ServerSentEvent;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import store.ggun.alarm.domain.dto.ChatListener;
//import store.ggun.alarm.domain.model.ChatModel;
//import store.ggun.alarm.serviceImpl.ChattingServiceImpl;
//
//import java.time.Duration;
//
//
//@Slf4j
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequiredArgsConstructor
//@RequestMapping("/chat")
//public class ChattingController {
//
//    private final ChattingServiceImpl chattingServiceImpl;
//    private final ChatListener chatListener;
//
//    @GetMapping(value = "/messages", produces = "text/event-stream")
//    public Flux<ServerSentEvent<ChatModel>> getMessages(@RequestParam String roomId) {
//        log.info("roomId: {}", roomId);
//        Flux<ServerSentEvent<ChatModel>> messageStream = chatListener.getChatMessages(roomId)
//                .map(message -> {
//                    log.info("Sending message: {}", message);
//                    return ServerSentEvent.builder(message).build();
//                });
//
//        Flux<ServerSentEvent<ChatModel>> keepAliveStream = Flux.interval(Duration.ofSeconds(10))
//                .map(seq -> {
//                    log.debug("Sending keep-alive message");
//                    return ServerSentEvent.<ChatModel>builder()
//                            .comment("keep-alive")
//                            .build();
//                });
//
//        return Flux.merge(messageStream, keepAliveStream)
//                .doOnCancel(() -> log.info("SSE connection closed for roomId: {}", roomId));
//    }
//
//    @PostMapping("/send")
//    public Mono<Void> sendMessage(@RequestBody ChatModel chatModel) {
//        return chattingServiceImpl.processMessage(chatModel).then();
//    }
//
//    @GetMapping("/create")
//    public Mono<ChatModel> createChatRoom(@RequestParam String senderId, @RequestParam String receiverId) {
//        return chattingServiceImpl.createChatRoom(senderId, receiverId);
//    }
//
//    @GetMapping("/history")
//    public Flux<ChatModel> getChatHistory(@RequestParam String roomId) {
//        return chattingServiceImpl.getChatMessages(roomId);
//    }
//
//    @GetMapping("/room")
//    public Mono<String> getRoomId(@RequestParam String senderId, @RequestParam String receiverId) {
//        return chattingServiceImpl.getRoomId(senderId, receiverId);
//    }
//}
