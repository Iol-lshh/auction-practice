package lshh.auction.app.dto.request;

import lombok.Data;

@Data
public class AuctionBidRequest {
    private final String auctionId;
    private final Long amount;
    private final String userId;
}
