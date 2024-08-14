package store.ggun.user.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import store.ggun.user.domain.ArticleModel;

public interface ArticleRepository extends JpaRepository<ArticleModel, Long>, ArticleDao {

    void deleteById(Long id);
}
