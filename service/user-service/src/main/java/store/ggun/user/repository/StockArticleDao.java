package store.ggun.user.repository;

import store.ggun.user.domain.StockArticleDto;
import store.ggun.user.domain.StockArticleDto2;

import java.util.List;

public interface StockArticleDao {
    List<StockArticleDto> findAllQuery(Long boardId);

    Long findByArticleIdQuery(Long articleId);

    void modify(StockArticleDto articleId);

    Long findBySoketQuery(String stockBoards);

    List<StockArticleDto2> findAllTopQuery();
}
