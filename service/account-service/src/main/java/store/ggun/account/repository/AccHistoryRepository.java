package store.ggun.account.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.ggun.account.domain.dto.AccHistoryDto;
import store.ggun.account.domain.model.AccHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccHistoryRepository extends JpaRepository<AccHistoryModel,Long> {


    Page<AccHistoryModel> findByAccountId(Long id, Pageable pageable);
}
