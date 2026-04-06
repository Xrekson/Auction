package com.eAuction.e_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eAuction.e_backend.DTO.AuctionData;
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
    public List<AuctionData> readAllProduct() {
        return prepo.findAll().stream().map(data->{
            return new AuctionData(data.getId(),data.getName(),data.getPrice(),data.getDetail(),null,data.getAuction_start(),data.getAuction_end(),null,null,null,null,null,data.getImages());
        }).toList();
    }
}
