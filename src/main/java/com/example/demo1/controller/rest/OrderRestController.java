package com.example.demo1.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.dto.order.OrderInDTO;
import com.example.demo1.dto.order.OrderOutDTO;
import com.example.demo1.dto.order.OrderUpdateDTO;
import com.example.demo1.mapper.OrderMapper;
import com.example.demo1.model.Order;
import com.example.demo1.service.IOrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final IOrderService orderService;
    private final OrderMapper orderMapper;

    public OrderRestController(IOrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderOutDTO> createOrder(@RequestBody OrderInDTO orderInDTO) {
        Order order = orderMapper.toOrder(orderInDTO);
        Order savedOrder = orderService.save(order);

        OrderOutDTO orderOutDTO = orderMapper.toOrderOutDTO(savedOrder);

        return ResponseEntity.status(201).body(orderOutDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderOutDTO>> getAllOrders() {
        Iterable<Order> orders = orderService.findAll();

        List<OrderOutDTO> orderOutDTOs = new ArrayList<>();
        orders.forEach(order -> orderOutDTOs.add(orderMapper.toOrderOutDTO(order)));

        return ResponseEntity.ok(orderOutDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateDTO orderUpdateDTO) {
        Optional<Order> optionalOrder = orderService.findById(id);

        if (optionalOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order existingOrder = optionalOrder.get();
        orderMapper.updateOrderFromDto(orderUpdateDTO, existingOrder);
        orderService.save(existingOrder);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.findById(id);

        if (optionalOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        orderService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutDTO> getOrderById(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.findById(id);

        if (optionalOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        OrderOutDTO orderOutDTO = orderMapper.toOrderOutDTO(optionalOrder.get());

        return ResponseEntity.ok(orderOutDTO);
    }
    
}
