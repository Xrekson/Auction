package com.eAuction.e_backend.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.eAuction.e_backend.Entity.Category;
import com.eAuction.e_backend.Repository.CategoryRepo;
import com.eAuction.e_backend.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	ObjectMapper json;

	@Override
	public Map<String,String> createCategory(Category category) {
		Map<String,String> res = new HashMap<String,String>();
		try{
			categoryRepo.save(category);
			res.put("msg", "User creation success!");
			return res;
		}catch(Exception err){
			res.put("msg", "Error creating User!");
			res.put("error",err.getMessage());
			return res;
		}
	}

	@Override
	public Map<String,String> readCategory(Integer id) {
		Map<String,String> res = new HashMap<String,String>();
		try{
			Category data = categoryRepo.findById(id).get();
			res.put( "data",json.writer().withDefaultPrettyPrinter().writeValueAsString(data));
		}catch(Exception err){
			res.put("msg", "Error getting User!");
			res.put("error",err.getMessage());
			return res;
		}
		return res;
	}

	@Override
	public Map<String,String> updateProduct(Category category) {
		Map<String,String> res = new HashMap<String,String>();
		try{
			Category mainData = categoryRepo.findById(category.getCategory_id()).get();
			mainData.setCategory_details(category.getCategory_details());
			mainData.setCategory_name(category.getCategory_name());
			mainData.setProducts(category.getProducts());
			categoryRepo.save(mainData);
			res.put("msg", "User data update success!");
			return res;
		}catch(Exception err){
			res.put("msg", "Error updating User!");
			res.put("error",err.getMessage());
			return res;
		}
	}

	@Override
	public Map<String,String> deleteProduct(int id) {
		Map<String,String> res = new HashMap<String,String>();
		try{
			categoryRepo.deleteById(id);
			res.put("msg", "User delete update success!");
			return res;
		}catch(Exception err){
			res.put("msg", "Error deleting User!");
			res.put("error",err.getMessage());
			return res;
		}
	}

	@Override
	public List<Category> readAllCategory() {
		return categoryRepo.findAll();
	}

}
