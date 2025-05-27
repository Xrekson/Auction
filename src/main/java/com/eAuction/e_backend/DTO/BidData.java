package com.eAuction.e_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidData {
    public int userId;
    public int auctionItemId;
    public double bidAmount;
}
