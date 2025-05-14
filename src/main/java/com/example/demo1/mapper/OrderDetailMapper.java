package com.example.demo1.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo1.dto.OrderDetail.OrderDetailInDTO;
import com.example.demo1.dto.OrderDetail.OrderDetailOutDTO;
import com.example.demo1.dto.OrderDetail.OrderDetailUpdateDTO;
import com.example.demo1.dto.order.OrderDetailDTO;
import com.example.demo1.model.OrderDetail;
import com.example.demo1.model.Product;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderDetail toOrderDetail(OrderDetailInDTO dto);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "order.id", target = "orderId")
    OrderDetailOutDTO toOrderDetailOutDTO(OrderDetail orderDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateOrderDetailFromDTO(OrderDetailInDTO dto, @MappingTarget OrderDetail orderDetail);
    
}
