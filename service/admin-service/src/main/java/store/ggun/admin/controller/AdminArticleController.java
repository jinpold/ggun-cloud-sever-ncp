package store.ggun.admin.controller;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.admin.domain.dto.AdminArticleDto;
import store.ggun.admin.domain.model.Messenger;
import store.ggun.admin.repository.jpa.AdminArticleRepository;
import store.ggun.admin.serviceImpl.AdminArticleService;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/adminArticles")
@Slf4j
public class AdminArticleController {
    private final AdminArticleService service;
    private final AdminArticleRepository repository;

    @SuppressWarnings("static-access")
    @PostMapping( "/save")
    public ResponseEntity<Messenger> save(@RequestBody AdminArticleDto dto) {
        log.info("입력받은 정보 : {}", dto );
        return ResponseEntity.ok(service.save(dto));

    }
    @GetMapping("/list")
    public ResponseEntity<List<AdminArticleDto>> findByBoardId() throws SQLException {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/detail")
    public ResponseEntity<AdminArticleDto> findById(@RequestParam("id") Long id) {
        log.info("입력받은 정보 : {}", id );
        return ResponseEntity.ok(service.findById(id).orElseGet(AdminArticleDto::new));
    }
    @PutMapping ("/modify")
    public ResponseEntity<Messenger> modify(@RequestBody AdminArticleDto dto) {
        log.info("입력받은 정보 : {}", dto );
        return ResponseEntity.ok(service.modify(dto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Messenger> deleteById(@RequestParam("id") Long id) {
        log.info("입력받은 정보 : {}", id );
        return ResponseEntity.ok(service.deleteById(id));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> count()  {
        return ResponseEntity.ok(service.count());
    }
    @GetMapping("/exists")
    public ResponseEntity<Messenger> existsById(@RequestParam("id") Long id){
        service.existsById(id);
        return ResponseEntity.ok(new Messenger());
    }
    @GetMapping("/myList")
    public ResponseEntity<List<AdminArticleDto>> getArticleByBoardId(@RequestParam("id") Long boardId) {
        return ResponseEntity.ok(service.getArticleByBoardId(boardId));
    }
}
