package lshh.auction.infra.jpa;

import lshh.auction.domain.model.Auction;
import lshh.auction.infra.projection.AuctionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionJpaRepository extends JpaRepository<Auction, String> {

    @Query(value = """
        select new lshh.auction.infra.projection.AuctionProjection(
            auction.id, item.id, item.name, auction.status, auction.price
        )
        from Auction auction
        left join auction.auctionItem item
    """)
    List<AuctionProjection> getList();

}
