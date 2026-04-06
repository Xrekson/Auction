package com.eAuction.e_backend.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.eAuction.e_backend.DTO.BidDTO;
import com.eAuction.e_backend.service.impl.BidServiceImpl;

@Controller
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:5173"})
public class WebSocketController {

    @Autowired
    BidServiceImpl bidService;

    @MessageMapping("/biding")
    @SendTo("/main/bid/response")
    public BidDTO processMessage(BidDTO message, Principal principal) {
        // principal.getName() is the username/email from the JWT
        bidService.placeBidByUsername(principal.getName(), message.getAuctionItemId(), message.getBidAmount());
        return message;
    }
}
