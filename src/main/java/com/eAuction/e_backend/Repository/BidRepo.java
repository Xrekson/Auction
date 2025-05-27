package com.eAuction.e_backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eAuction.e_backend.Entity.Bid;

@Repository
public interface BidRepo extends JpaRepository<Bid, Integer> {
    List<Bid> findByAuctionItemId(Integer auctionItemId);
}
