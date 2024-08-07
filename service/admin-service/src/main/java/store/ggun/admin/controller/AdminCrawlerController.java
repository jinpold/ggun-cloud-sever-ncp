package store.ggun.admin.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.ggun.admin.domain.model.AdminCrawlerModel;
import store.ggun.admin.service.AdminCrawlerService;

import java.io.IOException;
import java.util.List;

@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/news")
@Slf4j
public class AdminCrawlerController {
    private final AdminCrawlerService adminCrawlerService;

    @GetMapping("/list")
    public List<AdminCrawlerModel> findNews() throws IOException {
        return adminCrawlerService.findNews();
    }
}

