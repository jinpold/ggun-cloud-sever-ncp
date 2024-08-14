package store.ggun.alarm.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.dto.UserDto;
import store.ggun.alarm.domain.model.UserModel;
import store.ggun.alarm.service.UserService;



@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {


    private final UserService userService;
    private final UserDto userDto;

    @PostMapping("/save")
    public ResponseEntity<Mono<UserModel>> save(@RequestBody UserDto userDto) {
        log.info("adminDto: {}", userDto.getEmail());
        return ResponseEntity.ok(userService.save(userDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Mono<UserModel>> findById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Flux<UserModel>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Mono<UserModel>> update(@PathVariable String id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(id,userDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @PutMapping("/switching/{id}")
    public ResponseEntity<Mono<String>> switching(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.switching(id));
    }

    @GetMapping("/enabled")
    public ResponseEntity<Flux<UserModel>> findAllByEnabled() {
        return ResponseEntity.ok(userService.findAllByEnabled());
    }

    @GetMapping("/countEnabled")
    public ResponseEntity<Mono<Long>> countAdminsEnabledFalse() {
        return ResponseEntity.ok(userService.countAdminsEnabledFalse());
    }

    @GetMapping("/search")
    public ResponseEntity<Flux<UserDto>> searchByName(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchByName(keyword));
    }
}


