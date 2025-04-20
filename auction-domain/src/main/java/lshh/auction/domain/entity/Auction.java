package lshh.auction.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lshh.auction.domain.command.AuctionBidCommand;
import lshh.auction.domain.command.AuctionItemRegisterCommand;

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

    public void update(AuctionItemRegisterCommand command) {
        this.auctionItem = AuctionItem.from(command);
        this.status = AuctionStatus.READY;
        this.price = Money.from(command.getMinimumPrice());
    }

    public AuctionItem getItem() {
        return auctionItem;
    }

    public void open() {
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

    public void bid(AuctionBidCommand command) {
        if (!canBidding(command.getAmount())) {
            throw new IllegalStateException("Cannot bid");
        }
        this.price = command.getAmount();
        AuctionBid auctionBid = AuctionBid.from(this, command.getUserId());
        this.bids.add(auctionBid);
    }
}
