package com.project.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.backend.Entity.Listing;
import com.project.backend.Repository.ListingRepo;
import com.project.backend.service.AuctionProductService;

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
