package com.example.demo1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.demo1.model.Category;
import com.example.demo1.model.Product;
import com.example.demo1.repository.IProductRepository;
import com.example.demo1.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductServiceImplTest {

    private IProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        productRepository = mock(IProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    // Test 1: Guardar producto → retorna producto guardado
    @Test
    void saveProduct_ReturnsSavedProduct() {
        Product product = new Product();
        product.setName("Monopoly");
        product.setDescription("Classic board game.");
        product.setPrice(BigDecimal.valueOf(29.99));
        product.setStock(100);
        product.setImageUrl("monopoly.jpg");
        // Asumamos que la categoría ya existe:
        Category category = new Category();
        category.setId(1L);
        category.setName("Board Games");
        product.setCategory(category);

        // Simulamos que el repositorio asigna un ID al producto guardado
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName(product.getName());
        savedProduct.setDescription(product.getDescription());
        savedProduct.setPrice(product.getPrice());
        savedProduct.setStock(product.getStock());
        savedProduct.setImageUrl(product.getImageUrl());
        savedProduct.setCategory(product.getCategory());

        when(productRepository.save(product)).thenReturn(savedProduct);

        Product result = productService.save(product);
        assertNotNull(result, "El producto guardado no debe ser nulo");
        assertEquals(1L, result.getId(), "El ID del producto no coincide");
        assertEquals("Monopoly", result.getName(), "El nombre del producto no coincide");

        verify(productRepository, times(1)).save(product);
    }

    // Test 2: Buscar producto por ID existente → retorna producto
    @Test
    void findById_ExistingProduct_ReturnsProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Monopoly");
        product.setDescription("Classic board game.");
        product.setPrice(BigDecimal.valueOf(29.99));
        product.setStock(100);
        product.setImageUrl("monopoly.jpg");
        product.setCategory(new Category(1L, "Board Games", null));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(productId);
        assertTrue(result.isPresent(), "El producto no fue encontrado");
        assertEquals(productId, result.get().getId(), "El ID del producto no coincide");
    }

    // Test 3: Consultar todos los productos → retorna lista de productos
    @Test
    void findAll_ReturnsListOfProducts() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Monopoly");
        p1.setDescription("Classic board game.");
        p1.setPrice(BigDecimal.valueOf(29.99));
        p1.setStock(100);
        p1.setImageUrl("monopoly.jpg");
        p1.setCategory(new Category(1L, "Board Games", null));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Chess");
        p2.setDescription("Strategy board game.");
        p2.setPrice(BigDecimal.valueOf(19.99));
        p2.setStock(50);
        p2.setImageUrl("chess.jpg");
        p2.setCategory(new Category(1L, "Board Games", null));

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> result = productService.findAll();
        assertEquals(2, result.size(), "El tamaño de la lista no coincide");
    }

    // Test 4: Actualizar producto existente → retorna producto actualizado
    @Test
    void updateProduct_ExistingProduct_UpdatesProduct() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Monopoly");
        existingProduct.setDescription("Classic board game.");
        existingProduct.setPrice(BigDecimal.valueOf(29.99));
        existingProduct.setStock(100);
        existingProduct.setImageUrl("monopoly.jpg");
        existingProduct.setCategory(new Category(1L, "Board Games", null));

        Product updatedInfo = new Product();
        updatedInfo.setName("Monopoly Deluxe");
        updatedInfo.setDescription("Deluxe edition board game.");
        updatedInfo.setPrice(BigDecimal.valueOf(39.99));
        updatedInfo.setStock(80);
        updatedInfo.setImageUrl("monopoly_deluxe.jpg");
        updatedInfo.setCategory(new Category(1L, "Board Games", null));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Simulamos la actualización retornando el objeto actualizado
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName(updatedInfo.getName());
        updatedProduct.setDescription(updatedInfo.getDescription());
        updatedProduct.setPrice(updatedInfo.getPrice());
        updatedProduct.setStock(updatedInfo.getStock());
        updatedProduct.setImageUrl(updatedInfo.getImageUrl());
        updatedProduct.setCategory(updatedInfo.getCategory());

        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        Product result = productService.update(productId, updatedInfo);
        assertEquals("Monopoly Deluxe", result.getName(), "El nombre no se actualizó correctamente");
        assertEquals(BigDecimal.valueOf(39.99), result.getPrice(), "El precio no se actualizó correctamente");
    }

    // Test 5: Eliminar producto con ID válido → elimina correctamente
    @Test
    void deleteProduct_ValidId_DeletesProduct() {
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }
}
