package store.ggun.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.ggun.user.domain.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class StockArticleDaoImpl implements StockArticleDao{
    private final JPAQueryFactory queryFactory;
    private final QStockArticleModel qarticle = QStockArticleModel.stockArticleModel;
    private final QUserModel qUserModel = QUserModel.userModel;
    private final QStockBoardModel qStockBoardModel = QStockBoardModel.stockBoardModel;

    @Override
    public List<StockArticleDto> findAllQuery(Long boardId) {
        return queryFactory
                .select(new QStockArticleDto(
                        qarticle.id,
                        qarticle.title,
                        qarticle.content,
                        qarticle.writerId.username,
                        qarticle.stockBoards.id))
                .from(qarticle)
                .join(qUserModel).on(qarticle.writerId.id.eq(qUserModel.id))
                .join(qStockBoardModel).on(qarticle.stockBoards.id.eq(qStockBoardModel.id))
                .where(qarticle.stockBoards.id.eq(boardId))
                .from()
                .fetch();
    }

    @Override
    public Long findByArticleIdQuery(Long articleId) {
        return queryFactory
                .select(qarticle.writerId.id)
                .from(qarticle)
                .join(qUserModel).on(qarticle.writerId.id.eq(qUserModel.id))
                .where(qarticle.id.eq(articleId))
                .fetchOne();
    }

    @Transactional
    @Override
    public void modify(StockArticleDto article) {
        JPAUpdateClause updateClause = queryFactory.update(qarticle);
        if (article.getTitle()!=null){
            updateClause.set(qarticle.title, article.getTitle());
        }
        if (article.getContent()!=null){
            updateClause.set(qarticle.content, article.getContent());
        }
        updateClause.where(qarticle.id.eq(article.getId())).execute();
    }

    @Override
    public Long findBySoketQuery(String stockBoards) {
        return queryFactory
                .select(qStockBoardModel.id)
                .from(qStockBoardModel)
                .where(qStockBoardModel.title.eq(stockBoards))
                .fetchOne();
    }

    @Override
    public List<StockArticleDto2> findAllTopQuery() {
        return queryFactory
                .select(new QStockArticleDto2(
                        qarticle.id,
                        qarticle.title,
                        qarticle.content,
                        qarticle.writerId.username,
                        qarticle.stockBoards.title))
                .from(qarticle)
                .join(qUserModel).on(qarticle.writerId.id.eq(qUserModel.id))
                .join(qStockBoardModel).on(qarticle.stockBoards.id.eq(qStockBoardModel.id))
                .from()
                .orderBy(qarticle.id.desc())
                .limit(5)
                .fetch();
    }
}
