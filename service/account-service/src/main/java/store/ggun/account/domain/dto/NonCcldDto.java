package store.ggun.account.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Log4j2
public class NonCcldDto {


    private Long id;
    private Long ccldPrvs;
    private int pdQty;
    private String pdno;
    private String prdtName;
    private String tradeType;
    private int sllBuyDvsnCd;
    private int ordDvsnCd;
    private Long account;
    private String regDate;
    private String modDate;
}
