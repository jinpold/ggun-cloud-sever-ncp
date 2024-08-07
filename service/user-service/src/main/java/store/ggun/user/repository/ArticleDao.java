package store.ggun.user.repository;

import store.ggun.user.domain.ArticleDto;
import store.ggun.user.domain.ArticleModel;

import java.util.List;

public interface ArticleDao {
    public List<ArticleDto> findByBoardIdDao(String boardId);

    public ArticleModel modifyArticle(ArticleDto model, String id);
}
