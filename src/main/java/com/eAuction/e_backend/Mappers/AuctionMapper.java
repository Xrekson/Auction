package com.eAuction.e_backend.Mappers;

import org.springframework.stereotype.Component;

import com.eAuction.e_backend.DTO.AuctionData;
import com.eAuction.e_backend.DTO.CategoryDTO;
import com.eAuction.e_backend.Entity.Category;
import com.eAuction.e_backend.Entity.Listing;

@Component
public class AuctionMapper {

    public AuctionData toDto(Listing entity) {
        if (entity == null) return null;
        AuctionData dto = new AuctionData();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDetail(entity.getDetail());
        dto.setPriceInterval(entity.getPriceInterval());
        dto.setAuction_start(entity.getAuction_start());
        dto.setAuction_end(entity.getAuction_end());
        dto.setHighestbid(entity.getHighestbid());
        dto.setImages(entity.getImages());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        dto.setCreatedby(entity.getCreatedby());
        dto.setUpdatedby(entity.getUpdatedby());

        if (entity.getCategory() != null) {
            CategoryDTO catDto = new CategoryDTO();
            catDto.setCategory_id(entity.getCategory().getId());
            catDto.setCategory_name(entity.getCategory().getName());
            dto.setCategory(catDto);
        }
        return dto;
    }

    public Listing toEntity(AuctionData dto) {
        if (dto == null) return null;
        Listing entity = new Listing();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDetail(dto.getDetail());
        entity.setPriceInterval(dto.getPriceInterval());
        entity.setAuction_start(dto.getAuction_start());
        entity.setAuction_end(dto.getAuction_end());
        entity.setHighestbid(dto.getHighestbid());
        entity.setImages(dto.getImages());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        entity.setCreatedby(dto.getCreatedby());
        entity.setUpdatedby(dto.getUpdatedby());

        if (dto.getCategory() != null) {
            Category cat = new Category();
            cat.setId(dto.getCategory().getCategory_id());
            entity.setCategory(cat);
        }
        return entity;
    }
}
