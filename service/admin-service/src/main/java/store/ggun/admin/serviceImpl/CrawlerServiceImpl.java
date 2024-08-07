package store.ggun.admin.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import store.ggun.admin.domain.model.CrawlerModel;
import store.ggun.admin.repository.etc.CrawlerRepository;
import store.ggun.admin.service.CrawlerService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CrawlerServiceImpl implements CrawlerService {
    private final CrawlerRepository crawlerRepository;
    private List<CrawlerModel> cachedNews;

    @Override
    public List<CrawlerModel> findNews() {
        return cachedNews;
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateNewsCache() {
        try {
            this.cachedNews = crawlerRepository.findNews();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}