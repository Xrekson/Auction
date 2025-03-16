package com.project.backend.service.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.backend.Entity.Category;
import com.project.backend.Repository.CategoryRepo;
import com.project.backend.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo; 

	@Override
	public void createCategory(Category category) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Category> readCategory(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Category updateProduct(Category category) {
		Category mainData = categoryRepo.findById(category.getCategory_id()).get();
		mainData.setCategory_details(category.getCategory_details());
		mainData.setCategory_name(category.getCategory_name());
		mainData.setProducts(category.getProducts());
		
		return null;
	}

	@Override
	public void deleteProduct(int id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public List<Category> readAllCategory() {
		// TODO Auto-generated method stub
		return categoryRepo.findAll();
	}

}
