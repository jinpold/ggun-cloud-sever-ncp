package store.ggun.account.repository;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.ggun.account.domain.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    List<AccountModel> findByUserId(Long userModel);

    Boolean existsByAcno(String acno);

    @Modifying
    @Query("update accounts set balance = :balance where id = :id")
    int modifyBalanceById(@Param("id") Long id,@Param("balance") Long balance);

    @Modifying
    @Query("update accounts set bank = :bank,refundAcno = :refundAcno where id = :id")
    int modifyAccountById(@Param("id") Long id,@Param("bank") String bank,@Param("refundAcno") String refundAcno);

    boolean existsByUserIdAndAcType(Long id, String acType);

    List<AccountModel> findByAcType(String acType);
}
