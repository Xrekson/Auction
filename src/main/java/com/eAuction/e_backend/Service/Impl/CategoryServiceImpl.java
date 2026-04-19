package com.eAuction.e_backend.Service.Impl;

import com.eAuction.e_backend.DTO.CategoryDTO;
import com.eAuction.e_backend.Entity.Category;
import com.eAuction.e_backend.Mappers.CategoryMapper;
import com.eAuction.e_backend.Repository.CategoryRepo;
import com.eAuction.e_backend.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryMapper categoryMapper; // Injected your new mapper

    @Override
    public Map<String, Object> createCategory(CategoryDTO categoryDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            // Convert DTO to Entity for saving
            Category category = categoryMapper.toEntity(categoryDto);
            categoryRepo.save(category);
            
            res.put("msg", "Category creation success!");
            return res;
        } catch (Exception err) {
            res.put("msg", "Error creating Category!");
            res.put("error", err.getMessage());
            return res;
        }
    }

    @Override
    public CategoryDTO readCategory(Integer id) {
        // Instead of returning a Map with a JSON string, we return the DTO directly.
        // Spring will automatically convert this DTO to JSON for your Controller.
        return categoryRepo.findById(id)
                .map(categoryMapper::toDto)
                .orElse(null); // Or throw a custom exception
    }

    @Override
    public Map<String, Object> updateCategory(CategoryDTO categoryDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            Category existingCategory = categoryRepo.findById(categoryDto.getCategory_id())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            // Update fields
            existingCategory.setName(categoryDto.getCategory_name());
            existingCategory.setDetails(categoryDto.getCategory_details());
            
            // Map products if they exist in the DTO
            if (categoryDto.getProducts() != null) {
                Category tempEntity = categoryMapper.toEntity(categoryDto);
                existingCategory.setProducts(tempEntity.getProducts());
            }

            categoryRepo.save(existingCategory);
            res.put("msg", "Category update success!");
            return res;
        } catch (Exception err) {
            res.put("msg", "Error updating Category!");
            res.put("error", err.getMessage());
            return res;
        }
    }

    @Override
    public Map<String, String> deleteCategory(int id) {
        Map<String, String> res = new HashMap<>();
        try {
            categoryRepo.deleteById(id);
            res.put("msg", "Category deletion success!");
            return res;
        } catch (Exception err) {
            res.put("msg", "Error deleting Category!");
            res.put("error", err.getMessage());
            return res;
        }
    }

    @Override
    public List<CategoryDTO> readAllCategory() {
        // Fetch all entities and map them to a list of DTOs
        return categoryRepo.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}