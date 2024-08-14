package store.ggun.user.controller;

import io.swagger.v3.oas.annotations.headers.Header;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.user.domain.Messenger;
import store.ggun.user.domain.StockArticleDto;
import store.ggun.user.domain.StockArticleDto2;
import store.ggun.user.domain.StockBoardDto;
import store.ggun.user.service.StockArticleService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path="/stockArticle")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StockArticleController {
    private final StockArticleService service;

    @GetMapping(path = "/listTop")
    public ResponseEntity<List<StockArticleDto2>> listTop(){
        return ResponseEntity.ok(service.findAllListlistTop());
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<StockArticleDto>> list(@RequestParam Long boardId){
        return ResponseEntity.ok(service.findAllList(boardId));
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Messenger> save(@RequestBody StockArticleDto model, @RequestHeader("id") Long id){
        return ResponseEntity.ok(service.save(model, id));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Messenger> delete(@RequestParam Long articleId, @RequestHeader("id") Long id){
        return ResponseEntity.ok(service.deleteById(articleId, id));
    }

    @PostMapping(path = "/modify")
    public ResponseEntity<Messenger> modify(@RequestBody StockArticleDto articleId, @RequestHeader("id") Long id){
        return ResponseEntity.ok(service.modifyById(articleId, id));
    }


}
