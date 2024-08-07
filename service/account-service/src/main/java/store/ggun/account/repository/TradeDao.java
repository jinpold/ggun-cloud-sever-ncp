package store.ggun.account.repository;

import store.ggun.account.domain.model.TradeModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface TradeDao {


    List<TradeModel> getListByProductName(String prdtName);

    List<TradeModel> getListByDate(LocalDateTime start,LocalDateTime end);
}
