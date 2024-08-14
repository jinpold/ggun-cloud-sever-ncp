package store.ggun.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import store.ggun.user.domain.BoardDto;
import store.ggun.user.domain.BoardModel;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardModel, Long>, BoardDao {
    boolean existsByTitle(String title);

    @Query(value = "select b.boards_id from boards b where b.title = :boardTitle", nativeQuery = true)
    Long findByTitleaa(@RequestParam String boardTitle);
}
