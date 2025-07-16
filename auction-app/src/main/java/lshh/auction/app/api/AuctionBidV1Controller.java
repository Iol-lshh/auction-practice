package lshh.auction.app.api;

import lombok.RequiredArgsConstructor;
import lshh.auction.app.service.AuctionBidUsecase;
import lshh.auction.app.service.AuctionInfo;
import lshh.auction.domain.command.AuctionCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/v1/auction/bid")
@RequiredArgsConstructor
@RestController
public class AuctionBidV1Controller {
    private final AuctionBidUsecase auctionBidUsecase;

    @PostMapping("/state/")
    public AuctionV1Dto.AuctionResponse updateState(
            @RequestBody AuctionV1Dto.AuctionBidUpdateStateRequest request
    ) {
        AuctionInfo result = switch (request.state()) {
            case OPEN:
                yield auctionBidUsecase.open(request.auctionId());
            case CLOSE:
                yield auctionBidUsecase.close(request.auctionId());
            default:
                throw new IllegalArgumentException("Invalid state request");
        };
        return AuctionV1Dto.AuctionResponse.from(result);
    }
    @PostMapping("/bid/")
    public AuctionV1Dto.AuctionResponse bid(
            @RequestBody AuctionV1Dto.AuctionBidRequest request
    ) {
        AuctionInfo result = auctionBidUsecase.bid(
                AuctionCommand.Bid.of(request.auctionId(), request.amount(), request.userId())
        );
        return AuctionV1Dto.AuctionResponse.from(result);
    }
}
