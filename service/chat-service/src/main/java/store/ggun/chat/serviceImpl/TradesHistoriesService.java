package store.ggun.chat.serviceImpl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.chat.domain.Messenger;
import store.ggun.chat.domain.TradesHistriesModel;
import store.ggun.chat.repository.TradesHistoriesRepository;


@Slf4j
@RequiredArgsConstructor
@Service
public class TradesHistoriesService {

    private final TradesHistoriesRepository repository;

    public Mono<Messenger> save(TradesHistriesModel model) {
        return repository.save(model).flatMap(i -> Mono.just(Messenger.builder().message("SUCCESS").build()))
                .switchIfEmpty(Mono.just(Messenger.builder().message("FAILURE").build()));
    }
    public Flux<TradesHistriesModel> getAllHistory(TradesHistriesModel model) {
        return repository.findAll();
    }
}
