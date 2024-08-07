package store.ggun.account.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.ggun.account.domain.dto.OwnStockDto;
import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.model.OwnStockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnStockRepository extends JpaRepository<OwnStockModel,Long> {



    Optional<OwnStockModel> findByPdnoAndAccountIdAndTradeType(String pdno, Long account, String tradeType);


    int deleteByPdnoAndAccountIdAndTradeType(String pdno, Long account,String tradeType);

    List<OwnStockModel> findByAccountId(Long id);


    @Modifying
    @Query("update own_stocks set pdQty = :pdQty,avgPrvs=:avgPrvs where id = :id")
    int modifyStock(@Param("id")long id, @Param("pdQty")long pdQty, @Param("avgPrvs")long avgPrvs);

    @Modifying
    @Query("update own_stocks set pdQty = :pdQty where id = :id")
    int modifyStock(@Param("id")long id, @Param("pdQty")long pdQty);

    Optional<OwnStockModel> findByAccountIdAndPdno(Long account, String pdno);

    boolean existsByAccount(AccountModel account);
}
