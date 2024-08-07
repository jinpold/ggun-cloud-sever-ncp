package store.ggun.chat.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import store.ggun.chat.domain.TradesHistriesModel;

@Repository
public interface TradesHistoriesRepository extends ReactiveMongoRepository<TradesHistriesModel, Long>/* , QuerydslPredicateExecutor<TradesHistriesModel>*/ {
}
