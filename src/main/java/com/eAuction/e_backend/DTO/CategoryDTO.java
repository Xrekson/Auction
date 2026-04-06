package com.eAuction.e_backend.DTO;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDTO {
    private int category_id;
    private String category_name;
    private String category_details;
    private List<AuctionData> products;
}
