package com.project.backend.service;

import com.project.backend.Entity.Listing;

import java.util.List;
import java.util.Optional;

public interface Auction_ProductService {
    public void createProduct(Listing product);
    public Optional<Listing> readProduct(Integer id);
    public Listing updateProduct(Listing prod);
    public void deleteProduct(int id);

    public List<Listing> readAllProduct();

//    public double getHighestBid();
}
