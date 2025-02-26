package com.project.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.backend.Entity.Images;
import com.project.backend.Entity.Listing;
import com.project.backend.Repository.ImageRepo;
import com.project.backend.Repository.ListingRepo;

import jakarta.transaction.Transactional;

@Service
public class Auction_ProductService {
    @Autowired
    private ListingRepo prepo;
    @Autowired
    private ImageRepo imageRepo;
    
    @Transactional
    public void createProduct(Listing product,Images img) {
        imageRepo.save(img);
        prepo.save(product);
    }
    @Transactional
    public Listing readProduct(Integer id) {
        return prepo.findById(id).get();
    }
    @Transactional
    public String updateProduct(Listing prod,Images img) {
    	try {
			prepo.save(prod);
            imageRepo.save(img);
			return "Updated";
		} catch (Exception e) {
			return e.getMessage();
		}
    }
    @Transactional
    public String updateProduct(Listing prod) {
    	try {
			prepo.save(prod);
            imageRepo.save(prod.getImg());
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
