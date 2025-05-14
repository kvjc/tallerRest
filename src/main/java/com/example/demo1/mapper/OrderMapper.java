package com.example.demo1.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo1.dto.order.OrderDetailDTO;
import com.example.demo1.dto.order.OrderInDTO;
import com.example.demo1.dto.order.OrderOutDTO;
import com.example.demo1.dto.order.OrderUpdateDTO;
import com.example.demo1.model.AppUser;
import com.example.demo1.model.Order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderOutDTO toOrderOutDTO(Order order);

    List<OrderOutDTO> toOrderOutDTOs(List<Order> orders);

    default Order toOrder(OrderInDTO dto, AppUser user) {
        if (dto == null) return null;

        Order order = new Order();
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotalAmount());
        order.setOrderDate(java.time.LocalDate.now());
        order.setUser(user);
        return order;
    }

}
