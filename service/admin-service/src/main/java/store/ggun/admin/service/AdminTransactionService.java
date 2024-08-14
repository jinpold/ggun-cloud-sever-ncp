package store.ggun.admin.service;

import store.ggun.admin.domain.dto.AdminTransactionDto;
import store.ggun.admin.domain.dto.TradeMetrics;
import store.ggun.admin.domain.model.AdminTransactionModel;

import java.util.Map;

public interface AdminTransactionService extends AdminCommandService<AdminTransactionDto>, AdminQueryService<AdminTransactionDto> {

    Map<String, Map<String, Long>> getNetProfitByDate();

    Map<String, Map<String, TradeMetrics>> getTotalByDate();

    Map<String, Map<String, Integer>> getQuantityByDate();

    Map<String, Map<String, Map<String, Integer>>> getProductByDate();

    Long countAllTransactions();

    Map<String, Map<String, Integer>> getColorByDate();

    Map<String, Integer> getColorByCount();

    default AdminTransactionModel dtoToEntity(AdminTransactionDto dto) {
        return AdminTransactionModel.builder()
                .id(dto.getId())
                .ordDvsnName(dto.getOrdDvsnName())
                .ordDvsnCd(dto.getOrdDvsnCd())
                .sllBuyDvsnCd(dto.getSllBuyDvsnCd())
                .pdno(dto.getPdno())
                .prdtName(dto.getPrdtName())
                .ordQty(dto.getOrdQty())
                .ccldPrvs(dto.getCcldPrvs())
                .tradeType(dto.getTradeType())
                .sellingFee(dto.getSellingFee())
                .sellingTax(dto.getSellingTax())
                .standardFee(dto.getStandardFee())
                .baseTax(dto.getBaseTax())
                .color(dto.getColor())
                .build();
    }

    default AdminTransactionDto entityToDto(AdminTransactionModel ent) {
        return AdminTransactionDto.builder()
                .id(ent.getId())
                .ordDvsnName(ent.getOrdDvsnName())
                .ordDvsnCd(ent.getOrdDvsnCd())
                .sllBuyDvsnCd(ent.getSllBuyDvsnCd())
                .pdno(ent.getPdno())
                .prdtName(ent.getPrdtName())
                .ordQty(ent.getOrdQty())
                .ccldPrvs(ent.getCcldPrvs())
                .tradeType(ent.getTradeType())
                .sellingFee(ent.getSellingFee())
                .sellingTax(ent.getSellingTax())
                .standardFee(ent.getStandardFee())
                .baseTax(ent.getBaseTax())
                .color(ent.getColor())
                .build();
    }
}
