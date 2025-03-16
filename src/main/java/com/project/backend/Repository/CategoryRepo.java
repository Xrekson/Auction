package com.project.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.backend.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
