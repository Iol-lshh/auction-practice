package lshh.auction.app.service;

import lombok.extern.slf4j.Slf4j;
import lshh.auction.domain.command.AuctionCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AuctionItemUsecaseTest {

    @Autowired
    private AuctionItemUsecase auctionItemUsecase;

    @DisplayName("경매 아이템 등록")
    @Nested
    class Register {

        @DisplayName("경매 아이템을 등록하면, 경매 정보가 반환된다.")
        @Test
        void registerItem_returnsAuctionInfo() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            var result = auctionItemUsecase.register(command);

            assertNotNull(result);
            assertEquals("item1", result.itemName());
            assertEquals(0L, result.price());
            log.info(result.toString());
        }

        @DisplayName("경매 아이템 등록시, 아이템 이름이 비어있으면 예외를 던진다.")
        @ParameterizedTest
        @ValueSource(strings = {
                ""
        })
        void throwsException_whenItemNameOrPriceIsBlank(String itemName) {
            var exception = assertThrows(IllegalArgumentException.class, () -> {
                var command = AuctionCommand.RegisterItem.of("1", itemName, 0L);
                auctionItemUsecase.register(command);
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매 아이템 등록시, 가격이 음수이면 예외를 던진다.")
        @ParameterizedTest
        @ValueSource(longs = {
                -1L, -100L
        })
        void throwsException_whenPriceIsNegative(long price) {

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                var command = AuctionCommand.RegisterItem.of("1", "item1", price);
                auctionItemUsecase.register(command);
            });

            log.info(exception.getMessage());
        }
    }

    @DisplayName("경매 아이템 조회")
    @Nested
    class List {

        @DisplayName("경매 아이템을 조회하면, 해당 아이템의 경매 정보가 반환된다.")
        @Test
        void findItem_returnsAuctionInfo() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            auctionItemUsecase.register(command);

            var result = auctionItemUsecase.list();

            assertNotNull(result);
            assertFalse(result.isEmpty());
            log.info(result.toString());
        }
    }

}