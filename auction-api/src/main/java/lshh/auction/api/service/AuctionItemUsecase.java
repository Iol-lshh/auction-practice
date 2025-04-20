package lshh.auction.api.service;

import lombok.RequiredArgsConstructor;
import lshh.auction.api.dto.request.AuctionItemRegisterRequest;
import lshh.auction.api.dto.response.AuctionResponse;
import lshh.auction.domain.command.AuctionItemRegisterCommand;
import lshh.auction.domain.entity.Auction;
import lshh.auction.domain.service.AuctionFactory;
import lshh.auction.domain.service.AuctionRepository;
import lshh.auction.domain.service.AuctionSpecification;
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
    public List<AuctionResponse> list() {
        List<AuctionProjection> auctionProjections = auctionQuery.getList();
        return AuctionResponse.from(auctionProjections);
    }

    @Transactional
    public AuctionResponse register(AuctionItemRegisterRequest request) {
        AuctionItemRegisterCommand command = new AuctionItemRegisterCommand(request.getId(), request.getName(), request.getMinimumPrice());
        Auction auction = auctionFactory.generateByItem(command);
        auctionRepository.save(auction);
        return AuctionResponse.from(auction);
    }
}
