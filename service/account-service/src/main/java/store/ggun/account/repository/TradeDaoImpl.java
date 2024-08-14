package store.ggun.account.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.ggun.account.domain.model.QTradeModel;
import store.ggun.account.domain.model.TradeModel;

import java.time.LocalDateTime;
import java.util.List;



@RequiredArgsConstructor
@Slf4j
public class TradeDaoImpl implements TradeDao{
    private final JPAQueryFactory factory;
    private final QTradeModel trade = QTradeModel.tradeModel;
    @Override
    public List<TradeModel> getListByProductName(String prdtName) {

        return factory.selectFrom(trade)
                .where(trade.prdtName.eq(prdtName))
                .fetch();

    }

    @Override
    public List<TradeModel> getListByDate(LocalDateTime start,LocalDateTime end) {
        return factory.selectFrom(trade)
                .where(trade.regDate.between(start,end))
                .fetch();
    }
}
