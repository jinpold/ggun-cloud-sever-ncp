//package store.ggun.chat.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import store.ggun.chat.domain.Messenger;
//import store.ggun.chat.domain.UserModel;
//import store.ggun.chat.serviceImpl.UserService;
//
//@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(path = "/api/users")
//public class UserController {
//
//    private final UserService userService;
//
//    @PostMapping("/login")
//    public Mono<Messenger> login(@RequestBody UserModel param) {
//        return userService.login(param).defaultIfEmpty(Messenger.builder().message("FAILURE").build());
//    }
//
//
//
//    @GetMapping("/logout")
//    public Mono<Messenger> logout(@RequestHeader("Authorization") String accessToken) {
//        Messenger m = Messenger.builder().message("SUCCESS").build();
//        Mono<Messenger> logout = Mono.just(m);
//        return logout;
//    }
//
//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public Flux<UserModel> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/detail/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<UserModel> getUserById(@PathVariable("userId") Long userId) {
//        return userService.getUserDetailById(userId);
//    }
//
//    @PostMapping("/add")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<Messenger> saveUser(@RequestBody UserModel userModel) {
//        return userService.addUser(userModel);
//    }
//
//    @PutMapping("/update/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<UserModel> updateUser(@PathVariable("id") Long id, @RequestBody UserModel userModel) {
//        return userService.updateUser(id, userModel);
//    }
//
//    @DeleteMapping("/users/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Mono<Void> deleteUser(@PathVariable("id") Long id) {
//        return userService.deleteUser(id);
//    }
//
//    @DeleteMapping("/users")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Mono<Void> deleteAllUsers() {
//        return userService.deleteAllUsers();
//    }
//
//
//
//}