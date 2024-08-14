package store.ggun.user.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.user.domain.Messenger;
import store.ggun.user.domain.StockArticleDto;
import store.ggun.user.domain.StockCommentDto;
import store.ggun.user.domain.StockCommentModel;
import store.ggun.user.service.StockCommentService;

import java.util.List;

@Slf4j
@RestController
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
})
@RequiredArgsConstructor
@RequestMapping(path="/stockComment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StockCommentController {
    private final StockCommentService service;

    @GetMapping(path = "/list")
    public ResponseEntity<List<StockCommentDto>> list(@RequestParam Long articleId){
        return ResponseEntity.ok(service.findAllList(articleId));
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Messenger> save(@RequestBody StockCommentDto model, @RequestHeader("id") Long id){
        return ResponseEntity.ok(service.save(model, id));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Messenger> delete(@RequestParam Long articleId, @RequestHeader("id") Long id){
        return ResponseEntity.ok(service.deleteById(articleId, id));
    }

    @PostMapping(path = "/modify")
    public ResponseEntity<Messenger> modify(@RequestBody StockCommentDto articleId, @RequestHeader("id") Long id){
        return ResponseEntity.ok(service.modifyById(articleId, id));
    }
}
