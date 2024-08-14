package store.ggun.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.ggun.user.domain.ItemModel;

import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel,Long>, ItemDao{
    List<ItemModel> findByOrderByVolume();

    @Query(value = "select i from items i where i.item=:search and i.date between :state and :date")
    List<ItemModel> findDetail1(@Param("search") String search, @Param("state") Date sdate, @Param("date") Date edate);

}