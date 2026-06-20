package com.eAuction.e_backend.Controller;

import com.eAuction.e_backend.DTO.BidDTO;
import com.eAuction.e_backend.Service.BidService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${app.cors.allowed-origins}", methods = { RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT })
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api/bid")
public class BiddingController {

    @Autowired
    BidService bidService;

    @GetMapping("/all")
    public List<BidDTO> getAllBids() {
        return bidService.getAllBids();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(@PathVariable int id) {
        Map<String, String> res = new HashMap<String, String>();
        BidDTO b = bidService.getBidbyID(id);
        if (b == null) {
            res.put("error", "Auction not found with ID: " + id + " !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
        res.put("msg", "Bid placed with ID: " + id + " !");
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/auction/{auctionItemId}")
    public List<BidDTO> getBidsByAuctionItemId(
            @PathVariable("auctionItemId") Integer auctionItemId) {
        return bidService.getBidsByAuctionItemId(auctionItemId);
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeBid(@RequestBody(required = true) BidDTO data) {
        return bidService.placeBidByUsername(data.getUserName(), data.getAuctionItemId(), data.getBidAmount());
    }
}
