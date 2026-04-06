package com.eAuction.e_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eAuction.e_backend.Entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
