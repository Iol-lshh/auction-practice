package lshh.auction.infra.service;

import lshh.auction.infra.projection.AuctionProjection;

import java.util.List;

public interface AuctionQuery {
    List<AuctionProjection> getList();
}
