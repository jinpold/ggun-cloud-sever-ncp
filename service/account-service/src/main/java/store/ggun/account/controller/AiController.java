package store.ggun.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.account.domain.dto.AccountDto;
import store.ggun.account.domain.dto.Messenger;
import store.ggun.account.domain.dto.OwnStockDto;
import store.ggun.account.service.AccountService;
import store.ggun.account.service.OwnStockService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
public class AiController {
    private final AccountService service;
    private final OwnStockService ownStockService;

    @GetMapping("/getAIAcno")
    public ResponseEntity<List<AccountDto>> getAIAcno(){
        return ResponseEntity.ok(service.getAIAcno());
    }

    @PostMapping("/trade")
    public ResponseEntity<Messenger> save(@RequestBody OwnStockDto ownStockDto){
        log.info("계좌가입 입력정보 {} ",ownStockDto);
        return ResponseEntity.ok(ownStockService.save(ownStockDto));
    }

    @GetMapping("/stockList")
    public ResponseEntity<List<OwnStockDto>> findAll(@RequestParam Long id){
        return ResponseEntity.ok(ownStockService.findByAccount(id));
    }

}
