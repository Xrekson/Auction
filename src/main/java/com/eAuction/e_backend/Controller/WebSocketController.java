package com.eAuction.e_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.eAuction.e_backend.DTO.BidData;
import com.eAuction.e_backend.service.impl.BidServiceImpl;

@Controller
public class WebSocketController {

    @Autowired
    BidServiceImpl bidService;

	@MessageMapping("/biding") // Clients will send messages to /main/biding
    @SendTo("/main/bid/response") // Responses will be sent to subscribers of /auc/bid/response
    public BidData processMessage(BidData message) {
        try {
            System.out.println("from WEBSOC"+ message);
            bidService.placeBid(message.getUserId(),message.getAuctionItemId(),message.getBidAmount());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return message;
    }
}
