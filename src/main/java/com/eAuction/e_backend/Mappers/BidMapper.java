package com.eAuction.e_backend.Mappers;

import org.springframework.stereotype.Component;

import com.eAuction.e_backend.DTO.BidDTO;
import com.eAuction.e_backend.Entity.Bid;
import com.eAuction.e_backend.Entity.Listing;
import com.eAuction.e_backend.Entity.Users;

@Component
public class BidMapper {

    public BidDTO toDto(Bid entity) {
        if (entity == null) return null;
        BidDTO dto = new BidDTO();
        dto.setBid_id(entity.getBid_id());
        dto.setBidAmount(entity.getBidAmount());
        dto.setBidTimestamp(entity.getBidTimestamp());
        
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
            dto.setUserName(entity.getUser().getUserName());
        }
        if (entity.getAuctionItem() != null) {
            dto.setAuctionItemId(entity.getAuctionItem().getId());
            dto.setItemName(entity.getAuctionItem().getName());
        }
        return dto;
    }

    public Bid toEntity(BidDTO dto) {
        if (dto == null) return null;
        Bid entity = new Bid();
        entity.setBid_id(dto.getBid_id());
        entity.setBidAmount(dto.getBidAmount());
        entity.setBidTimestamp(dto.getBidTimestamp());

        // Set skeleton User
        if (dto.getUserId() != null) {
            Users user = new Users();
            user.setId(dto.getUserId());
            entity.setUser(user);
        }
        // Set skeleton Listing
        if (dto.getAuctionItemId() != null) {
            Listing item = new Listing();
            item.setId(dto.getAuctionItemId());
            entity.setAuctionItem(item);
        }
        return entity;
    }
}
