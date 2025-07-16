package lshh.auction.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lshh.auction.domain.command.AuctionCommand;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Auction {
    @Id
    private String id;

    @Embedded
    private AuctionItem auctionItem;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuctionBid> bids;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    @Embedded
    private Money price;

    protected Auction() {}
    public static Auction from(String id){
        Auction auction = new Auction();
        auction.id = id;
        auction.bids = new ArrayList<>();
        return auction;
    }

    public void update(AuctionCommand.RegisterItem command) {
        this.auctionItem = AuctionItem.from(command);
        this.status = AuctionStatus.READY;
        if (command.minimumPrice() == null) {
            throw new IllegalArgumentException("Minimum price must be greater than zero");
        }
        this.price = command.minimumPrice();
    }

    public AuctionItem getItem() {
        return auctionItem;
    }

    public void open() {
        if(!isReady()) {
            throw new IllegalStateException("Auction is not ready to open");
        }
        status = AuctionStatus.OPEN;
    }

    public void close() {
        if(bids.isEmpty()){
            status = AuctionStatus.UNSOLD;
            return;
        }
        status = AuctionStatus.SOLD_OUT;
    }

    public boolean isReady() {
        return status == AuctionStatus.READY;
    }

    public boolean isOpen() {
        return status == AuctionStatus.OPEN;
    }

    public boolean canBidding(Money amount) {
        return isOpen()
                && amount.isBiggerThan(price);
    }

    public void bid(AuctionCommand.Bid command) {
        if (!canBidding(command.amount())) {
            throw new IllegalArgumentException("Cannot bid");
        }
        this.price = command.amount();
        AuctionBid auctionBid = AuctionBid.from(this, command.userId());
        this.bids.add(auctionBid);
    }
}
