package lshh.auction.api.presentation;

import lombok.RequiredArgsConstructor;
import lshh.auction.api.service.AuctionBidUsecase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auction/bid")
@RequiredArgsConstructor
@RestController
public class AuctionBidController {
    private final AuctionBidUsecase auctionBidUsecase;


}
