package com.eAuction.e_backend.Controller;

import com.eAuction.e_backend.DTO.BidDTO;
import com.eAuction.e_backend.Service.Impl.BidServiceImpl;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:5173" })
public class WebSocketController {

    @Autowired
    BidServiceImpl bidService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/websoc/biding")
    public void processMessage(BidDTO message, Principal principal) {
        // Run the database and validation logic asynchronously on a virtual thread
        CompletableFuture.runAsync(() -> {
            try {
                ResponseEntity<String> response = bidService.placeBidByUsername(
                    principal.getName(),
                    message.getAuctionItemId(),
                    message.getBidAmount()
                );

                if (response.getStatusCode().is2xxSuccessful()) {
                    // Broadcast successful bid to all subscribers
                    messagingTemplate.convertAndSend("/main/bid/response", message);
                } else {
                    // Notify only the user who made the invalid bid
                    Map<String, Object> error = new HashMap<>();
                    error.put("error", "Bid Rejected");
                    error.put("message", response.getBody());
                    error.put("auctionItemId", message.getAuctionItemId());
                    
                    messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/errors", error);
                }
            } catch (Exception e) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "System Error");
                error.put("message", e.getMessage());
                messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/errors", error);
            }
        });
    }
}
