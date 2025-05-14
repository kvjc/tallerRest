package com.example.demo1.service;

import com.example.demo1.dto.category.CategoryInDTO;
import com.example.demo1.model.Category;

import java.util.List;
import java.util.Optional;

import com.example.demo1.dto.category.CategoryOutDTO;
import com.example.demo1.dto.category.CategoryUpdateDTO;

public interface ICategoryService {
    List<CategoryOutDTO> findAll();
    CategoryOutDTO findById(Long id);
    CategoryOutDTO save(CategoryInDTO categoryInDTO);
    CategoryOutDTO update(Long id, CategoryInDTO categoryUpdateDTO);
    void deleteById(Long id);
}