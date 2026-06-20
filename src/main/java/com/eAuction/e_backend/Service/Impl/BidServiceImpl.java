package com.eAuction.e_backend.Service.Impl;

import com.eAuction.e_backend.DTO.AuctionData;
import com.eAuction.e_backend.DTO.BidDTO;
import com.eAuction.e_backend.Entity.Bid;
import com.eAuction.e_backend.Entity.Listing;
import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.Repository.BidRepo;
import com.eAuction.e_backend.Service.AuctionProductService;
import com.eAuction.e_backend.Service.BidService;
import com.eAuction.e_backend.Service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    BidRepo brepo;

    @Autowired
    AuctionProductService prodService;

    @Autowired
    UserService userService;

    private static final Logger logger = LogManager.getLogger(
        BidServiceImpl.class
    );

    /**
     * @return
     */
    @Override
    @Transactional
    public List<BidDTO> getAllBids() {
        return brepo.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public BidDTO getBidbyID(int id) {
        return brepo.findById(id).map(this::toDTO).orElse(null);
    }

    /**
     * @param auctionItemId
     * @return
     */
    @Override
    @Transactional
    public List<BidDTO> getBidsByAuctionItemId(Integer auctionItemId) {
        return brepo.findByAuctionItemId(auctionItemId).stream().map(this::toDTO).toList();
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
    public ResponseEntity<String> placeBidByUsername(
        String username,
        int auctionItemId,
        double bidAmount
    ) {
        // Check if auction is active before continuing
        AuctionData auctionProduct;
        try {
            auctionProduct = prodService.readProduct(auctionItemId);
            if (auctionProduct == null) {
                return ResponseEntity.status(404).body("Auction item not found");
            }
        } catch (Exception e) {
            logger.error("Error fetching product: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        LocalDateTime now = LocalDateTime.now();
        if (auctionProduct.getAuction_start() != null && now.isBefore(auctionProduct.getAuction_start())) {
            return ResponseEntity.badRequest().body("Auction has not started yet.");
        }
        if (auctionProduct.getAuction_end() != null && now.isAfter(auctionProduct.getAuction_end())) {
            return ResponseEntity.badRequest().body("Auction has already ended.");
        }

        // 1. Find the user by username (email)
        Users user = userService.getUsers(username);

        if (user == null) {
            logger.error("Bid attempt by non-existent user: {}", username);
            return ResponseEntity.status(404).body("User not found");
        }

        // 2. Delegate to the existing placeBid logic using the verified User ID
        return placeBid(user.getId(), auctionItemId, bidAmount);
    }

    @Transactional
    public ResponseEntity<String> placeBid(
        int userId,
        int auctionItemId,
        double bidAmount
    ) {
        AuctionData auctionProduct;
        try {
            auctionProduct = prodService.readProduct(auctionItemId);
            if (auctionProduct == null) {
                return ResponseEntity.status(404).body(
                    "Auction item not found"
                );
            }
        } catch (Exception e) {
            logger.error("Error fetching product: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        // Check if auction is currently active
        LocalDateTime now = LocalDateTime.now();
        if (auctionProduct.getAuction_start() != null && now.isBefore(auctionProduct.getAuction_start())) {
            return ResponseEntity.badRequest().body("Auction has not started yet.");
        }
        if (auctionProduct.getAuction_end() != null && now.isAfter(auctionProduct.getAuction_end())) {
            return ResponseEntity.badRequest().body("Auction has already ended.");
        }

        // Business Logic: Check if bid is high enough
        double minIncrement = auctionProduct.getPriceInterval() != null ? auctionProduct.getPriceInterval() : 0.0;
        double currentHighest = auctionProduct.getHighestbid() != null ? auctionProduct.getHighestbid() : 0.0;
        double basePrice = auctionProduct.getPrice();

        double minimumRequired;
        if (currentHighest <= 0.0) {
            minimumRequired = basePrice;
        } else {
            minimumRequired = currentHighest + minIncrement;
        }

        if (bidAmount < minimumRequired) {
            return ResponseEntity.badRequest().body(
                "Bid too low! Minimum required: " + minimumRequired
            );
        }

        // Fetch user
        var user = userService.getUsers(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        if ("ADMIN".equalsIgnoreCase(user.getType())) {
            return ResponseEntity.status(403).body("Administrators are not allowed to place bids.");
        }

        // Create and save bid
        Bid bid = new Bid();
        bid.setUser(user);
        Listing listingEntity = new Listing();
        listingEntity.setId(auctionProduct.getId()); // Minimal entity for relationship
        bid.setAuctionItem(listingEntity);
        bid.setBidAmount(bidAmount);
        bid.setBidTimestamp(LocalDateTime.now());

        brepo.save(bid);

        // Update product highest bid
        auctionProduct.setHighestbid(bidAmount);
        String updateStatus = prodService.updateProduct(auctionProduct);

        return ResponseEntity.ok("Bid placed successfully. " + updateStatus);
    }

    private BidDTO toDTO(Bid bid) {
        return new BidDTO(
            bid.getBid_id(),
            bid.getUser().getId(),
            bid.getUser().getUserName(),
            bid.getAuctionItem().getId(),
            bid.getAuctionItem().getName(),
            bid.getBidAmount(),
            bid.getBidTimestamp()
        );
    }
}
