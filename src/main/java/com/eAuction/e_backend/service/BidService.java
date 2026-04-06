package com.eAuction.e_backend.service;

import com.eAuction.e_backend.Entity.Bid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BidService {

    public List<Bid> getAllBids();
    public Bid getBidbyID(int id);
    public List<Bid> getBidsByAuctionItemId(Integer auctionItemId);
    public ResponseEntity<String> placeBidByUsername(String username, int auctionItemId, double bidAmount);
}
