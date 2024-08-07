package store.ggun.account.service;

import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.model.NonCcldModel;
import store.ggun.account.domain.dto.NonCcldDto;

import java.util.List;

public interface NonCcldService extends CommandService<NonCcldDto>, QueryService<NonCcldDto> {

    default NonCcldModel dtoToEntity(NonCcldDto nonCcldDto, AccountModel accountModel){
        return NonCcldModel.builder()
                .id(nonCcldDto.getId())
                .ccldPrvs(nonCcldDto.getCcldPrvs())
                .pdQty(nonCcldDto.getPdQty())
                .pdno(nonCcldDto.getPdno())
                .prdtName(nonCcldDto.getPrdtName())
                .tradeType(nonCcldDto.getTradeType())
                .sllBuyDvsnCd(nonCcldDto.getSllBuyDvsnCd())
                .ordDvsnCd(nonCcldDto.getOrdDvsnCd())
                .account(accountModel)
                .build();
    }

    default NonCcldDto entityToDto(NonCcldModel nonCcldModel){
        return NonCcldDto.builder()
                .id(nonCcldModel.getId())
                .ccldPrvs(nonCcldModel.getCcldPrvs())
                .pdQty(nonCcldModel.getPdQty())
                .pdno(nonCcldModel.getPdno())
                .prdtName(nonCcldModel.getPrdtName())
                .tradeType(nonCcldModel.getTradeType())
                .sllBuyDvsnCd(nonCcldModel.getSllBuyDvsnCd())
                .ordDvsnCd(nonCcldModel.getOrdDvsnCd())
                .account(nonCcldModel.getAccount().getId())
                .build();
    }


    List<NonCcldDto> findByAccount(Long id);
}
