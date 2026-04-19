package com.eAuction.e_backend.Mappers;

import org.springframework.stereotype.Component;

import com.eAuction.e_backend.DTO.UserDTO;
import com.eAuction.e_backend.DTO.UserReq;
import com.eAuction.e_backend.Entity.Users;

@Component
public class UserMapper {

    public UserDTO toDto(Users entity) {
        if (entity == null) return null;
        return new UserDTO(
            entity.getId(),
            entity.getUserName(),
            entity.getName(),
            entity.getEmail(),
            entity.getPhno(),
            entity.getType(),
            entity.getDob()
        );
    }

    public Users toEntity(UserReq req) {
        if (req == null) return null;
        Users entity = new Users();
        entity.setUserName(req.getUsername());
        entity.setName(req.getName());
        entity.setEmail(req.getEmail());
        entity.setPhno(req.getMobileno());
        entity.setPassword(req.getPassword()); // Note: Encode this in Service layer!
        entity.setDob(req.getDob());
        entity.setType(req.getType());
        entity.setDesx(req.getDesx());
        entity.setAbout(req.getAbout());
        // Standard audit fields
        entity.setCreatedat(java.time.LocalDateTime.now());
        return entity;
    }
}