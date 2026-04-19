package com.eAuction.e_backend.Service;

import com.eAuction.e_backend.DTO.BidDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface BidService {
    public List<BidDTO> getAllBids();
    public BidDTO getBidbyID(int id);
    public List<BidDTO> getBidsByAuctionItemId(Integer auctionItemId);
    public ResponseEntity<String> placeBidByUsername(
        String username,
        int auctionItemId,
        double bidAmount
    );
}
