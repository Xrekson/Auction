package com.eAuction.e_backend.Controller.pre_auth;

import com.eAuction.e_backend.DTO.CategoryDTO;
import com.eAuction.e_backend.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pre-auth/categories")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:5173" }) // Adjust this for your frontend URL in production
public class CategoryPreAuthController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesPreAuth() {
        List<CategoryDTO> categories = categoryService.readAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryByIdPreAuth(@PathVariable Integer id) {
        CategoryDTO category = categoryService.readCategory(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(category);
    }
}