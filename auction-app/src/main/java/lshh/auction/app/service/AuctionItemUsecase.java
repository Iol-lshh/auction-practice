package lshh.auction.app.service;

import lombok.RequiredArgsConstructor;
import lshh.auction.domain.command.AuctionCommand;
import lshh.auction.domain.model.Auction;
import lshh.auction.domain.service.AuctionFactory;
import lshh.auction.domain.service.AuctionRepository;
import lshh.auction.infra.projection.AuctionProjection;
import lshh.auction.infra.service.AuctionQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuctionItemUsecase {
    private final AuctionQuery auctionQuery;
    private final AuctionRepository auctionRepository;
    private final AuctionFactory auctionFactory;

    @Transactional(readOnly = true)
    public List<AuctionInfo> list() {
        List<AuctionProjection> auctionProjections = auctionQuery.getList();
        return AuctionInfo.from(auctionProjections);
    }

    @Transactional
    public AuctionInfo register(AuctionCommand.RegisterItem command) {
        Auction auction = auctionFactory.generateByItem(command);
        auctionRepository.save(auction);
        return AuctionInfo.from(auction);
    }
}
