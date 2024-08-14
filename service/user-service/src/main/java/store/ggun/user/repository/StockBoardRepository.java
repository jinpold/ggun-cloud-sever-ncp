package store.ggun.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.user.domain.Messenger;
import store.ggun.user.domain.StockBoardModel;

@Repository
public interface StockBoardRepository extends JpaRepository<StockBoardModel,Long>, StockBoardDao{
    boolean existsByTitle(String title);

    void deleteByTitle(String title);
}
