package lshh.auction.app.api;

import lombok.extern.slf4j.Slf4j;
import lshh.auction.app.service.AuctionBidUsecase;
import lshh.auction.app.service.AuctionItemUsecase;
import lshh.auction.domain.command.AuctionCommand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static lshh.auction.app.api.AuctionV1Dto.AuctionBidUpdateStateRequest.StateRequest.OPEN;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuctionBidV1E2ETest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private AuctionItemUsecase auctionItemUsecase;
    @Autowired
    private AuctionBidUsecase auctionBidUsecase;

    @Description("POST /v1/auction/bid/state")
    @Nested
    class UpdateState {
        @Description("경매 시작 요청을 보내면, 성공적으로 경매 정보를 반환한다.")
        @Test
        void returnsOpenAuction_whenRequestIsSuccessful() {
            // Arrange
            String requestUrl = "/v1/auction/bid/state/";
            var preCommand = AuctionCommand.RegisterItem.of(
                "testItemId", "Test Item", 100L
            );
            var registeredItem = auctionItemUsecase.register(preCommand);
            AuctionV1Dto.AuctionBidUpdateStateRequest request = new AuctionV1Dto.AuctionBidUpdateStateRequest(
                    registeredItem.id(), OPEN
            );

            // Act
            ParameterizedTypeReference<AuctionV1Dto.AuctionResponse> responseType =
                    new ParameterizedTypeReference<>() {};
            ResponseEntity<AuctionV1Dto.AuctionResponse> response =
                    testRestTemplate.exchange(requestUrl, HttpMethod.POST, new HttpEntity<>(request), responseType);

            // Assert
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertNotNull(response.getBody());
            assertEquals(registeredItem.itemId(), response.getBody().itemId());
            assertEquals(registeredItem.price(), response.getBody().price());
        }

        @Description("경매 시작 요청을 보내면, 경매가 이미 오픈된 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsAlreadyOpen() {
            // Arrange
            String requestUrl = "/v1/auction/bid/state/";
            var preCommand = AuctionCommand.RegisterItem.of(
                "testItemId", "Test Item", 100L
            );
            var registeredItem = auctionItemUsecase.register(preCommand);
            auctionBidUsecase.open(registeredItem.id());
            AuctionV1Dto.AuctionBidUpdateStateRequest request = new AuctionV1Dto.AuctionBidUpdateStateRequest(
                    registeredItem.id(), OPEN
            );

            // Act & Assert
            ParameterizedTypeReference<AuctionV1Dto.AuctionResponse> responseType =
                    new ParameterizedTypeReference<>() {};
            ResponseEntity<AuctionV1Dto.AuctionResponse> response =
                    testRestTemplate.exchange(requestUrl, HttpMethod.POST, new HttpEntity<>(request), responseType);


            assertTrue(response.getStatusCode().is4xxClientError());
        }

        @Description("경매 종료 요청을 보내면, 성공적으로 경매 정보를 반환한다.")
        @Test
        void returnsClosedAuction_whenRequestIsSuccessful() {
            // Arrange
            String requestUrl = "/v1/auction/bid/state/";
            var preCommand = AuctionCommand.RegisterItem.of(
                "testItemId", "Test Item", 100L
            );
            var registeredItem = auctionItemUsecase.register(preCommand);
            auctionBidUsecase.open(registeredItem.id());
            AuctionV1Dto.AuctionBidUpdateStateRequest request = new AuctionV1Dto.AuctionBidUpdateStateRequest(
                    registeredItem.id(), AuctionV1Dto.AuctionBidUpdateStateRequest.StateRequest.CLOSE
            );

            // Act
            ParameterizedTypeReference<AuctionV1Dto.AuctionResponse> responseType =
                    new ParameterizedTypeReference<>() {};
            ResponseEntity<AuctionV1Dto.AuctionResponse> response =
                    testRestTemplate.exchange(requestUrl, HttpMethod.POST, new HttpEntity<>(request), responseType);

            // Assert
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertNotNull(response.getBody());
            assertEquals(registeredItem.itemId(), response.getBody().itemId());
            assertEquals(registeredItem.price(), response.getBody().price());
        }

        @Description("경매 종료 요청을 보내면, 경매가 아직 오픈되지 않은 상태면 예외를 던진다.")
        @Test
        void throwsException_whenAuctionIsNotOpen() {
            // Arrange
            String requestUrl = "/v1/auction/bid/state/";
            var preCommand = AuctionCommand.RegisterItem.of(
                "testItemId", "Test Item", 100L
            );
            var registeredItem = auctionItemUsecase.register(preCommand);
            AuctionV1Dto.AuctionBidUpdateStateRequest request = new AuctionV1Dto.AuctionBidUpdateStateRequest(
                    registeredItem.id(), AuctionV1Dto.AuctionBidUpdateStateRequest.StateRequest.CLOSE
            );

            // Act & Assert
            ParameterizedTypeReference<AuctionV1Dto.AuctionResponse> responseType =
                    new ParameterizedTypeReference<>() {};
            ResponseEntity<AuctionV1Dto.AuctionResponse> response =
                    testRestTemplate.exchange(requestUrl, HttpMethod.POST, new HttpEntity<>(request), responseType);

            assertTrue(response.getStatusCode().is4xxClientError());
        }
    }
}
