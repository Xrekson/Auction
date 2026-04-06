package com.eAuction.e_backend.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionData {
     private int id;
    private String name;
    private double price;
    private String detail;
    private Double priceInterval;
    private LocalDateTime auction_start;
    private LocalDateTime auction_end;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Double highestbid;
    private String createdby;
    private String updatedby;
    private List<String> images;
}
