package com.eAuction.e_backend.Service.Impl;

import com.eAuction.e_backend.DTO.AuctionData;
import com.eAuction.e_backend.Mappers.AuctionMapper;
import com.eAuction.e_backend.Repository.ListingRepo;
import com.eAuction.e_backend.Service.AuctionProductService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionProductServiceImpl implements AuctionProductService {

    @Autowired
    private ListingRepo prepo;

    @Autowired
    private AuctionMapper autionMapper;

    @Override
    @Transactional
    public void createProduct(AuctionData product) {
        prepo.save(autionMapper.toEntity(product));
    }

    @Override
    public AuctionData readProduct(Integer id) {
        return prepo.findById(id).map(autionMapper::toDto).orElse(null);
    }

    @Override
    @Transactional
    public String updateProduct(AuctionData prod) {
        try {
            prepo.save(autionMapper.toEntity(prod));
            return "Updated";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        prepo.deleteById(id);
    }

    @Override
    public List<AuctionData> readAllProduct() {
        return prepo
            .findAll()
            .stream()
            .map(autionMapper::toDto)
            .toList();
    }

}
