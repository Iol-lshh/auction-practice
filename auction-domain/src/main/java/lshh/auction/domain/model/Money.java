package lshh.auction.domain.model;

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
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount must be a non-negative value.");
        }
        money.amount = amount;
        return money;
    }

    public boolean isBiggerThan(Money amount) {
        return this.amount > amount.getAmount();
    }
}
