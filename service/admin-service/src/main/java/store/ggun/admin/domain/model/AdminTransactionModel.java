package store.ggun.admin.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Transactions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@ToString(exclude = {"id"})
@AllArgsConstructor
public class AdminTransactionModel extends AdminBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "account_id")
    private long accountId;
    @Column(name = "ord_dvsn_name")
    private String ordDvsnName; //주문구분명 : 시장가 or 지정가
    @Column(name = "ord_dvsn_cd")
    private int ordDvsnCd; //주문 구분 코드  1:시장가 or 2:지정가
    @Column(name = "sll_buy_dvsn_cd")
    private int sllBuyDvsnCd; // 매도 매수 구분코드  1:매수 or 2:매도
    private String pdno; //상품번호
    @Column(name = "prdt_name")
    private String prdtName; //상품명
    @Column(name = "ord_qty")
    private int ordQty; //주문 수량
    @Column(name = "ccld_prvs")
    private long ccldPrvs; // 평균가(체결가, 지정가) - 이름만 평균가임
    @Column(name = "trade_type")
    private String tradeType; // 거래타입(ai,user)
    @Column(name = "selling_fee")
    private long sellingFee; //매도 수수료 (순이익)
    @Column(name = "selling_tax")
    private long sellingTax; //매도 세금
    @Column(name = "standard_fee")
    private double standardFee; // 수수료(기준)0.015%
    @Column(name = "base_tax")
    private double baseTax; //세금(기준)0.020%
    private String color; //색상
}
