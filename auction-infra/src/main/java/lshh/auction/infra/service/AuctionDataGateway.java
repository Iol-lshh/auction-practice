package lshh.auction.infra.service;

import lombok.RequiredArgsConstructor;
import lshh.auction.domain.entity.Auction;
import lshh.auction.domain.service.AuctionRepository;
import lshh.auction.infra.jpa.AuctionJpaRepository;
import lshh.auction.infra.projection.AuctionProjection;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuctionDataGateway implements AuctionRepository, AuctionQuery {

    private final AuctionJpaRepository jpaRepository;

    @Override
    public List<AuctionProjection> getList() {
        return jpaRepository.getList();
    }

    @Override
    public Auction save(Auction auction) {
        return jpaRepository.save(auction);
    }

    @Override
    public Optional<Auction> findById(String id) {
        return jpaRepository.findById(id);
    }
}
