package store.ggun.chat.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import store.ggun.chat.domain.NotificationModel;


@Repository
public interface NotificationRepository extends ReactiveMongoRepository<NotificationModel, Long> {
    Flux<NotificationModel> findByRoomId(String roomId);
}
