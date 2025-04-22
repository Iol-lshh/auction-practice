package lshh.auction.app.service;

import lombok.extern.slf4j.Slf4j;
import lshh.auction.app.dto.request.AuctionBidRequest;
import lshh.auction.app.dto.request.AuctionItemRegisterRequest;
import lshh.auction.app.dto.response.AuctionResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AuctionBidUsecaseTest {

    @Autowired
    private AuctionItemUsecase itemUsecase;
    @Autowired
    private AuctionBidUsecase utd;

    @Test
    void 경매_오픈() {
        AuctionItemRegisterRequest request = new AuctionItemRegisterRequest("1", "item1", 0L);
        AuctionResponse response = itemUsecase.register(request);
        var registeredId = response.getId();

        AuctionResponse result = utd.open(registeredId);

        assertEquals(registeredId, result.getId());
        assertEquals("item1", result.getItemName());
        assertEquals("OPEN", result.getStatus());
        log.info(result.toString());
    }


    @Test
    void 경매_입찰() {
        AuctionItemRegisterRequest request = new AuctionItemRegisterRequest("1", "item1", 0L);
        AuctionResponse response = itemUsecase.register(request);
        var registeredId = response.getId();
        utd.open(registeredId);

        AuctionResponse result = utd.bid(new AuctionBidRequest(registeredId, 100L, "1"));

        assertEquals(registeredId, result.getId());
        assertEquals("item1", result.getItemName());
        assertEquals(100, result.getPrice());
        log.info(result.toString());
    }

    @Nested
    class 경매_종료{
        @Test
        void 경매_종료_유찰() {
            AuctionItemRegisterRequest request = new AuctionItemRegisterRequest("1", "item1", 0L);
            AuctionResponse response = itemUsecase.register(request);
            var registeredId = response.getId();
            utd.open(registeredId);

            AuctionResponse result = utd.close(registeredId);

            assertEquals(registeredId, result.getId());
            assertEquals("item1", result.getItemName());
            assertEquals("UNSOLD", result.getStatus());
            log.info(result.toString());
        }

        @Test
        void 경매_종료_낙찰() {
            AuctionItemRegisterRequest request = new AuctionItemRegisterRequest("1", "item1", 0L);
            AuctionResponse response = itemUsecase.register(request);
            var registeredId = response.getId();
            utd.open(registeredId);
            utd.bid(new AuctionBidRequest(registeredId, 100L, "1"));

            AuctionResponse result = utd.close(registeredId);

            assertEquals(registeredId, result.getId());
            assertEquals("item1", result.getItemName());
            assertEquals("SOLD_OUT", result.getStatus());
            log.info(result.toString());
        }
    }
}