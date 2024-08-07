package store.ggun.account.domain.model;

import jakarta.persistence.*;
//import com.jsggun.account.trade.model.Trade;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"id"})
@Entity(name = "accounts")
@Builder
@AllArgsConstructor
@Setter
public class AccountModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String acno;
    private String acpw;
    private Long balance;
    private String refundAcno;
    private String bank;
    private String acType;

    private Long userId;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TradeModel> trades;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OwnStockModel> OwnStocks;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AccHistoryModel> AccHistories;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<NonCcldModel> nonCclds;




}

