package store.ggun.account.repository;

import org.springframework.stereotype.Repository;
import store.ggun.account.domain.model.TradeModel;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TradeDao {


    List<TradeModel> getListByProductName(String prdtName);

    List<TradeModel> getListByDate(LocalDateTime start,LocalDateTime end);
}
