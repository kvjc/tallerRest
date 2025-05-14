package com.example.demo1.dto.order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDetailDTO {

    private LocalDate orderDate;
    private String status;
    private BigDecimal totalAmount;

    // Getters y Setters
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
    
}
