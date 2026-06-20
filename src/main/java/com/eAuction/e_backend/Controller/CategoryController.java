package com.eAuction.e_backend.Controller;

import com.eAuction.e_backend.DTO.CategoryDTO;
import com.eAuction.e_backend.Service.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api/categories")
@CrossOrigin(origins = "${app.cors.allowed-origins}")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Map<String, Object> response = categoryService.createCategory(categoryDTO);
        if (response.containsKey("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.readAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id) {
        CategoryDTO category = categoryService.readCategory(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        Map<String, Object> response = categoryService.updateCategory(categoryDTO);
        if (response.containsKey("error")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable int id) {
        Map<String, String> response = categoryService.deleteCategory(id);
        if (response.containsKey("error")) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }
}