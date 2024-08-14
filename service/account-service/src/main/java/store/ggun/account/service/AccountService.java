package store.ggun.account.service;

import store.ggun.account.domain.dto.AccountDto;
import store.ggun.account.domain.dto.Messenger;
import store.ggun.account.domain.dto.OwnStockDto;
import store.ggun.account.domain.model.AccountModel;

import java.util.List;

public interface AccountService extends CommandService<AccountDto>, QueryService<AccountDto> {

    default AccountModel dtoToEntity(AccountDto accountDto){
        return AccountModel.builder()
                .id(accountDto.getId())
                .acno(accountDto.getAcno())
                .acpw(accountDto.getAcpw())
                .balance(accountDto.getBalance())
                .bank(accountDto.getBank())
                .acType(accountDto.getAcType())
                .userId(accountDto.getUserId())
                .build();
    }

    default AccountDto entityToDto(AccountModel accountModel){
        return AccountDto.builder()
                .id(accountModel.getId())
                .acno(accountModel.getAcno())
                .acpw(accountModel.getAcpw())
                .balance(accountModel.getBalance())
                .bank(accountModel.getBank())
                .acType(accountModel.getAcType())
                .userId(accountModel.getUserId())
                .regDate(String.valueOf(accountModel.getRegDate()))
                .modDate(String.valueOf(accountModel.getRegDate()))
                .build();
    }


    List<AccountDto> findByUser(Long id);

    Messenger deposit(AccountDto accountDto);

    Messenger withdraw(AccountDto accountDto);
    Messenger acTransfer(AccountDto accountDto);

    List<AccountDto> getAIAcno();

    boolean checkAiAc(Long id, String acType);

    Messenger modifyByAccount(AccountDto accountDto);

    Messenger getTotalBalanceByUser(List<OwnStockDto> accountDto);

}