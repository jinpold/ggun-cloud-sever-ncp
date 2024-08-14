package store.ggun.user.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.user.domain.PrincipalUserDetails;
import store.ggun.user.domain.TokenVo;
import store.ggun.user.domain.UserDto;
import store.ggun.user.repository.UserRepository;
import store.ggun.user.service.UserService;

@Slf4j
@RestController
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
})
@RequiredArgsConstructor
@RequestMapping(path="/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping
    public String test(){
        return "helteho";
    }

    @PostMapping("/oauth")
    public ResponseEntity<PrincipalUserDetails> oauth(@RequestBody String email) {
        return ResponseEntity.ok(userService.findByEmailOauth(email));
    }

    @PostMapping("/join")
    public ResponseEntity<TokenVo> join(@RequestBody UserDto param) {
        return ResponseEntity.ok(userService.join(param));
    }

    @GetMapping("/check")
    public ResponseEntity<TokenVo> checkUser(@RequestParam String username){
        return ResponseEntity.ok(TokenVo.builder()
                .message(userRepository.existsByUsername(username)?"FAIL":"SUCCESS")
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<PrincipalUserDetails> login(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.login(userDto));
    }

}