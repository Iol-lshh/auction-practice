package lshh.auction.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lshh.auction.domain.command.AuctionCommand;

@Getter
@Embeddable
public class AuctionItem {
    @Column(name = "item_id")
    private String id;
    @Column(name = "item_name")
    private String name;

    public static AuctionItem from(AuctionCommand.RegisterItem command) {
        if (command.itemId() == null || command.itemId().isBlank()) {
            throw new IllegalArgumentException("Item ID must not be null or blank");
        }
        if (command.itemName() == null || command.itemName().isBlank()) {
            throw new IllegalArgumentException("Item name must not be null or blank");
        }
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.id = command.itemId();
        auctionItem.name = command.itemName();
        return auctionItem;
    }
}
