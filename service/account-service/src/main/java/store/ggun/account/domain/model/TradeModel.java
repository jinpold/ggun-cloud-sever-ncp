package store.ggun.account.domain.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Setter
@AllArgsConstructor
@Entity(name = "trades")
public class TradeModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ordDvsnName; //주문구분명 : 시장가 or 지정가
    private int ordDvsnCd; //주문 구분 코드  1:시장가 or 2:지정가
    private int sllBuyDvsnCd; // 매도 매수 구분코드  1:매수 or 2:매도
    private String pdno; //상품번호
    private String prdtName; //상품명
    private int ordQty; //주문 수량
    private long ccldPrvs; // 평균가(체결가, 지정가) - 이름만 평균가임
    private String tradeType; // 거래타입(ai,user)
    private long sellingFee; //매도 수수료
    private long sellingTax; //매도 세금
    private double standardFee; // 수수료(기준)0.015%
    private double baseTax; //세금(기준)0.020%
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountModel account;

}
