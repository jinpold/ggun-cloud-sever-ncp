package store.ggun.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.account.domain.dto.Messenger;
import store.ggun.account.domain.dto.OwnStockDto;
import store.ggun.account.service.OwnStockService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ownStocks")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
public class OwnStockController {
    
    private final OwnStockService ownStockService;


    @GetMapping("/")
    public String test(){
        return "ownStock test";
    }

    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody OwnStockDto ownStockDto){
        return ResponseEntity.ok(ownStockService.save(ownStockDto));
    }

    @PostMapping("/profit")
    public ResponseEntity<List<OwnStockDto>> getProfit(@RequestBody List<OwnStockDto> ownStockDto){
        return ResponseEntity.ok(ownStockService.getProfit(ownStockDto));
    }

    @PostMapping("/totalProfit")
    public ResponseEntity<OwnStockDto> getTotalProfit(@RequestBody List<OwnStockDto> ownStockDto){
        return ResponseEntity.ok(ownStockService.getTotalProfit(ownStockDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Messenger> deleteById(@RequestParam long id){
        return ResponseEntity.ok(ownStockService.deleteById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<OwnStockDto>> findAll(@RequestParam Long id){
        return ResponseEntity.ok(ownStockService.findByAccount(id));
    }

    @GetMapping("/detail")
    public ResponseEntity<Optional<OwnStockDto>> findById(@RequestParam long id){
        return ResponseEntity.ok(ownStockService.findById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        return ResponseEntity.ok(ownStockService.count());
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@RequestParam long id){
        return ResponseEntity.ok(ownStockService.existsById(id));
    }




}
