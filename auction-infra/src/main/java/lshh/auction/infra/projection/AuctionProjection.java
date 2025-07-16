package lshh.auction.infra.projection;

import lombok.Data;
import lshh.auction.domain.model.AuctionStatus;
import lshh.auction.domain.model.Money;

@Data
public class AuctionProjection {
    private String id;
    private String itemId;
    private String itemName;
    private String status;
    private Long price;

    public AuctionProjection(String id, String itemId, String itemName, AuctionStatus status, Money price) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.status = status.name();
        this.price = price.getAmount();
    }
}
