package lshh.auction.app.api;

import lshh.auction.app.service.AuctionInfo;
import lshh.auction.domain.command.AuctionCommand;

import java.util.List;

public interface AuctionV1Dto {
    record AuctionBidRequest (
        String auctionId,
        Long amount,
        String userId
    ) implements AuctionV1Dto {
    }

    record AuctionBidUpdateStateRequest(
            String auctionId,
            StateRequest state
    ){
        public enum StateRequest {
            OPEN, CLOSE
        }
    }

    record AuctionItemRegisterRequest (
        String itemId,
        String itemName,
        Long minimumPrice
    ) implements AuctionV1Dto {
        public AuctionCommand.RegisterItem toCommand() {
            if (itemId == null || itemId.isBlank()) {
                throw new IllegalArgumentException("Item ID must not be null or blank");
            }
            if (itemName == null || itemName.isBlank()) {
                throw new IllegalArgumentException("Item name must not be null or blank");
            }
            if (minimumPrice == null || minimumPrice <= 0) {
                throw new IllegalArgumentException("Minimum price must be a positive number");
            }
            return AuctionCommand.RegisterItem.of(itemId, itemName, minimumPrice);
        }
    }

    record AuctionResponse (
        String id,
        String itemId,
        String itemName,
        String status,
        Long price
    ) implements AuctionV1Dto {
        public static List<AuctionResponse> from(List<AuctionInfo> infos) {
            return infos.stream()
                .map(info -> new AuctionResponse(
                    info.id(),
                    info.itemId(),
                    info.itemName(),
                    info.status(),
                    info.price()
                ))
                .toList();
        }

        public static AuctionResponse from(AuctionInfo info) {
            return new AuctionResponse(
                info.id(),
                info.itemId(),
                info.itemName(),
                info.status(),
                info.price()
            );
        }
    }
}
