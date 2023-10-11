package com.project.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.backend.Entity.Images;
import com.project.backend.Entity.Product;
import com.project.backend.Repository.ImageRepo;
import com.project.backend.Repository.ProductRepository;

@Service
public class Auction_ProductService {
    @Autowired
    private ProductRepository prepo;
    @Autowired
    private ImageRepo imageRepo;
    String corn="*/2 * * * * *";
    
    public void createProduct(Product product,Images img) {
        imageRepo.save(img);
        prepo.save(product);
    }
    
    public Product readProduct(Integer id) {
        return prepo.findById(id).get();
    }
    
    public String updateProduct(Product prod,Images img) {
    	try {
			prepo.save(prod);
            imageRepo.save(img);
			return "Updated";
		} catch (Exception e) {
			return e.getMessage();
		}
    }
    
    public void deleteProduct(int id) {
        prepo.deleteById(id);
    }
    
    public List<Product> readAllProduct() {
        return prepo.findAll();
    }
}
