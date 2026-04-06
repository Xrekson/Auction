package com.eAuction.e_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    private Long bid_id;
    private Integer userId;
    private String userName; // Helpful for the frontend to show who bid
    private Integer auctionItemId;
    private String itemName;
    private double bidAmount;
    private LocalDateTime bidTimestamp;
}
