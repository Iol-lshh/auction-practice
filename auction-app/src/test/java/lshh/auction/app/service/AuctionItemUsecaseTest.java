package lshh.auction.app.service;

import lombok.extern.slf4j.Slf4j;
import lshh.auction.app.dto.request.AuctionItemRegisterRequest;
import lshh.auction.app.dto.response.AuctionResponse;
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

        assertTrue(result.size() > 0);
        log.info(result.toString());
    }
}