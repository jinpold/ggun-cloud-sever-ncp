package store.ggun.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.ggun.user.domain.BoardDto;
import store.ggun.user.domain.BoardModel;
import store.ggun.user.domain.QBoardDto;
import store.ggun.user.domain.QBoardModel;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao{
    private final JPAQueryFactory queryFactory;
    private final QBoardModel qBoard = QBoardModel.boardModel;

    @Override
    public List<BoardDto> findAllR() {
        return queryFactory
                .select(new QBoardDto(qBoard.id, qBoard.title,qBoard.content,qBoard.description))
                .from(qBoard)
                .fetch();
    }
}
