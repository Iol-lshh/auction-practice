package lshh.auction.domain.command;

import lshh.auction.domain.model.Money;

public interface AuctionCommand {
    record Bid (
        String auctionId,
        Money amount,
        String userId
    ) implements AuctionCommand  {
        public static Bid of(String auctionId, Long amount, String userId) {
            return new Bid(auctionId, Money.from(amount), userId);
        }
    }

    record RegisterItem (
        String itemId,
        String itemName,
        Money minimumPrice
    ) implements AuctionCommand {
        public static RegisterItem of(String itemId, String itemName, Long minimumPrice) {
            return new RegisterItem(itemId, itemName, Money.from(minimumPrice));
        }
    }
}
