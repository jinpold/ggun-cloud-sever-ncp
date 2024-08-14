package store.ggun.account.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.ggun.account.domain.dto.Messenger;
import store.ggun.account.domain.dto.NonCcldDto;
import store.ggun.account.repository.AccountRepository;
import store.ggun.account.repository.NonCcldRepository;
import store.ggun.account.service.NonCcldService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NonCcldServiceImpl implements NonCcldService {

    private final NonCcldRepository repository;
    private final AccountRepository accountRepository;

    @Override
    public Messenger save(NonCcldDto nonCcldDto) {
        return null;
    }

    @Override
    public Messenger deleteById(Long id) {
        return null;
    }

    @Override
    public Optional<NonCcldDto> modify(NonCcldDto nonCcldDto) {
        return Optional.empty();
    }

    @Override
    public List<NonCcldDto> findAll() {
        return null;
    }

    @Override
    public Optional<NonCcldDto> findById(Long id) {
        return Optional.ofNullable(
                entityToDto(Objects.requireNonNull(repository.findById(id).orElse(null))));
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<NonCcldDto> findByAccount(Long id) {

        return repository.findByAccount(accountRepository.findById(id).get());
    }
}
