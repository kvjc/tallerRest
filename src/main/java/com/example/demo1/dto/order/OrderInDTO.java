package com.example.demo1.dto.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderInDTO {
   private Long userId; 
    private LocalDate orderDate;
    private String status;
    private BigDecimal totalAmount;
    private List<Long> orderDetails; 

    // Getters y Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Long> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<Long> orderDetails) {
        this.orderDetails = orderDetails;
    } 
}
