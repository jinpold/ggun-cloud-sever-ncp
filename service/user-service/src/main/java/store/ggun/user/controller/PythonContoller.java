package store.ggun.user.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.user.repository.UserRepository;

@Slf4j
@RestController
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
})
@RequiredArgsConstructor
@RequestMapping(path="/python")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PythonContoller {
    private final UserRepository userRepository;


    @GetMapping("/color")
    public ResponseEntity<String> join(@RequestHeader("id") Long id) {
        return ResponseEntity.ok(userRepository.color(id));
    }
}
