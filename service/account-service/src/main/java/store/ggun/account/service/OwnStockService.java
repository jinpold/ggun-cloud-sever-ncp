package store.ggun.account.service;

import store.ggun.account.domain.dto.Messenger;
import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.model.OwnStockModel;
import store.ggun.account.domain.dto.OwnStockDto;

import java.util.List;

public interface OwnStockService extends CommandService<OwnStockDto>, QueryService<OwnStockDto> {
    default OwnStockModel dtoToEntity(OwnStockDto ownStockDto, AccountModel accountModel){
        return OwnStockModel.builder()
                .id(ownStockDto.getId())
                .pdno(ownStockDto.getPdno())
                .prdtName(ownStockDto.getPrdtName())
                .pdQty(ownStockDto.getPdQty())
                .avgPrvs(ownStockDto.getAvgPrvs())
                .tradeType(ownStockDto.getTradeType())
                .account(accountModel)
                .build();
    }

    default OwnStockDto entityToDto(OwnStockModel ownStockModel){
        return OwnStockDto.builder()
                .id(ownStockModel.getId())
                .pdno(ownStockModel.getPdno())
                .prdtName(ownStockModel.getPrdtName())
                .pdQty(ownStockModel.getPdQty())
                .avgPrvs(ownStockModel.getAvgPrvs())
                .tradeType(ownStockModel.getTradeType())
                .account(ownStockModel.getAccount().getId())
                .regDate(String.valueOf(ownStockModel.getRegDate()))
                .modDate(String.valueOf(ownStockModel.getModDate()))

                .build();
    }

    boolean existsByAccount(Long id);
    List<OwnStockDto> findByAccount(Long id);

    List<OwnStockDto> getProfit(List<OwnStockDto> ownStockDto);

    OwnStockDto getTotalProfit(List<OwnStockDto> ownStockDto);
}
