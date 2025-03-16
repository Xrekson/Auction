package com.project.backend.service;

import java.util.List;
import java.util.Optional;

import com.project.backend.Entity.Category;

public interface CategoryService {
	public void createCategory(Category category);
    public Optional<Category> readCategory(Integer id);
    public Category updateProduct(Category category);
    public void deleteProduct(int id);

    public List<Category> readAllCategory();
}
