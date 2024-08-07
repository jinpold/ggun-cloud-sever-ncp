package store.ggun.account.service;

import org.springframework.data.domain.Page;
import store.ggun.account.domain.model.AccHistoryModel;
import store.ggun.account.domain.dto.AccHistoryDto;
import store.ggun.account.domain.model.AccountModel;

import java.util.List;

public interface AccHistoryService extends CommandService<AccHistoryDto>, QueryService<AccHistoryDto> {


    default AccHistoryModel dtoToEntity(AccHistoryDto accHistorydto, AccountModel accountModel){
        return AccHistoryModel.builder()
                .id(accHistorydto.getId())
                .balance(accHistorydto.getBalance())
                .tradeType(accHistorydto.getTradeType())
                .briefs(accHistorydto.getBriefs())
                .account(accountModel)
                .build();
    }

    default AccHistoryDto entityToDto(AccHistoryModel accHistoryModel){
        return AccHistoryDto.builder()
                .id(accHistoryModel.getId())
                .balance(accHistoryModel.getBalance())
                .tradeType(accHistoryModel.getTradeType())
                .briefs(accHistoryModel.getBriefs())
                .account(accHistoryModel.getAccount().getId())
                .build();
    }


    Page<AccHistoryDto> findByAccount(Long id, int page);
}
