package store.ggun.admin.repository.etc;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;
import store.ggun.admin.domain.model.AdminCrawlerModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AdminCrawlerRepository {
    private List<AdminCrawlerModel> crawlers = new ArrayList<>();

    public List<AdminCrawlerModel> findNews() throws IOException {
        try {
            Document doc = Jsoup.connect("https://news.naver.com/breakingnews/section/101/258").get();
            crawlers.clear();  // 리스트를 초기화하여 이전 데이터를 제거

            Elements articleElements = doc.select("ul.sa_list > li.sa_item");

            for (Element articleElement : articleElements) {
                String imgLink = articleElement.select("a.sa_thumb_link").attr("href");
                String title = articleElement.select("a.sa_text_title").text();
                String content = articleElement.select("div.sa_text_lede").text();
                String imgSrc = articleElement.select("img").attr("data-src");

                crawlers.add(new AdminCrawlerModel(imgLink, title, content, imgSrc));
            }

            log.info("News successfully crawled. Total articles: {}", crawlers.size());

        } catch (IOException e) {
            log.error("Error crawling news", e);
            throw e;  // 예외를 상위로 던져서 적절히 처리
        }
        return crawlers;
    }
}
