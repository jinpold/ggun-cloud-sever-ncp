package store.ggun.admin.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.model.AdminCrawlerModel;
import store.ggun.admin.repository.etc.AdminCrawlerRepository;
import store.ggun.admin.service.AdminCrawlerService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminCrawlerServiceImpl implements AdminCrawlerService {
    private final AdminCrawlerRepository adminCrawlerRepository;
    private List<AdminCrawlerModel> cachedNews;

    @Override
    public List<AdminCrawlerModel> findNews() {
        return cachedNews;
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateNewsCache() {
        try {
            this.cachedNews = adminCrawlerRepository.findNews();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}