package lshh.auction.app.api;

import lshh.auction.app.service.AuctionItemUsecase;
import lshh.auction.domain.command.AuctionCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuctionItemV1E2ETest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private AuctionItemUsecase auctionItemUsecase;

    @DisplayName("POST /v1/auction/item/")
    @Nested
    class RegisterAuctionItem {
        @DisplayName("경매 아이템 등록 요청을 보내면, 성공적으로 등록된 아이템 정보를 반환한다.")
        @Test
        void returnsRegisteredAuctionItem_whenRequestIsSuccessful() {
            // Arrange
            String requestUrl = "/v1/auction/item/";
            AuctionV1Dto.AuctionItemRegisterRequest request = new AuctionV1Dto.AuctionItemRegisterRequest(
                "test", "This is a test item description.", 100L
            );

            // Act
            ParameterizedTypeReference<AuctionV1Dto.AuctionResponse> responseType =
                    new ParameterizedTypeReference<>() {};
            ResponseEntity<AuctionV1Dto.AuctionResponse> response =
                    testRestTemplate.exchange(requestUrl, HttpMethod.POST, new HttpEntity<>(request), responseType);

            // Assert
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().id());
            assertEquals(request.itemId(), response.getBody().itemId());
            assertEquals(request.itemName(), response.getBody().itemName());
            assertEquals(request.minimumPrice(), response.getBody().price());
        }

        @DisplayName("경매 아이템 등록 요청 시, 아이템 아이디가 누락되면 400 에러를 낸다.")
        @Test
        void failsToRegisterAuctionItem_whenRequiredFieldsAreMissing() {
            // Arrange
            String requestUrl = "/v1/auction/item/";
            AuctionV1Dto.AuctionItemRegisterRequest request = new AuctionV1Dto.AuctionItemRegisterRequest(
                null, "This is a test item description.", 100L
            );

            // Act
            ResponseEntity<String> response =
                    testRestTemplate.exchange(requestUrl, HttpMethod.POST, new HttpEntity<>(request), String.class);

            // Assert
            assertTrue(response.getStatusCode().is4xxClientError());
        }
    }

    @DisplayName("GET /v1/auction/item/")
    @Nested
    class ListAuctionItems {
        @DisplayName("경매 아이템 목록 요청을 보내면, 등록된 모든 아이템 정보를 반환한다.")
        @Test
        void returnsListOfAuctionItems_whenRequestIsSuccessful() {
            // Arrange
            var command = AuctionCommand.RegisterItem.of(
                "item1", "Test Item 1", 100L
            );
            auctionItemUsecase.register(command);
            String requestUrl = "/v1/auction/item/";

            // Act
            ParameterizedTypeReference<List<AuctionV1Dto.AuctionResponse>> responseType =
                    new ParameterizedTypeReference<>() {};
            ResponseEntity<List<AuctionV1Dto.AuctionResponse>> response =
                    testRestTemplate.exchange(requestUrl, HttpMethod.GET, null, responseType);

            // Assert
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
        }
    }
}
