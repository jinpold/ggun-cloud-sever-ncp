package store.ggun.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.user.domain.StockArticleModel;

@Repository
public interface StockArticleRepository extends JpaRepository<StockArticleModel,Long>, StockArticleDao{

}
