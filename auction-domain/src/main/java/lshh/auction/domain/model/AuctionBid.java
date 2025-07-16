package lshh.auction.domain.model;

import jakarta.persistence.*;

@Entity
public class AuctionBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @ManyToOne
    private Auction auction;

    public static AuctionBid from(Auction auction, String userId) {
        AuctionBid auctionBid = new AuctionBid();
        auctionBid.auction = auction;
        auctionBid.userId = userId;
        return auctionBid;
    }
}
