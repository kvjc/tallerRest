package com.example.demo1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo1.dto.product.ProductInDTO;
import com.example.demo1.dto.product.ProductOutDTO;
import com.example.demo1.dto.product.ProductUpdateDTO;
import com.example.demo1.model.Category;
import com.example.demo1.model.Product;

//Cuando se va a crear o actualizar un producto, se necesita buscar primero la categoría por su id, para poder pasarla al mapper.

@Mapper(componentModel = "spring")
public interface  ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductInDTO dto);

    @Mapping(source = "category.name", target = "categoryName")
    ProductOutDTO toProductOutDTO(Product product);

    @Mapping(target = "category", ignore = true)
    void updateProductFromDto(ProductUpdateDTO dto, @MappingTarget Product product);

}
