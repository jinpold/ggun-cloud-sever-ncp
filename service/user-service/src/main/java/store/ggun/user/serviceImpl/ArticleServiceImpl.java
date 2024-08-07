package store.ggun.user.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.ggun.user.domain.ArticleDto;
import store.ggun.user.domain.ArticleModel;
import store.ggun.user.domain.Messenger;
import store.ggun.user.domain.UserModel;
import store.ggun.user.repository.ArticleRepository;
import store.ggun.user.service.ArticleService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repository;

    @Override
    public ArticleModel save(ArticleDto model, String id) {
        Long writerId = Long.valueOf(id);
        ArticleModel article = ArticleModel.builder()
                .title(model.getTitle())
                .content(model.getContent())
                .writerId(UserModel.builder()
                        .id(writerId)
                        .build())
                .boardId(model.getBoardId())
                .build();
        repository.save(article);
        return null;
    }

    @Override
    public List<ArticleDto> findAllByBoardId(String boardId) {
        return repository.findByBoardIdDao(boardId);
    }

    @Override
    public ArticleModel modify(ArticleDto model, String id) {
        Long writerId = Long.valueOf(id);
        ArticleModel article=writerId.equals(repository.findByBoardIdQuery(model.getId()))?repository.modifyArticle(model,id): null;
        boolean a = model.getTitle() == null || model.getTitle().equals(article.getTitle());
        boolean b = model.getContent() == null || model.getContent().equals(article.getContent());
        return a&&b?article:null;
    }

    @Override
    public Messenger delete(Long model, String id) {
        Long writerId = Long.valueOf(id);
        if(writerId.equals(repository.findByBoardIdQuery(model)))repository.deleteQuery(model);
        return Messenger.builder()
                .message(repository.existsById(model)?"FAIL":"SUCCESS")
                .build();
    }
}
