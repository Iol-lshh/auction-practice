package lshh.auction.app.service;

import lombok.RequiredArgsConstructor;
import lshh.auction.domain.command.AuctionCommand;
import lshh.auction.domain.model.Auction;
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
    public AuctionInfo open(String auctionId) {
        Auction auction = auctionSpecification.prepareForOpen(auctionId);
        auction.open();
        auctionRepository.save(auction);
        return AuctionInfo.from(auction);
    }

    @Transactional
    public AuctionInfo close(String auctionId) {
        Auction auction = auctionSpecification.prepareForClose(auctionId);
        auction.close();
        auctionRepository.save(auction);
        return AuctionInfo.from(auction);
    }

    @Transactional
    public AuctionInfo bid(AuctionCommand.Bid command) {
        Auction auction = auctionSpecification.prepareForBid(command);
        auction.bid(command);
        auctionRepository.save(auction);
        return AuctionInfo.from(auction);
    }
}
