package lshh.auction.app.dto.request;

import lombok.Data;

@Data
public class AuctionItemRegisterRequest {
    private final String id;
    private final String name;
    private final Long minimumPrice;
}
