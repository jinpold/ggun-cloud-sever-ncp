package store.ggun.admin.repository.jpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.admin.domain.model.AdminTransactionModel;
import store.ggun.admin.repository.dao.AdminTransactionDao;

@Repository
public interface AdminTransactionRepository extends JpaRepository<AdminTransactionModel, Long>, AdminTransactionDao {
}

