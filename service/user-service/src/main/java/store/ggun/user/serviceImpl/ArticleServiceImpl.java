package store.ggun.user.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import store.ggun.user.domain.*;
import store.ggun.user.repository.ArticleRepository;
import store.ggun.user.repository.BoardRepository;
import store.ggun.user.service.ArticleService;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository repository;
    private final BoardRepository boardRepository;

    @Override
    public ArticleModel save(ArticleDto model, Long id) {
        ArticleModel article = ArticleModel.builder()
                .title(model.getTitle())
                .content(model.getContent())
                .writerId(UserModel.builder()
                        .id(id)
                        .build())
                .boardId(BoardModel.builder()
                        .id(model.getBoardId())
                        .build())
                .build();
        repository.save(article);
        return null;
    }

    @Override
    public List<ArticleDto> findAllByBoardId(Long boardId) {
        return repository.findByBoardIdDao(boardId);
    }

    @Override
    public ArticleDto modify(ArticleDto model, Long id) {
        if (id.equals(repository.findByArticleIdQuery(model.getId()))) {
            ArticleDto2 article = repository.findByArticleId(model.getId());
            if(model.getTitle()!=null)article.setTitle(model.getTitle());
            if(model.getContent()!=null)article.setContent(model.getContent());
            repository.save(dto2ToEntity(article));
            return repository.findByArticleIdR(model.getId());
        } else {
            return null;
        }
    }

    @Override
    public Messenger delete(Long articleId, Long id) {
        if(Objects.equals(id, repository.findByArticleIdQuery(articleId)))repository.deleteById(articleId);
        return Messenger.builder()
                .message(repository.existsById(articleId)?"FAIL":"SUCCESS")
                .build();
    }
}
