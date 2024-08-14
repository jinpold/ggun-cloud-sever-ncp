package store.ggun.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.user.domain.StockCommentModel;

@Repository
public interface StockCommentRepository extends JpaRepository<StockCommentModel,Long>, StockCommentDao{
}
