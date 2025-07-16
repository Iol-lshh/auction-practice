package lshh.auction.app.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AuctionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleException(IllegalArgumentException e) {
        var body = Map.of(
            "error", "IllegalArgumentException",
            "message", e.getMessage()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleException(IllegalStateException e) {
        var body = Map.of(
            "error", "IllegalStateException",
            "message", e.getMessage()
        );
        return ResponseEntity.badRequest().body(body);
    }
}
