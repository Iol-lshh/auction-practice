package lshh.auction.domain.command;

import lombok.Data;
import lshh.auction.domain.entity.Money;

@Data
public class AuctionBidCommand {
    private String auctionId;
    private Money amount;
    private String userId;

    public AuctionBidCommand(String auctionId, Long amount, String userId) {
        this.auctionId = auctionId;
        this.amount = Money.from(amount);
        this.userId = userId;
    }
}
