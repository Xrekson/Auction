package com.project.backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.backend.Entity.Bid;
import com.project.backend.Entity.Listing;
import com.project.backend.Repository.BidRepo;
import com.project.backend.service.BidService;
import com.project.backend.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    BidRepo brepo;

    @Autowired
    AuctionProductServiceImpl prodService;

    @Autowired
    UserService userService;

    private static final Logger logger = LogManager.getLogger(BidServiceImpl.class);
    /**
     * @return
     */
    @Override
    @Transactional
    public List<Bid> getAllBids() {
        return brepo.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Bid getBidbyID(int id) {
        return brepo.findById(id).orElse(null);
    }

    /**
     * @param auctionItemId
     * @return
     */
    @Override
    @Transactional
    public List<Bid> getBidsByAuctionItemId(Integer auctionItemId) {
        return brepo.findByAuctionItemId(auctionItemId);
    }

    /**
     * @param userId
     * @param auctionItemId
     * @param bidAmount
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<String> placeBid(int userId, int auctionItemId, double bidAmount) {
        Listing auctionProduct = null;
        try {
            auctionProduct = prodService.readProduct(auctionItemId);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        if(bidAmount<=auctionProduct.getHighestbid()+auctionProduct.getPriceInterval()){
            return ResponseEntity.badRequest().body("Add more money to add BID!");
        }
        // Create a new bid
        Bid bid = new Bid();
        bid.setUser(userService.getUsers(userId));
        bid.setAuctionItem(auctionProduct);
        bid.setBidAmount(bidAmount);
        bid.setBidTimestamp(LocalDateTime.now());

        // Update the highest bid amount for the auction item
        auctionProduct.setHighestbid(bidAmount);
        String data = prodService.updateProduct(auctionProduct);

        // Save the bid to the database
        bid = brepo.save(bid);

        if(auctionProduct == null || bid == null)
            return ResponseEntity.internalServerError().body("Bid Not Created!");
        return ResponseEntity.ok(bidAmount+data);
    }
}
