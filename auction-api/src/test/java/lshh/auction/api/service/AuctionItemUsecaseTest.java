package lshh.auction.api.service;

import lombok.extern.slf4j.Slf4j;
import lshh.auction.api.dto.request.AuctionItemRegisterRequest;
import lshh.auction.api.dto.response.AuctionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AuctionItemUsecaseTest {

    @Autowired
    private AuctionItemUsecase utd;

    @Test
    void 넣고_리스트_확인() {
        AuctionItemRegisterRequest request = new AuctionItemRegisterRequest("1", "item1", 0L);
        utd.register(request);

        List<AuctionResponse> result = utd.list();

        assertEquals(1, result.size());
        log.info(result.toString());
    }
}