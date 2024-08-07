package store.ggun.admin.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.admin.domain.model.TransactionModel;
import store.ggun.admin.repository.dao.TransactionDao;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long>, TransactionDao {

}

