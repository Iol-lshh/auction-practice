package lshh.auction.app.presentation;

import lombok.RequiredArgsConstructor;
import lshh.auction.app.dto.request.AuctionItemRegisterRequest;
import lshh.auction.app.dto.response.AuctionResponse;
import lshh.auction.app.service.AuctionItemUsecase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/auction/item")
@RequiredArgsConstructor
@RestController
public class AuctionItemController {
    private final AuctionItemUsecase auctionItemUsecase;

    @GetMapping("/")
    public List<AuctionResponse> list() {
        return auctionItemUsecase.list();
    }

    @PostMapping("/")
    public AuctionResponse register(AuctionItemRegisterRequest request) {
        return auctionItemUsecase.register(request);
    }
}
