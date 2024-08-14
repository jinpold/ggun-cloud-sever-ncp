package store.ggun.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.ggun.user.domain.ItemModel;
import store.ggun.user.domain.QItemModel;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ItemDaoImpl implements ItemDao{
    private final JPAQueryFactory factory;
    private final QItemModel item = QItemModel.itemModel;


    @Override
    public List<ItemModel> findDetail(String search, Date sdate, Date edate) {
        return factory
                .selectFrom(item)
                .where(item.item.eq(search))
                .where(item.date.between(sdate,edate))
                .fetch();
    }
}