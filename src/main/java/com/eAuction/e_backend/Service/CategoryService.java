package com.eAuction.e_backend.Service;

import com.eAuction.e_backend.DTO.CategoryDTO;
import java.util.List;
import java.util.Map;

public interface CategoryService {
    public Map<String, Object> createCategory(CategoryDTO category);
    public CategoryDTO readCategory(Integer id);
    public Map<String, Object> updateCategory(CategoryDTO category);
    public Map<String, String> deleteCategory(int id);

    public List<CategoryDTO> readAllCategory();
}
