package store.ggun.account.service;

import store.ggun.account.domain.model.AccountModel;
import store.ggun.account.domain.dto.TradeDto;
import store.ggun.account.domain.model.TradeModel;

import java.util.List;


public interface TradeService extends CommandService<TradeDto>, QueryService<TradeDto> {


    default TradeModel dtoToEntity(TradeDto tradeDto, AccountModel account){
        return TradeModel.builder()
                .tradeId(tradeDto.getTradeId())
                .odno(tradeDto.getOdno())
                .ordDvsnName(tradeDto.getOrdDvsnName())
                .ordDvsnCd(tradeDto.getOrdDvsnCd())
                .sllBuyDvsnCd(tradeDto.getSllBuyDvsnCd())
                .pdno(tradeDto.getPdno())
                .prdtName(tradeDto.getPrdtName())
                .ordQty(tradeDto.getOrdQty())
                .totCcldQty(tradeDto.getTotCcldQty())
                .ccldPrvs(tradeDto.getCcldPrvs())
                .tradeType(tradeDto.getTradeType())
                .sellingFee(tradeDto.getSellingFee())
                .sellingTax(tradeDto.getSellingTax())
                .standardFee(tradeDto.getStandardFee())
                .baseTax(tradeDto.getBaseTax())
                .account(account)
                .build();
    }

    default TradeDto entityToDto(TradeModel trade){
        return TradeDto.builder()
                .tradeId(trade.getTradeId())
                .odno(trade.getOdno())
                .ordDvsnName(trade.getOrdDvsnName())
                .ordDvsnCd(trade.getOrdDvsnCd())
                .sllBuyDvsnCd(trade.getSllBuyDvsnCd())
                .pdno(trade.getPdno())
                .prdtName(trade.getPrdtName())
                .ordQty(trade.getOrdQty())
                .totCcldQty(trade.getTotCcldQty())
                .ccldPrvs(trade.getCcldPrvs())
                .tradeType(trade.getTradeType())
                .sellingFee(trade.getSellingFee())
                .sellingTax(trade.getSellingTax())
                .standardFee(trade.getStandardFee())
                .baseTax(trade.getBaseTax())
                .account(trade.getAccount().getId())

                .regDate(String.valueOf(trade.getRegDate()))
                .modDate(String.valueOf(trade.getModDate()))
                .build();
    }


    List<TradeDto> findByAcno(Long acno);

    List<TradeDto> findByProductName(String pdno);

    List<TradeDto> findByDate(String start,String end);
}
