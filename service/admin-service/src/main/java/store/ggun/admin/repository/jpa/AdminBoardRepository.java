package store.ggun.admin.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.ggun.admin.domain.model.AdminBoardModel;

import java.util.List;

@Repository
public interface AdminBoardRepository extends JpaRepository<AdminBoardModel, Long>{

    List<AdminBoardModel> getAllByOrderByContentDesc();
}