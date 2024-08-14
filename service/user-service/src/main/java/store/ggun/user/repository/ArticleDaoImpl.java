package store.ggun.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.ggun.user.domain.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ArticleDaoImpl implements ArticleDao{
    private final JPAQueryFactory queryFactory;
    private final QBoardModel qBoard = QBoardModel.boardModel;
    private final QArticleModel qArticle = QArticleModel.articleModel;
    private final QUserModel qUser = QUserModel.userModel;

    public List<ArticleDto> findByBoardIdDao(Long boardId) {
        return queryFactory.select(
                        new QArticleDto(
                                qArticle.id,
                                qArticle.title,
                                qArticle.content,
                                qArticle.writerId.username,
                                qArticle.boardId.id,
                                qArticle.answer))
                .from(qArticle)
                .join(qBoard).on(qArticle.boardId.id.eq(qBoard.id))
                .join(qUser).on(qArticle.writerId.id.eq(qUser.id))
                .where(qArticle.boardId.id.eq(boardId))
                .fetch();
        }

    @Override
    public ArticleDto2 findByArticleId(Long model) {
        return queryFactory.select(
                new QArticleDto2(
                        qArticle.id,
                        qArticle.title,
                        qArticle.content,
                        qArticle.writerId.id,
                        qArticle.boardId.id,
                        qArticle.answer))
                .from(qArticle)
                .join(qBoard).on(qArticle.boardId.id.eq(qBoard.id))
                .join(qUser).on(qArticle.writerId.id.eq(qUser.id))
                .where(qArticle.id.eq(model))
                .fetchOne();
    }

    @Override
    public ArticleDto findByArticleIdR(Long id) {
        return queryFactory.select(
                        new QArticleDto(
                                qArticle.id,
                                qArticle.title,
                                qArticle.content,
                                qArticle.writerId.username,
                                qArticle.boardId.id,
                                qArticle.answer))
                .from(qArticle)
                .join(qBoard).on(qArticle.boardId.id.eq(qBoard.id))
                .join(qUser).on(qArticle.writerId.id.eq(qUser.id))
                .where(qArticle.id.eq(id))
                .fetchOne();
    }

    @Override
    public Long findByArticleIdQuery(Long id) {
        return queryFactory
                .select(qArticle.writerId.id)
                .from(qArticle)
                .join(qUser).on(qArticle.writerId.id.eq(qUser.id))
                .where(qArticle.id.eq(id))
                .fetchOne();
    }
}
