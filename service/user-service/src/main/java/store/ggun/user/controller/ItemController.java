package store.ggun.user.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.ggun.user.domain.ItemModel;
import store.ggun.user.service.ItemService;
import store.ggun.user.config.PageRequestVo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
})
@RequiredArgsConstructor
@RequestMapping(path="/items")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {
    private final ItemService service;

    @GetMapping(path = "/findByVolume")
    public ResponseEntity<List<ItemModel>> findByVolume() {
        return ResponseEntity.ok(service.findByVolume());
    }

    @GetMapping("/list")
    public List<ItemModel> findAll(PageRequestVo vo) {
        return service.findAll();
    }

    @PostMapping(path = "/detail")
    public ResponseEntity<List<ItemModel>> findAllByBoarId(@RequestBody Map<String, String> search, Date sdate, Date edate) throws ParseException {
        log.info(search.toString());
        return ResponseEntity.ok(service.findDetail(search));
    }
}