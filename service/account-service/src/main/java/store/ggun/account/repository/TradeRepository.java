package store.ggun.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.model.TradeModel;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<TradeModel,Long>, TradeDao{


    List<TradeModel> findByAccount(AccountModel accountModel);

}
