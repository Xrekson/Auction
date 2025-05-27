package com.eAuction.e_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eAuction.e_backend.Entity.Listing;
import com.eAuction.e_backend.Repository.ListingRepo;
import com.eAuction.e_backend.service.AuctionProductService;

import jakarta.transaction.Transactional;

@Service
public class AuctionProductServiceImpl implements AuctionProductService {
    @Autowired
    private ListingRepo prepo;
    
    @Transactional
    public void createProduct(Listing product) {
        prepo.save(product);
    }
    @Transactional
    public Listing readProduct(Integer id) {
        return prepo.findById(id).get();
    }
    @Transactional
    public String updateProduct(Listing prod) {
    	try {
			prepo.save(prod);
			return "Updated";
		} catch (Exception e) {
			return e.getMessage();
		}
    }
    @Transactional
    public void deleteProduct(int id) {
        prepo.deleteById(id);
    }
    @Transactional
    public List<Listing> readAllProduct() {
        return prepo.findAll();
    }
}
