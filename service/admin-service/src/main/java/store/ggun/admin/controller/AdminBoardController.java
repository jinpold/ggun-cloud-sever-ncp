package store.ggun.admin.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.admin.domain.dto.AdminBoardDto;
import store.ggun.admin.domain.model.AdminMessengerModel;
import store.ggun.admin.pagination.PageRequestVo;
import store.ggun.admin.serviceImpl.AdminBoardService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/adminBoards")
@Slf4j
public class AdminBoardController {

    private final AdminBoardService service;


    @SuppressWarnings("static-access")
    @PostMapping( "/save")
    public ResponseEntity<AdminMessengerModel> save(@RequestBody AdminBoardDto dto) {
        log.info("입력받은 정보 : {}", dto );
        return ResponseEntity.ok(service.save(dto));
    }
    @GetMapping("/list") //all-users
    public ResponseEntity<List<AdminBoardDto>> findAll() throws SQLException {
        log.info("입력받은 정보 : {}" );
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/detail")
    public ResponseEntity<AdminBoardDto> findById(@RequestParam("id") Long id) {
        log.info("입력받은 정보 : {}", id );
        return ResponseEntity.ok(service.findById(id).orElseGet(AdminBoardDto::new));
    }
    @PutMapping ("/modify")
    public ResponseEntity<AdminMessengerModel> modify(@RequestBody AdminBoardDto dto) {
        log.info("입력받은 정보 : {}", dto );
        return ResponseEntity.ok(service.modify(dto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<AdminMessengerModel> deleteById(@RequestParam("id") Long id) {
        log.info("입력받은 정보 : {}", id );
        return ResponseEntity.ok(service.deleteById(id));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> count()  {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<AdminMessengerModel> existsById(PageRequestVo vo){
        service.existsById(0L);
        return ResponseEntity.ok(new AdminMessengerModel());
    }



}