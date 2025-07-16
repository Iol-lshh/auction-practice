package lshh.auction.app.api;

import lombok.RequiredArgsConstructor;
import lshh.auction.app.service.AuctionItemUsecase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/auction/item")
@RequiredArgsConstructor
@RestController
public class AuctionItemV1Controller {
    private final AuctionItemUsecase auctionItemUsecase;

    @GetMapping("/")
    public List<AuctionV1Dto.AuctionResponse> list() {
        var infos = auctionItemUsecase.list();
        return AuctionV1Dto.AuctionResponse.from(infos);
    }

    @PostMapping("/")
    public AuctionV1Dto.AuctionResponse register(@RequestBody AuctionV1Dto.AuctionItemRegisterRequest request) {
        var command = request.toCommand();
        var info = auctionItemUsecase.register(command);
        return AuctionV1Dto.AuctionResponse.from(info);
    }
}
