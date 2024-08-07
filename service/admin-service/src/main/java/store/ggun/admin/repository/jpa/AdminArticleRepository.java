package store.ggun.admin.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.ggun.admin.domain.model.AdminArticleModel;

import java.util.List;

@Repository
public interface AdminArticleRepository extends JpaRepository<AdminArticleModel, Long> {

    @Query("select a "
            + "from adminArticles a where a.adminBoardModel.id = :boardId order by a.id desc")
    List<AdminArticleModel> getArticleByBoardId(@Param("boardId") Long boardId);

}
