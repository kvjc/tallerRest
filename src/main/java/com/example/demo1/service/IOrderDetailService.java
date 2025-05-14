package com.example.demo1.service;

import com.example.demo1.model.OrderDetail;

import java.util.List;
import java.util.Optional;

import com.example.demo1.dto.OrderDetail.OrderDetailInDTO;
import com.example.demo1.dto.OrderDetail.OrderDetailUpdateDTO;
import com.example.demo1.dto.order.OrderDetailDTO;

public interface IOrderDetailService {
    List<OrderDetail> findAll();
    Optional<OrderDetail> findById(Long id);
    OrderDetail save(OrderDetailInDTO orderDetailInDTO); 
    OrderDetail update(Long id, OrderDetailUpdateDTO orderDetailUpdateDTO); 
    void deleteById(Long id); 
}
