//package store.ggun.chat.repository;
//
//import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
//import org.springframework.stereotype.Repository;
//import reactor.core.publisher.Mono;
//
//@Repository
//public interface TokenRepository extends ReactiveMongoRepository<TokenModel, String> {
//
//    Mono<TokenModel> findByEmail(String email);
//}