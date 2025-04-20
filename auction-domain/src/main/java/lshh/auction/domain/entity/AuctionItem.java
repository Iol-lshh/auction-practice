package lshh.auction.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lshh.auction.domain.command.AuctionItemRegisterCommand;

@Getter
@Embeddable
public class AuctionItem {
    @Column(name = "item_id")
    private String id;
    @Column(name = "item_name")
    private String name;

    public static AuctionItem from(AuctionItemRegisterCommand command) {
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.id = command.getId();
        auctionItem.name = command.getName();
        return auctionItem;
    }
}
