package store.ggun.account.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Log4j2
public class TradeDto {

//    localhost:8888/api/trades/history
//    post
    private long tradeId;
    private long odno; //주문번호
    private String ordDvsnName; //주문구분명 : 시장가 or 지정가
    private int ordDvsnCd; //주문 구분 코드
    private int sllBuyDvsnCd; // 매도 매수 구분코드
    private String pdno; //상품번호
    private String prdtName; //상품명
    private int ordQty; //주문 수량
    private int totCcldQty; // 총 체결수량
    private long ccldPrvs; // 평균가(체결가, 지정가) - 이름만 평균가임
    private String tradeType; // 거래타입(ai,user)
    private long sellingFee; //매도 수수료
    private long sellingTax;
    private double standardFee; // 수수료(기준)0.015%
    private double baseTax; //세금(기준)0.20%
    private String regDate;
    private String modDate;
    private Long account;


}
