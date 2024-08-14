package store.ggun.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.ggun.user.domain.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class StockCommentDaoImpl implements StockCommentDao{
    private final JPAQueryFactory queryFactory;
    private final QStockCommentModel qStockCommentModel = QStockCommentModel.stockCommentModel;
    private final QUserModel qUserModel = QUserModel.userModel;
    private final QStockArticleModel qStockArticleModel = QStockArticleModel.stockArticleModel;

    @Override
    public List<StockCommentDto> findAllQuery(Long articleId) {
        return queryFactory
                .select(new QStockCommentDto(
                        qStockCommentModel.id,
                        qStockCommentModel.comment,
                        qStockCommentModel.writerId.username,
                        qStockCommentModel.regDate,
                        qStockCommentModel.stockArticles.id
                        ))
                .from(qStockCommentModel)
                .join(qUserModel).on(qStockCommentModel.writerId.id.eq(qUserModel.id))
                .join(qStockArticleModel).on(qStockCommentModel.stockArticles.id.eq(qStockArticleModel.id))
                .where(qStockCommentModel.stockArticles.id.eq(articleId))
                .fetch();
    }

    @Override
    public Long findByArticleIdQuery(Long articleId) {
        return queryFactory
                .select(qStockCommentModel.writerId.id)
                .from(qStockCommentModel)
                .join(qUserModel).on(qStockCommentModel.writerId.id.eq(qUserModel.id))
                .where(qStockCommentModel.id.eq(articleId))
                .fetchFirst();
    }

    @Transactional
    @Override
    public void modify(StockCommentDto article) {
        JPAUpdateClause updateClause = queryFactory.update(qStockCommentModel);
        if(article.getComment()!=null){
            updateClause.set(qStockCommentModel.comment, article.getComment());
        }
        updateClause.set(qStockCommentModel.modDate, LocalDateTime.now());
                updateClause.where(qStockCommentModel.id.eq(article.getId())).execute();
    }

    @Override
    public Long findByArticleIdFind(String stockArticles) {
        return queryFactory
                .select(qStockArticleModel.id)
                .from(qStockArticleModel)
                .where(qStockArticleModel.title.eq(stockArticles))
                .fetchOne();
    }
}
