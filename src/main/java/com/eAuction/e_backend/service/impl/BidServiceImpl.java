package com.eAuction.e_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eAuction.e_backend.Entity.Bid;
import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.Entity.Listing;
import com.eAuction.e_backend.Repository.BidRepo;
import com.eAuction.e_backend.service.BidService;
import com.eAuction.e_backend.service.UserService;

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
    /**
     * Places a bid using the username extracted from the JWT Principal.
     * This prevents users from placing bids on behalf of other IDs.
     */
    @Override
    @Transactional
    public ResponseEntity<String> placeBidByUsername(String username, int auctionItemId, double bidAmount) {
        // 1. Find the user by username (email)
        // Assumption: Your userService has a method findByEmail or similar
        Users user = userService.getusername(username);

        if (user == null) {
            logger.error("Bid attempt by non-existent user: {}", username);
            return ResponseEntity.status(404).body("User not found");
        }

        // 2. Delegate to the existing placeBid logic using the verified User ID
        return placeBid(user.getId(), auctionItemId, bidAmount);
    }

    @Transactional
    public ResponseEntity<String> placeBid(int userId, int auctionItemId, double bidAmount) {
        Listing auctionProduct;
        try {
            auctionProduct = prodService.readProduct(auctionItemId);
            if (auctionProduct == null) {
                return ResponseEntity.status(404).body("Auction item not found");
            }
        } catch (Exception e) {
            logger.error("Error fetching product: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        // Business Logic: Check if bid is high enough
        double minimumRequired = auctionProduct.getHighestbid() + auctionProduct.getPriceInterval();
        if (bidAmount < minimumRequired) {
            return ResponseEntity.badRequest()
                    .body("Bid too low! Minimum required: " + minimumRequired);
        }

        // Fetch user
        var user = userService.getUsers(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Create and save bid
        Bid bid = new Bid();
        bid.setUser(user);
        bid.setAuctionItem(auctionProduct);
        bid.setBidAmount(bidAmount);
        bid.setBidTimestamp(LocalDateTime.now());

        brepo.save(bid);

        // Update product highest bid
        auctionProduct.setHighestbid(bidAmount);
        String updateStatus = prodService.updateProduct(auctionProduct);

        return ResponseEntity.ok("Bid placed successfully. " + updateStatus);
    }
}
