package store.ggun.account.repository;


import store.ggun.account.domain.dto.NonCcldDto;
import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.model.NonCcldModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NonCcldRepository extends JpaRepository<NonCcldModel,Long> {
    List<NonCcldDto> findByAccount(AccountModel accountModel);

}
