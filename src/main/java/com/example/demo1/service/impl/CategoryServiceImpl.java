package com.example.demo1.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo1.dto.category.CategoryInDTO;
import com.example.demo1.dto.category.CategoryOutDTO;
import com.example.demo1.mapper.CategoryMapper;
import com.example.demo1.model.Category;
import com.example.demo1.repository.ICategoryRepository;
import com.example.demo1.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(ICategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryOutDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryOutDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryOutDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toCategoryOutDTO(category);
    }

    @Override
    public CategoryOutDTO save(CategoryInDTO categoryInDTO) {
        Category category = categoryMapper.toCategory(categoryInDTO);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toCategoryOutDTO(saved);
    }

    @Override
    public CategoryOutDTO update(Long id, CategoryInDTO categoryInDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setName(categoryInDTO.getName());
        Category updated = categoryRepository.save(existingCategory);
        return categoryMapper.toCategoryOutDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
