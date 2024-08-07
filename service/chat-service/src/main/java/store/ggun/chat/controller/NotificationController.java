package store.ggun.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import store.ggun.chat.domain.NotificationModel;
import store.ggun.chat.serviceImpl.NotificationService;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/chat")
public class NotificationController {
    private final NotificationService service;

    @GetMapping(path = "/receive/{id}")
    public Flux<ServerSentEvent<NotificationModel>> receiveByRoomId(@PathVariable String id) {
        log.info("Receive request received : {}", id);
        return service.connect(id).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/send")
    public Mono<Boolean> send(@RequestBody NotificationModel entity) {
        log.info("11{}",entity.toString());
        return service.save(entity).subscribeOn(Schedulers.boundedElastic());
    }
}

