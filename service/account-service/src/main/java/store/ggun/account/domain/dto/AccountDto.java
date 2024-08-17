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
public class AccountDto {

    private Long id;
    private String acno;
    private Long receiveAcId;
    private String acpw;
    private Long balance;
    private String refundAcno;
    private String bank;
    private String briefs;
    private String acType;
    private String tradeType;
    private String paymentUid;
    private String regDate;
    private String modDate;
    private String totalBalance;
    private Long userId;
//    private List<Trade> trades = new ArrayList<>();

}
