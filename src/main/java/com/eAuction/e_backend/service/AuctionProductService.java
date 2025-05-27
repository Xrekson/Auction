package com.eAuction.e_backend.service;

import com.eAuction.e_backend.Entity.Listing;

import java.util.List;

public interface AuctionProductService {
    public void createProduct(Listing product);
    public Listing readProduct(Integer id);
    public String updateProduct(Listing prod);
    public void deleteProduct(int id);

    public List<Listing> readAllProduct();

//    public double getHighestBid();
}
