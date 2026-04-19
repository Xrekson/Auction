package com.eAuction.e_backend.Service;

import com.eAuction.e_backend.DTO.AuctionData;
import java.util.List;

public interface AuctionProductService {
    public void createProduct(AuctionData product);
    public AuctionData readProduct(Integer id);
    public String updateProduct(AuctionData prod);
    public void deleteProduct(int id);

    public List<AuctionData> readAllProduct();

    //    public double getHighestBid();
}
