package lshh.auction.domain.service;

import lshh.auction.domain.model.Auction;

import java.util.Optional;

public interface AuctionRepository {
    Auction save(Auction auction);

    Optional<Auction> findById(String id);
}
