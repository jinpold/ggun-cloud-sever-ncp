package store.ggun.user.domain;


import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@ToString(exclude = {"id"})
@Entity(name = "items")
public class ItemModel extends BaseEntity {
    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Float adjClose;
    private int volume;
    @Temporal(TemporalType.DATE)
    private Date date;

}