package com.eAuction.e_backend.service;

import java.util.List;
import java.util.Map;

import com.eAuction.e_backend.Entity.Category;

public interface CategoryService {
	public Map<String,String> createCategory(Category category);
    public Map<String,String> readCategory(Integer id);
    public Map<String,String> updateProduct(Category category);
    public Map<String,String> deleteProduct(int id);

    public List<Category> readAllCategory();
}
