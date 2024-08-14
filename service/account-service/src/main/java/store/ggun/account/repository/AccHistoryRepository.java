package store.ggun.account.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.account.domain.model.AccHistoryModel;

@Repository
public interface AccHistoryRepository extends JpaRepository<AccHistoryModel,Long> {


//    List<AccHistoryModel> findByAccountId(Long id);
    Page<AccHistoryModel> findByAccountId(Long id, Pageable pageable);
}
