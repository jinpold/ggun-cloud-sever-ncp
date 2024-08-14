package store.ggun.admin.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@Log4j2
public class AdminTransactionDto {

    private long id;
    private long accountId;
    private String ordDvsnName;
    private int ordDvsnCd;
    private int sllBuyDvsnCd;
    private String pdno;
    private String prdtName;
    private int ordQty;
    private long ccldPrvs;
    private String tradeType;
    private long sellingFee;
    private long sellingTax;
    private double standardFee;
    private double baseTax;
    private String color;
    private String regDate;
    private String modDate;

    @QueryProjection
    public AdminTransactionDto(long id, long accountId ,String ordDvsnName, int ordDvsnCd, int sllBuyDvsnCd, String pdno,
                               String prdtName, int ordQty, long ccldPrvs, String tradeType, long sellingFee,
                               long sellingTax, double standardFee, double baseTax, String color, String regDate, String modDate) {
        this.id = id;
        this.accountId = accountId;
        this.ordDvsnName = ordDvsnName;
        this.ordDvsnCd = ordDvsnCd;
        this.sllBuyDvsnCd = sllBuyDvsnCd;
        this.pdno = pdno;
        this.prdtName = prdtName;
        this.ordQty = ordQty;
        this.ccldPrvs = ccldPrvs;
        this.tradeType = tradeType;
        this.sellingFee = sellingFee;
        this.sellingTax = sellingTax;
        this.standardFee = standardFee;
        this.baseTax = baseTax;
        this.color = color;
        this.regDate = regDate;
        this.modDate = modDate;
    }
}
