package com.eAuction.e_backend.Mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eAuction.e_backend.DTO.CategoryDTO;
import com.eAuction.e_backend.Entity.Category;
import com.eAuction.e_backend.Entity.Listing;

@Component
public class CategoryMapper {

    @Autowired
    private AuctionMapper auctionMapper;

    public CategoryDTO toDto(Category entity) {
        if (entity == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setCategory_id(entity.getId());
        dto.setCategory_name(entity.getName());
        dto.setCategory_details(entity.getDetails());

        if (entity.getProducts() != null) {
            dto.setProducts(
                entity.getProducts().stream()
                    .map(auctionMapper::toDto)
                    .collect(java.util.stream.Collectors.toList())
            );
        }
        return dto;
    }

    public Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;
        Category entity = new Category();
        entity.setId(dto.getCategory_id());
        entity.setName(dto.getCategory_name());
        entity.setDetails(dto.getCategory_details());

        if (dto.getProducts() != null) {
            List<Listing> productsArray = dto.getProducts().stream()
                .map(auctionMapper::toEntity)
                .toList();
            entity.setProducts(productsArray);
        }
        return entity;
    }
}
