package com.example.demo1.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo1.model.OrderDetail;
import com.example.demo1.repository.IOrderDetailRepository;
import com.example.demo1.service.IOrderDetailService;
@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    private final IOrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(IOrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail update(Long id, OrderDetail orderDetail) {
        Optional<OrderDetail> existingOrderDetail = orderDetailRepository.findById(id);
        if (existingOrderDetail.isPresent()) {
            OrderDetail updatedOrderDetail = existingOrderDetail.get();
            updatedOrderDetail.setQuantity(orderDetail.getQuantity());
            updatedOrderDetail.setPrice(orderDetail.getPrice());
            updatedOrderDetail.setOrder(orderDetail.getOrder());
            updatedOrderDetail.setProduct(orderDetail.getProduct());
            return orderDetailRepository.save(updatedOrderDetail);
        } else {
            throw new RuntimeException("OrderDetail not found");
        }
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
