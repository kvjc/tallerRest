package com.example.demo1.controller.rest;

import com.example.demo1.dto.OrderDetail.OrderDetailInDTO;
import com.example.demo1.dto.OrderDetail.OrderDetailOutDTO;
import com.example.demo1.dto.OrderDetail.OrderDetailUpdateDTO;
import com.example.demo1.mapper.OrderDetailMapper;
import com.example.demo1.model.OrderDetail;
import com.example.demo1.model.Product;
import com.example.demo1.service.IOrderDetailService;
import com.example.demo1.service.IProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailRestController {

    private final IOrderDetailService orderDetailService;
    private final OrderDetailMapper orderDetailMapper;
    private final IProductService productService;

    public OrderDetailRestController(IOrderDetailService orderDetailService, OrderDetailMapper orderDetailsMapper, IProductService productService) {
        this.orderDetailService = orderDetailService;
        this.orderDetailMapper = orderDetailsMapper;
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<OrderDetailOutDTO> createOrderDetail(@RequestBody OrderDetailInDTO orderDetailInDTO) {
        Optional<Product> optionalProduct = productService.findById(orderDetailInDTO.getProductId());

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }

        Product product = optionalProduct.get();

        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(orderDetailInDTO, product);


        OrderDetail savedOrderDetail = orderDetailService.save(orderDetail);

        OrderDetailOutDTO orderDetailOutDTO = orderDetailMapper.toOrderDetailOutDTO(savedOrderDetail);

        return ResponseEntity.status(201).body(orderDetailOutDTO);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDetailOutDTO>> getAllOrderDetails() {
        Iterable<OrderDetail> orderDetails = orderDetailService.findAll();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetails.forEach(orderDetailList::add);

        List<OrderDetailOutDTO> orderDetailOutDTOs = orderDetailMapper.toOrderDetailOutDTOs(orderDetailList);

        return ResponseEntity.ok(orderDetailOutDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailOutDTO> getOrderDetailById(@PathVariable Long id) {
        Optional<OrderDetail> optionalOrderDetail = orderDetailService.findById(id);

        if (optionalOrderDetail.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        OrderDetailOutDTO orderDetailOutDTO = orderDetailMapper.toOrderDetailOutDTO(optionalOrderDetail.get());
        return ResponseEntity.ok(orderDetailOutDTO);
    }

    public ResponseEntity<Void> updateOrderDetail(
        @PathVariable Long id,
        @RequestBody OrderDetailUpdateDTO orderDetailUpdateDTO) {

    Optional<OrderDetail> optionalOrderDetail = orderDetailService.findById(id);

    if (optionalOrderDetail.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    OrderDetail existingOrderDetail = optionalOrderDetail.get();
    
    // Asegúrate de usar el nombre correcto del método
    orderDetailMapper.updateOrderDetailFromDTO(orderDetailUpdateDTO, existingOrderDetail, existingOrderDetail.getProduct());

    orderDetailService.save(existingOrderDetail);

    return ResponseEntity.noContent().build();
}

@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        Optional<OrderDetail> optionalOrderDetail = orderDetailService.findById(id);

        if (optionalOrderDetail.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        orderDetailService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    
}