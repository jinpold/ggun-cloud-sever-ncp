package store.ggun.user.repository;

import store.ggun.user.domain.StockArticleDto;
import store.ggun.user.domain.StockCommentDto;

import java.util.List;

public interface StockCommentDao {
    List<StockCommentDto> findAllQuery(Long articleId);

    Long findByArticleIdQuery(Long articleId);

    void modify(StockCommentDto articleId);

    Long findByArticleIdFind(String stockArticles);
}
