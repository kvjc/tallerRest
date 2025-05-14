package com.example.demo1.mapper;

import com.example.demo1.dto.category.CategoryInDTO;
import com.example.demo1.dto.category.CategoryOutDTO;
import com.example.demo1.dto.category.CategoryUpdateDTO;
import com.example.demo1.model.Category;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryInDTO dto);

    CategoryOutDTO toCategoryOutDTO(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "products", ignore = true)
    void updateCategoryFromDTO(CategoryUpdateDTO dto, @MappingTarget Category category);
    
}
