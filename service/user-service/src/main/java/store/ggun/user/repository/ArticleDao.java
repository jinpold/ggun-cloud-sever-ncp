package store.ggun.user.repository;

import com.querydsl.jpa.impl.JPAQuery;
import store.ggun.user.domain.ArticleDto;
import store.ggun.user.domain.ArticleDto2;

import java.util.List;

public interface ArticleDao {
    List<ArticleDto> findByBoardIdDao(Long boardId);

    ArticleDto2 findByArticleId(Long model);

    ArticleDto findByArticleIdR(Long id);

    Long findByArticleIdQuery(Long id);
}
