package store.ggun.account.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.ggun.account.domain.dto.AccHistoryDto;
import store.ggun.account.domain.dto.Messenger;
import store.ggun.account.domain.model.AccHistoryModel;
import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.repository.AccHistoryRepository;
import store.ggun.account.repository.AccountRepository;
import store.ggun.account.service.AccHistoryService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccHistoryServiceImpl implements AccHistoryService {

    private final AccHistoryRepository repository;
    private final AccountRepository accountRepository;
    @Override
    public Messenger save(AccHistoryDto accHistoryDto) {

        AccountModel accountModel = accountRepository.findById(accHistoryDto.getAccount()).get();

        AccHistoryModel accHistoryModel = repository.save(dtoToEntity(accHistoryDto,accountModel));

        return Messenger.builder()
                .message(accHistoryModel instanceof AccHistoryModel ? "SUCCESS" : "FAIURE")
                .build();
    }

    @Override
    public Messenger deleteById(Long id) {
        return null;
    }

    @Override
    public Optional<AccHistoryDto> modify(AccHistoryDto accHistoryDto) {
        return Optional.empty();
    }

    @Override
    public List<AccHistoryDto> findAll() {
        return null;
    }

    @Override
    public Optional<AccHistoryDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public List<AccHistoryDto> findByAccount(Long id) {
//
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("regDate"));
//        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//        Page<AccHistoryDto> a = repository.findByAccountId(id,pageable).map(i->entityToDto(i));
//        log.info("확인 {}" , a);
        return repository.findByAccountId(id).stream().map(i->entityToDto(i)).toList();
    }
}
