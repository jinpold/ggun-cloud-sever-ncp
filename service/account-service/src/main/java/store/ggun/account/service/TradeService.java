package store.ggun.account.service;

import store.ggun.account.domain.dto.TradeDto;
import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.model.TradeModel;

import java.util.List;


public interface TradeService extends CommandService<TradeDto>, QueryService<TradeDto> {


    default TradeModel dtoToEntity(TradeDto tradeDto, AccountModel account){
        return TradeModel.builder()
                .id(tradeDto.getId())
                .ordDvsnName(tradeDto.getOrdDvsnName())
                .ordDvsnCd(tradeDto.getOrdDvsnCd())
                .sllBuyDvsnCd(tradeDto.getSllBuyDvsnCd())
                .pdno(tradeDto.getPdno())
                .prdtName(tradeDto.getPrdtName())
                .ordQty(tradeDto.getOrdQty())
                .ccldPrvs(tradeDto.getCcldPrvs())
                .tradeType(tradeDto.getTradeType())
                .sellingFee(tradeDto.getSellingFee())
                .sellingTax(tradeDto.getSellingTax())
                .standardFee(tradeDto.getStandardFee())
                .baseTax(tradeDto.getBaseTax())
                .account(account)
                .color(tradeDto.getColor())
                .build();
    }

    default TradeDto entityToDto(TradeModel trade){
        return TradeDto.builder()
                .id(trade.getId())
                .ordDvsnName(trade.getOrdDvsnName())
                .ordDvsnCd(trade.getOrdDvsnCd())
                .sllBuyDvsnCd(trade.getSllBuyDvsnCd())
                .pdno(trade.getPdno())
                .prdtName(trade.getPrdtName())
                .ordQty(trade.getOrdQty())
                .ccldPrvs(trade.getCcldPrvs())
                .tradeType(trade.getTradeType())
                .sellingFee(trade.getSellingFee())
                .sellingTax(trade.getSellingTax())
                .standardFee(trade.getStandardFee())
                .baseTax(trade.getBaseTax())
                .account(trade.getAccount().getId())
                .color(trade.getColor())
                .regDate(String.valueOf(trade.getRegDate()))
                .modDate(String.valueOf(trade.getModDate()))
                .build();
    }


    List<TradeDto> findByAcno(Long acno);

    List<TradeDto> findByProductName(String pdno);

    List<TradeDto> findByDate(String start,String end);
}
