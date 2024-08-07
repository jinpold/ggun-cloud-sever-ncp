package store.ggun.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import store.ggun.user.domain.ArticleModel;

public interface ArticleRepository extends JpaRepository<ArticleModel, Long>, ArticleDao {
    @Query(value = "select a.writerId from articles a where a.boardId=:id", nativeQuery = true)
    Long findByBoardIdQuery(long id);
    @Query(value = "delete from articles a where a.boardId=:id", nativeQuery = true)
    void deleteQuery(Long id);
}
