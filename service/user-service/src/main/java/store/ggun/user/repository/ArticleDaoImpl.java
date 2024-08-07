package store.ggun.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.ggun.user.domain.ArticleDto;
import store.ggun.user.domain.ArticleModel;
import store.ggun.user.domain.QArticleModel;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ArticleDaoImpl implements ArticleDao{
    private final JPAQueryFactory queryFactory;
    private final QArticleModel qArticle = QArticleModel.articleModel;

    public List<ArticleDto> findByBoardIdDao(String boardId) {
        return queryFactory
                .select(Projections.constructor(ArticleDto.class,
                        qArticle.id,
                        qArticle.title,
                        qArticle.content,
                        qArticle.writerId.username,
                        qArticle.boardId,
                        qArticle.answer))
                .from(qArticle)
                .where(qArticle.boardId.eq(boardId))
                .fetch();
    }

    @Override
    public ArticleModel modifyArticle(ArticleDto model, String id) {
        JPAQuery<ArticleModel> query = queryFactory.selectFrom(qArticle);
        query.where(qArticle.boardId.eq(model.getBoardId()));
        List<ArticleModel> articles = query.fetch();
        ArticleModel article = new ArticleModel();
        article.setTitle(model.getTitle()!=null?articles.get(0).getTitle():null);
        article.setTitle(model.getContent()!=null?articles.get(0).getContent():null);
        return article;
    }
}
