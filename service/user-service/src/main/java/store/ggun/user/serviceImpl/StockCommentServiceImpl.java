package store.ggun.user.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.ggun.user.domain.Messenger;
import store.ggun.user.domain.StockArticleDto;
import store.ggun.user.domain.StockCommentDto;
import store.ggun.user.repository.StockCommentRepository;
import store.ggun.user.service.StockCommentService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockCommentServiceImpl implements StockCommentService {
    private final StockCommentRepository repository;

    @Override
    public List<StockCommentDto> findAllList(Long articleId) {
        return repository.findAllQuery(articleId);
    }

    @Transactional
    @Override
    public Messenger save(StockCommentDto model, Long id) {
        repository.save(dtoToEntity(model,id));
        return Messenger.builder()
                .message("SUCCESS")
                .build();
    }

    @Transactional
    @Override
    public Messenger deleteById(Long commentId, Long id) {
        if(id.equals(repository.findByArticleIdQuery(commentId))){
            repository.deleteById(commentId);
            if(!repository.existsById(commentId)){
                return Messenger.builder().message("SUCCESS").build();
            }
        }
        return Messenger.builder().message("FAIL").build();
    }

    @Override
    public Messenger modifyById(StockCommentDto article, Long id) {
        if(id.equals(repository.findByArticleIdQuery(article.getId()))){
            repository.modify(article);
                return Messenger.builder().message("SUCCESS").build();
        }
        return Messenger.builder().message("FAIL").build();
    }
}
