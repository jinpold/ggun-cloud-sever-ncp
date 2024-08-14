package store.ggun.alarm.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import store.ggun.alarm.domain.model.CounterDocument;

public interface CounterRepository extends ReactiveMongoRepository<CounterDocument, String> {
}
