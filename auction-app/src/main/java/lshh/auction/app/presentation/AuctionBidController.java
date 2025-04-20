package lshh.auction.app.presentation;

import lombok.RequiredArgsConstructor;
import lshh.auction.app.service.AuctionBidUsecase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auction/bid")
@RequiredArgsConstructor
@RestController
public class AuctionBidController {
    private final AuctionBidUsecase auctionBidUsecase;


}
