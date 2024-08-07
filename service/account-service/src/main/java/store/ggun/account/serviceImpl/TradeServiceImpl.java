package store.ggun.account.serviceImpl;

import store.ggun.account.domain.model.AccHistoryModel;
import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.dto.TradeDto;
import store.ggun.account.domain.model.TradeModel;
import store.ggun.account.repository.TradeRepository;
import store.ggun.account.repository.AccountRepository;
import store.ggun.account.domain.dto.Messenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.ggun.account.service.TradeService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeServiceImpl implements TradeService {
    private final TradeRepository repository;
    private final AccountRepository accountRepository;
    @Override
    public Messenger save(TradeDto tradeDto) {
        AccountModel accountModel = accountRepository.findById(tradeDto.getAccount()).get();

        TradeModel tradeModel = repository.save(dtoToEntity(tradeDto,accountModel));

        return Messenger.builder()
                .message(tradeModel instanceof TradeModel ? "SUCCESS" : "FAIURE")
                .build();
    }

    @Override
    public Messenger deleteById(Long id) {
        return null;
    }

    @Override
    public Optional<TradeDto> modify(TradeDto tradeDto) {
        return Optional.empty();
    }

    @Override
    public List<TradeDto> findAll() {
        return repository.findAll()
                .stream().map(i->entityToDto(i)).toList();
    }

    @Override
    public Optional<TradeDto> findById(Long id) {
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
    public List<TradeDto> findByAcno(Long id) {
        AccountModel ac = accountRepository.findById(id).get();
        return repository.findByAccount(ac)
                .stream().map(i->entityToDto(i)).toList();
    }

    @Override
    public List<TradeDto> findByProductName(String prdtName) {
        return repository.getListByProductName(prdtName)
                .stream().map(i->entityToDto(i)).toList();
    }


    @Override
    public List<TradeDto> findByDate(String start,String end) {
        LocalDateTime startOfDay = LocalDateTime.parse(start).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.parse(end).toLocalDate().atTime(LocalTime.MAX);

        log.info("start: {}",startOfDay);
        log.info("end: {}",endOfDay);
        return repository.getListByDate(startOfDay,endOfDay).stream().map(i->entityToDto(i)).toList();
    }

}