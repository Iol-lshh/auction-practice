package lshh.auction.app.service;

import lombok.RequiredArgsConstructor;
import lshh.auction.app.dto.request.AuctionBidRequest;
import lshh.auction.app.dto.response.AuctionResponse;
import lshh.auction.domain.command.AuctionBidCommand;
import lshh.auction.domain.entity.Auction;
import lshh.auction.domain.service.AuctionRepository;
import lshh.auction.domain.service.AuctionSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuctionBidUsecase {
    private final AuctionRepository auctionRepository;
    private final AuctionSpecification auctionSpecification;

    @Transactional
    public AuctionResponse open(String auctionId) {
        Auction auction = auctionSpecification.prepareForOpen(auctionId);
        auction.open();
        auctionRepository.save(auction);
        return AuctionResponse.from(auction);
    }

    @Transactional
    public AuctionResponse close(String auctionId) {
        Auction auction = auctionSpecification.prepareForClose(auctionId);
        auction.close();
        auctionRepository.save(auction);
        return AuctionResponse.from(auction);
    }

    @Transactional
    public AuctionResponse bid(AuctionBidRequest request) {
        AuctionBidCommand command = new AuctionBidCommand(request.getAuctionId(), request.getAmount(), request.getUserId());
        Auction auction = auctionSpecification.prepareForBid(command.getAuctionId(), command.getAmount());
        auction.bid(command);
        auctionRepository.save(auction);
        return AuctionResponse.from(auction);
    }
}
