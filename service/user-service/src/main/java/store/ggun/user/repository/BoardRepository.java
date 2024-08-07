package store.ggun.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.user.domain.BoardModel;

@Repository
public interface BoardRepository extends JpaRepository<BoardModel, Long> {
    boolean existsByTitle(String title);
}
