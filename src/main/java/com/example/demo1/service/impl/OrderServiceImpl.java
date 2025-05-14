package com.example.demo1.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo1.model.Order;
import com.example.demo1.repository.IOrderRepository;
import com.example.demo1.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;

    public OrderServiceImpl(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Long id, Order order) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setOrderDate(order.getOrderDate());
            updatedOrder.setStatus(order.getStatus());
            updatedOrder.setTotalAmount(order.getTotalAmount());
            updatedOrder.setUser(order.getUser());
            updatedOrder.setOrderDetails(order.getOrderDetails());
            return orderRepository.save(updatedOrder);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
