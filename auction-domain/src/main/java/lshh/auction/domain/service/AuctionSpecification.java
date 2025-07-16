package lshh.auction.domain.service;

import lombok.RequiredArgsConstructor;
import lshh.auction.domain.command.AuctionCommand;
import lshh.auction.domain.model.Auction;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuctionSpecification {

    private final AuctionRepository auctionRepository;

    public Auction prepareForOpen(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        if (!auction.isReady()){
            throw new IllegalArgumentException("Auction cannot be opened");
        }
        return auction;
    }

    public Auction prepareForClose(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        if (!auction.isOpen()){
            throw new IllegalArgumentException("Auction cannot be closed");
        }
        return auction;
    }

    public Auction prepareForBid(AuctionCommand.Bid command) {
        return auctionRepository.findById(command.auctionId()).orElseThrow();
    }
}
