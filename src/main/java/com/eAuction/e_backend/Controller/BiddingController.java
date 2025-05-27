package com.eAuction.e_backend.Controller;

import com.eAuction.e_backend.Entity.Bid;
import com.eAuction.e_backend.service.impl.BidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RestController
@RequestMapping(path = "/auth/bid")
public class BiddingController {
    @Autowired
    BidServiceImpl bidService;
    @GetMapping("/all")
    public List<Bid> getAllBids() {
        return bidService.getAllBids();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(@PathVariable int id) {
    	Map<String ,String> res = new HashMap<String, String>();
        Bid b = bidService.getBidbyID(id);
        if (b == null) {
        	res.put("error", "Auction not found with ID: "+id+ " !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        };
        res.put("msg", "Bid placed with ID: "+id+ " !");
        return ResponseEntity.ok().body(res);
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
