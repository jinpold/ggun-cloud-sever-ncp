package store.ggun.user.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.ggun.user.domain.Messenger;
import store.ggun.user.domain.StockArticleDto;
import store.ggun.user.domain.StockArticleDto2;
import store.ggun.user.repository.StockArticleRepository;
import store.ggun.user.service.StockArticleService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockArticleServiceImpl implements StockArticleService {
    private final StockArticleRepository repository;

    @Override
    public List<StockArticleDto> findAllList(Long boardId) {
        return repository.findAllQuery(boardId);
    }

    @Override
    public Messenger save(StockArticleDto model, Long id) {
        repository.save(dtoToEntity(model,id));
        return Messenger.builder()
                .message("SUCCESS")
                .build();
    }

    @Transactional
    @Override
    public Messenger deleteById(Long articleId, Long id) {
        if(id.equals(repository.findByArticleIdQuery(articleId))){
            repository.deleteById(articleId);
            if(!repository.existsById(articleId)){
                return Messenger.builder().message("SUCCESS").build();
            }
        }
        return Messenger.builder().message("FAIL").build();
    }

    @Override
    public Messenger modifyById(StockArticleDto article, Long id) {
        if (id.equals(repository.findByArticleIdQuery(article.getId()))) {
            repository.modify(article);
            return Messenger.builder().message("SUCCESS").build();
        }
        return Messenger.builder().message("FAIL").build();
    }

    @Override
    public List<StockArticleDto2> findAllListlistTop() {
        return repository.findAllTopQuery();
    }
}
