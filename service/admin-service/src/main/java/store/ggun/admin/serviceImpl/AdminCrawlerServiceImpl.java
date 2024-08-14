package store.ggun.admin.serviceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.model.AdminCrawlerModel;
import store.ggun.admin.repository.etc.AdminCrawlerRepository;
import store.ggun.admin.service.AdminCrawlerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCrawlerServiceImpl implements AdminCrawlerService {
    private final AdminCrawlerRepository adminCrawlerRepository;
    private List<AdminCrawlerModel> cachedNews = new ArrayList<>();

    @Override
    public List<AdminCrawlerModel> findNews() {
        if (cachedNews.isEmpty()) {
            updateNewsCache();
        }
        return cachedNews;
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateNewsCache() {
        try {
            this.cachedNews = adminCrawlerRepository.findNews();
            log.info("News cache updated successfully.");
        } catch (IOException e) {
            log.error("Error updating news cache", e);
        }
    }
}