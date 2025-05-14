package com.example.demo1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo1.model.Category;
import com.example.demo1.repository.ICategoryRepository;
import com.example.demo1.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryServiceImplTest {

    private ICategoryRepository categoryRepository;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setup() {
        categoryRepository = mock(ICategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void saveCategory_ReturnsSavedCategory() {
        Category category = new Category();
        category.setName("Board Games");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Board Games");

        when(categoryRepository.save(category)).thenReturn(savedCategory);

        Category result = categoryService.save(category);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Board Games", result.getName());
    }

    @Test
    void findById_ExistingCategory_ReturnsCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Board Games");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findAll_ReturnsAllCategories() {
        Category c1 = new Category(1L, "Board Games", null);
        Category c2 = new Category(2L, "Accessories", null);

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Category> categories = categoryService.findAll();
        assertEquals(2, categories.size());
    }

    @Test
    void updateCategory_ExistingCategory_UpdatesCategory() {
        Category existing = new Category(1L, "Board Games", null);
        Category update = new Category();
        update.setName("Updated Name");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(existing)).thenReturn(new Category(1L, "Updated Name", null));

        Category result = categoryService.update(1L, update);
        assertEquals("Updated Name", result.getName());
    }

    @Test
    void deleteCategory_ValidId_DeletesCategory() {
        doNothing().when(categoryRepository).deleteById(1L);
        categoryService.deleteById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
