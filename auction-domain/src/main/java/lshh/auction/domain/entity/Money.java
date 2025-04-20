package lshh.auction.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class Money {
    private Long amount;

    public static Money from(Long amount) {
        Money money = new Money();
        money.amount = amount;
        return money;
    }

    public boolean isBiggerThan(Money amount) {
        return this.amount > amount.getAmount();
    }
}
