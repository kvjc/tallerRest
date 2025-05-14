package com.example.demo1.service;

import com.example.demo1.model.Order;

import java.util.List;
import java.util.Optional;

import com.example.demo1.dto.order.OrderInDTO;
import com.example.demo1.dto.order.OrderUpdateDTO;

public interface IOrderService {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order save(OrderInDTO orderInDTO);
    Order update(Long id, OrderUpdateDTO orderUpdateDTO);
    void deleteById(Long id);
}
