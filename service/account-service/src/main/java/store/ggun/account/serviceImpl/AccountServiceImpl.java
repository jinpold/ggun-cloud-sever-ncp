package store.ggun.account.serviceImpl;

import store.ggun.account.domain.model.AccHistoryModel;
import store.ggun.account.repository.AccHistoryRepository;
import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.dto.AccountDto;
import store.ggun.account.repository.AccountRepository;
import store.ggun.account.domain.dto.Messenger;
import store.ggun.account.repository.OwnStockRepository;
import store.ggun.account.service.AccountService;
import store.ggun.account.service.OwnStockService;
import store.ggun.account.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccHistoryRepository accHistoryRepository;
    private final UtilService util;
    private final PasswordEncoder passwordEncoder;
    private final OwnStockRepository ownStockRepository;

    @Override
    public Messenger save(AccountDto accountDto) {

        return accountDto.getAcType().equals("02") ?
                checkAiAc(accountDto.getUserId(), accountDto.getAcType()) ?
                        Messenger.builder().message("AI 계좌가 존재합니다.").build() : createAc(accountDto)
                : createAc(accountDto);
    }

    private Messenger createAc(AccountDto accountDto) {
        String acno = util.createAccountNumber(accountDto.getAcType());
        while (repository.existsByAcno(acno)) {
            acno = util.createAccountNumber(accountDto.getAcType());
        }
        AccountModel accountModel = repository.save(AccountModel.builder()
                .id(accountDto.getId())
                .acno(acno)
                .acpw(passwordEncoder.encode(accountDto.getAcpw()))
                .balance(0L)
                .refundAcno(accountDto.getRefundAcno())
                .bank(accountDto.getBank())
                .acType(accountDto.getAcType())
                .userId(accountDto.getUserId())
                .build());
        return Messenger.builder()
                .message(accountModel instanceof AccountModel ? "SUCCESS" : "FAIURE")
                .build();
    }

    private boolean checkAiAc(Long id, String acType) {
        return repository.existsByUserIdAndAcType(id, acType);
    }

    @Override
    public Messenger deleteById(Long id) {
        AccountModel accountModel = repository.findById(id).get();
        boolean balance = accountModel.getBalance()==0;

        if(!ownStockRepository.existsByAccount(accountModel) && balance ){
            repository.deleteById(id);
            return Messenger.builder()
                    .message(accountModel instanceof AccountModel ? "SUCCESS" : "FAIURE")
                    .build();
        }  else {
            return Messenger.builder()
                    .message("계좌에 자산이 있습니다.")
                    .build();
        }
    }

    @Override
    public Optional<AccountDto> modify(AccountDto accountDto) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Messenger modifyByAccount(AccountDto accountDto) {
        return Messenger.builder()
                .message(repository.modifyAccountById(accountDto.getId(),accountDto.getBank(),accountDto.getRefundAcno())== 0 ?
                        "FAIURE" : "SUCCESS")
                .build();
    }

    @Override
    public List<AccountDto> findAll() {
        return repository.findAll().stream().map(i->entityToDto(i)).toList();
    }

    @Override
    public Optional<AccountDto> findById(Long id) {

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
    public List<AccountDto> findByUser(Long id) {
        return repository.findByUserId(id)
                .stream().map(i -> entityToDto(i)).toList();
    }

    @Override
    @Transactional
    public Messenger deposit(AccountDto accountDto) {
        AccountModel ac = repository.findById(accountDto.getId()).get();

//        ac.setBalance(ac.getBalance() + accountDto.getBalance());
//
//        repository.save(ac);
        long txBalance = ac.getBalance() + accountDto.getBalance();
        int result = repository.modifyBalanceById(ac.getId(), txBalance);

        accHistoryRepository.save(AccHistoryModel.builder()
                .balance(accountDto.getBalance())
                .tradeType(accountDto.getTradeType())
                .briefs(accountDto.getBriefs())
                .imp_uid(accountDto.getPaymentUid())
                .account(ac)
                .build());

        return Messenger.builder()
                .message(result == 1 ? "SUCCESS" : "FAIURE")
                .build();
    }

    @Override
    @Transactional
    public Messenger withdraw(AccountDto accountDto) {
        AccountModel ac = repository.findById(accountDto.getId()).get();
        long txBalance = ac.getBalance() - accountDto.getBalance();

        if (passwordEncoder.matches(accountDto.getAcpw(), ac.getAcpw()) || accountDto.getAcpw().equals(ac.getAcpw())) {
            if (txBalance >= 0) {
                int result = repository.modifyBalanceById(ac.getId(), txBalance);

                accHistoryRepository.save(AccHistoryModel.builder()
                        .balance(accountDto.getBalance())
                        .tradeType(accountDto.getTradeType())
                        .briefs(accountDto.getBriefs())
                        .imp_uid(accountDto.getPaymentUid())
                        .account(ac)
                        .build());
                return Messenger.builder()
                        .message(result == 1 ? "SUCCESS" : "FAIURE")
                        .build();
            } else {
                return Messenger.builder()
                        .message("잔액이 부족합니다.")
                        .build();
            }
        } else {
            return Messenger.builder()
                    .message("비밀번호를 다시 입력바랍니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public Messenger acTransfer(AccountDto accountDto) {

        AccountDto acDto = AccountDto.builder()
                .id(accountDto.getId())
                .balance(accountDto.getBalance())
                .tradeType("출금")
                .acpw(accountDto.getAcpw())
                .briefs(accountDto.getBriefs() + " 송금")
                .build();

        AccountDto rcDto = AccountDto.builder()
                .id(accountDto.getReceiveAcId())
                .balance(accountDto.getBalance())
                .tradeType("입금")
                .briefs(accountDto.getBriefs() + " 송금")
                .build();

        if (withdraw(acDto).getMessage().equals("SUCCESS")) {
            return Messenger.builder()
                    .message(deposit(rcDto).getMessage().equals("SUCCESS") ? "SUCCESS" : "FAIURE")
                    .build();
        } else {
            return Messenger.builder()
                    .message("FAIURE")
                    .build();
        }
    }

    @Override
    public List<AccountDto> getAIAcno() {
        return repository.findByAcType("02")
                .stream().map(i -> entityToDto(i)).toList();
    }


}
