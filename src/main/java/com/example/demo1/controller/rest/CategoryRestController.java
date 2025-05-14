package com.example.demo1.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.dto.category.CategoryInDTO;
import com.example.demo1.dto.category.CategoryOutDTO;
import com.example.demo1.dto.category.CategoryUpdateDTO;
import com.example.demo1.mapper.CategoryMapper;
import com.example.demo1.model.Category;
import com.example.demo1.service.ICategoryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
    private final ICategoryService categoryService;

    public CategoryRestController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryOutDTO>> getAllCategories() {
        List<CategoryOutDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryOutDTO> getCategoryById(@PathVariable Long id) {
        CategoryOutDTO category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryOutDTO> createCategory(@RequestBody CategoryInDTO categoryInDTO) {
        CategoryOutDTO saved = categoryService.save(categoryInDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryOutDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryInDTO categoryInDTO) {
        CategoryOutDTO updated = categoryService.update(id, categoryInDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}