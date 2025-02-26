package com.project.backend.service;

import com.project.backend.Entity.Bid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BidService {

    public List<Bid> getAllBids();
    public Bid getBidbyID(int id);
    public List<Bid> getBidsByAuctionItemId(Integer auctionItemId);
    public ResponseEntity<String> placeBid(int userId, int auctionItemId, double bidAmount);
}
