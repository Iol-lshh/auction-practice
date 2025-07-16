package lshh.auction.app.service;

import lombok.extern.slf4j.Slf4j;
import lshh.auction.domain.command.AuctionCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AuctionBidUsecaseTest {

    @Autowired
    private AuctionItemUsecase itemUsecase;
    @Autowired
    private AuctionBidUsecase auctionBidUsecase;

    @DisplayName("경매 오픈")
    @Nested
    class Open {
        @DisplayName("경매 아이템 등록 후 오픈시, 오픈된 상태의 경매 정보를 준다.")
        @Test
        void returnsAuctionInfo_whenItemIsRegisteredAndOpened() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);
            var registeredId = auction.id();

            var result = auctionBidUsecase.open(registeredId);

            assertEquals(registeredId, result.id());
            assertEquals("item1", result.itemName());
            assertEquals("OPEN", result.status());
            log.info(result.toString());
        }

        @DisplayName("경매 아이템 등록 후 오픈시, 경매가 이미 오픈된 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsAlreadyOpen() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);
            auctionBidUsecase.open(auction.id());

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                auctionBidUsecase.open(auction.id());
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매 아이템 등록 후 오픈시, 경매가 종료된 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsClosed() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);
            auctionBidUsecase.open(auction.id());
            auctionBidUsecase.close(auction.id());

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                auctionBidUsecase.open(auction.id());
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매가 아직 등록되지 않은 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsNotRegistered() {
            var exception = assertThrows(NoSuchElementException.class, () -> {
                auctionBidUsecase.open("non-existent-id");
            });

            log.info(exception.getMessage());
        }
    }

    @DisplayName("경매 입찰")
    @Nested
    class Bid {
        @DisplayName("경매 아이템 등록 후 입찰시, 입찰된 상태의 경매 정보를 준다.")
        @Test
        void returnsAuctionInfo_whenItemIsRegisteredAndBid() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);
            auctionBidUsecase.open(auction.id());

            var result = auctionBidUsecase.bid(AuctionCommand.Bid.of(auction.id(), 100L, "1"));

            assertEquals(auction.id(), result.id());
            assertEquals("item1", result.itemName());
            assertEquals(100, result.price());
            log.info(result.toString());
        }

        @DisplayName("경매 아이템 등록 후 입찰시, 입찰가가 이전 입찰가보다 낮으면 예외를 던진다.")
        @Test
        void throwsException_whenBidPriceIsLowerThanPrevious() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);
            auctionBidUsecase.open(auction.id());
            auctionBidUsecase.bid(AuctionCommand.Bid.of(auction.id(), 100L, "1"));

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                auctionBidUsecase.bid(AuctionCommand.Bid.of(auction.id(), 50L, "1"));
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매 아이템 등록 후 입찰시, 경매가 오픈되지 않은 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsNotOpen() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                auctionBidUsecase.bid(AuctionCommand.Bid.of(auction.id(), 100L, "1"));
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매 아이템 등록 후 입찰시, 경매가 종료된 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsClosed() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);
            auctionBidUsecase.open(auction.id());
            auctionBidUsecase.close(auction.id());

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                auctionBidUsecase.bid(AuctionCommand.Bid.of(auction.id(), 100L, "1"));
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매 아이템 등록 후 입찰시, 입찰가가 0 이하일 경우 예외를 던진다.")
        @Test
        void throwsException_whenBidPriceIsZeroOrNegative() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);
            auctionBidUsecase.open(auction.id());

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                auctionBidUsecase.bid(AuctionCommand.Bid.of(auction.id(), 0L, "1"));
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매가 등록되지 않은 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsNotRegistered() {
            var exception = assertThrows(NoSuchElementException.class, () -> {
                auctionBidUsecase.bid(AuctionCommand.Bid.of("non-existent-id", 100L, "1"));
            });

            log.info(exception.getMessage());
        }
    }

    @Nested
    @DisplayName("경매 종료")
    class close {
        @DisplayName("경매 아이템 등록 후 입찰 없이 종료시, 유찰된 상태의 경매 정보를 준다.")
        @Test
        void returnsAuctionInfo_whenItemIsRegisteredAndClosedWithoutBids() {
            var registerCommand = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(registerCommand);
            auctionBidUsecase.open(auction.id());

            AuctionInfo result = auctionBidUsecase.close(auction.id());

            assertEquals(auction.id(), result.id());
            assertEquals("item1", result.itemName());
            assertEquals("UNSOLD", result.status());
            log.info(result.toString());
        }

        @DisplayName("경매 아이템 등록 후 입찰 후 종료시, 낙찰된 상태의 경매 정보를 준다.")
        @Test
        void returnsAuctionInfo_whenItemIsRegisteredAndClosedWithBids() {
            var registerCommand = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(registerCommand);
            auctionBidUsecase.open(auction.id());
            auctionBidUsecase.bid(AuctionCommand.Bid.of(auction.id(), 100L, "1"));

            AuctionInfo result = auctionBidUsecase.close(auction.id());

            assertEquals(auction.id(), result.id());
            assertEquals("item1", result.itemName());
            assertEquals("SOLD_OUT", result.status());
            assertEquals(100, result.price());
            log.info(result.toString());
        }

        @DisplayName("경매 아이템 등록 후 종료시, 경매가 오픈되지 않은 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsNotOpen() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                auctionBidUsecase.close(auction.id());
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매 아이템 등록 후 종료시, 경매가 이미 종료된 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsAlreadyClosed() {
            var command = AuctionCommand.RegisterItem.of("1", "item1", 0L);
            AuctionInfo auction = itemUsecase.register(command);
            auctionBidUsecase.open(auction.id());
            auctionBidUsecase.close(auction.id());

            var exception = assertThrows(IllegalArgumentException.class, () -> {
                auctionBidUsecase.close(auction.id());
            });

            log.info(exception.getMessage());
        }

        @DisplayName("경매가 아직 등록되지 않은 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsNotRegistered() {
            var exception = assertThrows(NoSuchElementException.class, () -> {
                auctionBidUsecase.close("non-existent-id");
            });

            log.info(exception.getMessage());
        }
    }
}