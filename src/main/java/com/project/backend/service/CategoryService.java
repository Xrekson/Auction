package com.project.backend.service;

import java.util.List;
import java.util.Map;

import com.project.backend.Entity.Category;

public interface CategoryService {
	public Map<String,String> createCategory(Category category);
    public Map<String,String> readCategory(Integer id);
    public Map<String,String> updateProduct(Category category);
    public Map<String,String> deleteProduct(int id);

    public List<Category> readAllCategory();
}
