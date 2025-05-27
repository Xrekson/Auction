package com.eAuction.e_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eAuction.e_backend.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
