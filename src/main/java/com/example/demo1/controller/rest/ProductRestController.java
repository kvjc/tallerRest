package com.example.demo1.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo1.dto.product.ProductInDTO;
import com.example.demo1.dto.product.ProductOutDTO;
import com.example.demo1.dto.product.ProductUpdateDTO;
import com.example.demo1.mapper.ProductMapper;
import com.example.demo1.service.IProductService;
import com.example.demo1.model.Category;
import com.example.demo1.model.Product;
import com.example.demo1.service.ICategoryService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductRestController {
    
    private IProductService productService;
    private ProductMapper productMapper;
    private ICategoryService categoryService;


    @PostMapping
    public ResponseEntity<ProductOutDTO> createProduct(@RequestBody ProductInDTO productInDTO) {

        Optional<Category> optionalCategory = categoryService.findById(productInDTO.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }

        Category category = optionalCategory.get();
        Product product = productMapper.toProduct(productInDTO, category);
        Product savedProduct = productService.save(product);
        ProductOutDTO productOutDTO = productMapper.toProductOutDTO(savedProduct, true);

        return ResponseEntity.status(201).body(productOutDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductOutDTO>> getAllProducts() {
        Iterable<Product> products = productService.findAll();

        List<Product> productList = new ArrayList<>();
        products.forEach(productList::add);

        List<ProductOutDTO> productOutDTOs = productList.stream()
                .map(product -> productMapper.toProductOutDTO(product, true))   
                .collect(Collectors.toList());

        return ResponseEntity.ok(productOutDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOutDTO> getProductById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProductOutDTO productOutDTO = productMapper.toProductOutDTO(optionalProduct.get(), true);
        return ResponseEntity.ok(productOutDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateDTO productUpdateDTO) {

        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existingProduct = optionalProduct.get();

        productMapper.updateProductFromDto(productUpdateDTO, existingProduct);

        productService.save(existingProduct);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }




}
