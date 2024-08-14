package store.ggun.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.user.domain.ArticleDto;
import store.ggun.user.domain.Messenger;
import store.ggun.user.domain.StockBoardDto;
import store.ggun.user.domain.StockBoardModel;
import store.ggun.user.repository.StockBoardDao;
import store.ggun.user.service.StockBoardService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path="/stockBoard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StockBoardController {

    private final StockBoardService service;

    @GetMapping(path = "/list")
    public ResponseEntity<List<StockBoardDto>> list(){
        return ResponseEntity.ok(service.findAllList());
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Messenger> save(@RequestBody StockBoardDto model){
        return ResponseEntity.ok(service.save(model));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Messenger> delete(@RequestParam Long boardId ){
        return ResponseEntity.ok(service.deleteById(boardId));
    }

    @PostMapping(path = "/modify")
    public ResponseEntity<Messenger> modify(@RequestBody StockBoardDto model){
        return ResponseEntity.ok(service.modify(model));
    }
}
