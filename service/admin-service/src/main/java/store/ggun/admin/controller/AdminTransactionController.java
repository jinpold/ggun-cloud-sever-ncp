package store.ggun.admin.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.admin.domain.dto.AdminTransactionDto;
import store.ggun.admin.domain.dto.TradeMetrics;
import store.ggun.admin.domain.model.Messenger;
import store.ggun.admin.serviceImpl.AdminTransactionServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RequestMapping(path = "/transactions")
@Slf4j
public class AdminTransactionController {

    private final AdminTransactionServiceImpl service;

    @SuppressWarnings("static-access")
    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody AdminTransactionDto dto) {
        log.info("입력받은 정보 : {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AdminTransactionDto>> findTransactionsById() throws SQLException {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/netProfitByDate")
    public ResponseEntity<Map<String, Map<String, Long>>> getNetProfitByDate() {
        return ResponseEntity.ok(service.getNetProfitByDate());
    }

    @GetMapping("/TotalByDate")
    public ResponseEntity<Map<String, Map<String, TradeMetrics>>> getTotalByDate() {
        return ResponseEntity.ok(service.getTotalByDate());
    }


    @GetMapping("/QuantityByDate")
    public ResponseEntity<Map<String, Map<String, Integer>>> getQuantityByDate() {
        return ResponseEntity.ok(service.getQuantityByDate());
    }

    @GetMapping("/getProductByDate")
    public ResponseEntity<Map<String, Map<String, Map<String, Integer>>>> getProductByDate() {
        return ResponseEntity.ok(service.getProductByDate());
    }

    @GetMapping("/getColorByDate")
    public ResponseEntity<Map<String, Map<String, Integer>>> getColorByDate() {
        return ResponseEntity.ok(service.getColorByDate());
    }

    @GetMapping("/getColorByCount")
    public ResponseEntity<Map<String, Integer>> getColorByCount() {
        return ResponseEntity.ok(service.getColorByCount());
    }
}
