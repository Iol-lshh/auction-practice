package lshh.auction.domain.command;

import lombok.Data;

@Data
public class AuctionItemRegisterCommand {
    private String id;
    private String name;
    private Long minimumPrice;

    public AuctionItemRegisterCommand(String id, String name, Long minimumPrice) {
        this.id = id;
        this.name = name;
        this.minimumPrice = minimumPrice;
    }
}
