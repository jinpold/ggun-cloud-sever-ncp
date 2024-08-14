package store.ggun.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.account.domain.dto.AccHistoryDto;
import store.ggun.account.domain.dto.Messenger;
import store.ggun.account.service.AccHistoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accHistories")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AccHistoryController {

    private final AccHistoryService service;

    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody AccHistoryDto accHistoryDto){
        return ResponseEntity.ok(service.save(accHistoryDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Messenger> deleteById(@RequestParam long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

//    @GetMapping("/list")
//    public ResponseEntity<Page<AccHistoryDto>> findAll(@RequestParam Long id, @RequestParam(value="page", defaultValue="0") int page){
//        return ResponseEntity.ok(service.findByAccount(id,page));
//    }

    @GetMapping("/list")
    public ResponseEntity<List<AccHistoryDto>> findAll(@RequestParam Long id){
        return ResponseEntity.ok(service.findByAccount(id));
    }
    @GetMapping("/detail")
    public ResponseEntity<Optional<AccHistoryDto>> findById(@RequestParam long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@RequestParam long id){
        return ResponseEntity.ok(service.existsById(id));
    }

}
