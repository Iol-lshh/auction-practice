package lshh.auction.app.service;

import lshh.auction.domain.model.Auction;
import lshh.auction.infra.projection.AuctionProjection;

import java.util.List;

public record AuctionInfo (
    String id,
    String itemId,
    String itemName,
    String status,
    Long price
){
    public static AuctionInfo from(Auction auction) {
        return new AuctionInfo(
            auction.getId(),
            auction.getItem().getId(),
            auction.getItem().getName(),
            auction.getStatus().name(),
            auction.getPrice().getAmount()
        );
    }

    public static List<AuctionInfo> from(List<AuctionProjection> auctionProjections) {
        return auctionProjections.stream()
            .map(auctionProjection -> new AuctionInfo(
                auctionProjection.getId(),
                auctionProjection.getItemId(),
                auctionProjection.getItemName(),
                auctionProjection.getStatus(),
                auctionProjection.getPrice()
            ))
            .toList();
    }
}
