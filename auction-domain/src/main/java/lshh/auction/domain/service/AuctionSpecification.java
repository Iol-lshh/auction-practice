package lshh.auction.domain.service;

import lombok.RequiredArgsConstructor;
import lshh.auction.domain.entity.Auction;
import lshh.auction.domain.entity.Money;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuctionSpecification {

    private final AuctionRepository auctionRepository;

    public Auction prepareForOpen(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        if (!auction.isReady()){
            throw new IllegalStateException("Auction cannot be opened");
        }
        return auction;
    }

    public Auction prepareForClose(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        if (!auction.isOpen()){
            throw new IllegalStateException("Auction cannot be closed");
        }
        return auction;
    }

    public Auction prepareForBid(String auctionId, Money amount) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        if (!auction.canBidding(amount)){
            throw new IllegalStateException("Auction is not open for bidding");
        }
        return auction;
    }
}
