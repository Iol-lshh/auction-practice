package lshh.auction.domain.service;

import lshh.auction.domain.command.AuctionCommand;
import lshh.auction.domain.model.Auction;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class AuctionFactory {

    public Auction generate() {
        String id = generateUuid();
        return Auction.from(id);
    }

    private String generateUuid(){
        long unixTimeMillis = Instant.now().toEpochMilli();
        long mostSigBits = (unixTimeMillis << 16);
        mostSigBits |= (0x7L << 12);
        long leastSigBits = (long) (Math.random() * Long.MAX_VALUE);
        leastSigBits &= ~(0xC000000000000000L);
        leastSigBits |= 0x8000000000000000L;
        return new UUID(mostSigBits, leastSigBits).toString();
    }

    public Auction generateByItem(AuctionCommand.RegisterItem command) {
        Auction auction = generate();
        auction.update(command);
        return auction;
    }
}
