package store.ggun.user.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.ggun.user.domain.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class StockBoardDaoImpl implements StockBoardDao{
    private final JPAQueryFactory queryFactory;
    private final QStockBoardModel qBoard = QStockBoardModel.stockBoardModel;

    @Override
    public List<StockBoardDto> findAllQuery() {
        return queryFactory
                .select(new QStockBoardDto(
                        qBoard.id,
                        qBoard.title,
                        qBoard.content,
                        qBoard.description))
                .from(qBoard).fetch();
    }

    @Override
    public Optional<StockBoardDto> findByTitleQuery(String title) {
        return Optional.ofNullable(queryFactory
                .select(new QStockBoardDto(
                        qBoard.id,
                        qBoard.title,
                        qBoard.content,
                        qBoard.description))
                .from(qBoard)
                .where(qBoard.title.eq(title))
                .fetchOne());
    }

    @Transactional
    @Override
    public void modify(StockBoardDto model) {
        JPAUpdateClause updateClause = queryFactory.update(qBoard);
        if(model.getTitle()!=null){
            updateClause.set(qBoard.title, model.getTitle());
        }
        if(model.getContent()!=null){
            updateClause.set(qBoard.content, model.getContent());
        }
        if(model.getDescription()!=null){
            updateClause.set(qBoard.description, model.getDescription());
        }
        updateClause.where(qBoard.id.eq(model.getId())).execute();
    }
}
