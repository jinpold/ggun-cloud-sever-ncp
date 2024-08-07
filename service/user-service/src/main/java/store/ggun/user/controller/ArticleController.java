package store.ggun.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.user.domain.ArticleDto;
import store.ggun.user.domain.ArticleModel;
import store.ggun.user.domain.Messenger;
import store.ggun.user.service.ArticleService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path="/articles")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArticleController {

    private final ArticleService service;

    @GetMapping(path = "/list")
    public ResponseEntity<List<ArticleDto>> list(@RequestParam String boardId){
        return ResponseEntity.ok(service.findAllByBoardId(boardId));
    }

    @PostMapping(path = "/save")
    public ResponseEntity<ArticleModel> save(@RequestBody ArticleDto model, @RequestHeader("id") String id){
        return ResponseEntity.ok(service.save(model,id));
    }

    @PostMapping(path = "/modify")
    public ResponseEntity<ArticleModel> modify(@RequestBody ArticleDto model, @RequestHeader("id") String id){
        return ResponseEntity.ok(service.modify(model,id));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Messenger> delete(@RequestBody Long boardId, @RequestHeader("id") String id){
        return ResponseEntity.ok(service.delete(boardId,id));
    }
}
