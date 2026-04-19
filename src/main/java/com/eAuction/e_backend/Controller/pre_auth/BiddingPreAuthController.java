package com.eAuction.e_backend.Controller.pre_auth;

import com.eAuction.e_backend.DTO.BidDTO;
import com.eAuction.e_backend.Service.BidService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
    origins = { "http://localhost:4200", "http://localhost:5173" },
    methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT }
)
@RestController
@RequestMapping(path = "/api/pre-auth/bid")
public class BiddingPreAuthController {

    @Autowired
    BidService bidService;

    @GetMapping("/all")
    public List<BidDTO> getAllBids() {
        return bidService.getAllBids();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBidByIdPreAuth(@PathVariable int id) {
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
    public List<BidDTO> getBidsByAuctionItemIdPreAuth(
        @PathVariable("auctionItemId") Integer auctionItemId
    ) {
        return bidService.getBidsByAuctionItemId(auctionItemId);
    }

}
