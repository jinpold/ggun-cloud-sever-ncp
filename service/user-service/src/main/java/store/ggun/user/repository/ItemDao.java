package store.ggun.user.repository;

import org.springframework.stereotype.Repository;
import store.ggun.user.domain.ItemModel;

import java.util.Date;
import java.util.List;

@Repository
public interface ItemDao {
    List<ItemModel> findDetail(String search, Date sdate, Date edate);

}
