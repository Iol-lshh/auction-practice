package lshh.auction.api.dto.response;

import lombok.Getter;
import lombok.ToString;
import lshh.auction.domain.entity.Auction;
import lshh.auction.infra.projection.AuctionProjection;

import java.util.List;

@Getter
@ToString
public class AuctionResponse {
    private String id;
    private String itemId;
    private String itemName;
    private String status;
    private Long price;

    public static List<AuctionResponse> from(List<AuctionProjection> auctionProjections) {
        return auctionProjections.stream()
                .map(AuctionResponse::from)
                .toList();
    }
    public static AuctionResponse from(AuctionProjection auctionProjection) {
        var response = new AuctionResponse();
        response.id = auctionProjection.getId();
        response.itemId = auctionProjection.getItemId();
        response.itemName = auctionProjection.getItemName();
        response.status = auctionProjection.getStatus();
        response.price = auctionProjection.getPrice();
        return response;
    }

    public static AuctionResponse from(Auction auction) {
        var response = new AuctionResponse();
        response.id = auction.getId();
        response.itemId = auction.getItem().getId();
        response.itemName = auction.getItem().getName();
        response.status = auction.getStatus().name();
        response.price = auction.getPrice().getAmount();
        return response;
    }
}
