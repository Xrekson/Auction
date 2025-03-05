package com.project.backend.Controller;

import com.project.backend.Entity.Bid;
import com.project.backend.service.impl.BidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RestController
@RequestMapping(path = "/bid")
public class BiddingController {
    @Autowired
    BidServiceImpl bidService;
    @GetMapping("/all")
    public List<Bid> getAllBids() {
        return bidService.getAllBids();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(@PathVariable int id) {
        Bid b = bidService.getBidbyID(id);
        if (b == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid ID Wrong");
        };
        return ResponseEntity.ok().body(b);
    }
    @GetMapping("/auction/{auctionItemId}")
    public List<Bid> getBidsByAuctionItemId(@PathVariable("auctionItemId") Integer auctionItemId) {
        return bidService.getBidsByAuctionItemId(auctionItemId);
    }
    @PostMapping("/place")
    public ResponseEntity<String> placeBid(
            @RequestParam("userId") int userId,
            @RequestParam("auctionItemId") int auctionItemId,
            @RequestParam("bidAmount") double bidAmount) {
        return bidService.placeBid(userId, auctionItemId, bidAmount);
    }
}
