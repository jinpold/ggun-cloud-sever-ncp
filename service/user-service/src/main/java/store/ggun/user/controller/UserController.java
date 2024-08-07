package store.ggun.user.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.user.domain.TokenVo;
import store.ggun.user.domain.UserDto;
import store.ggun.user.service.UserService;

import java.util.Optional;


@Slf4j
@RestController
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
})
@RequiredArgsConstructor
@RequestMapping(path="")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/modify")
    public ResponseEntity<TokenVo> modify(@RequestBody UserDto userDto, @RequestHeader String id){
        userDto.setId(Long.valueOf(id));
        return ResponseEntity.ok(userService.modify(userDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TokenVo> delete(@RequestHeader Long id){
        return ResponseEntity.ok(userService.delete(id));
    }




    public Optional<Object> searchUser(String username) {
    return null;
    }

}
