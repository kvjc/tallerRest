package com.example.demo1.dto.OrderDetail;

import java.math.BigDecimal;

public class OrderDetailUpdateDTO {

    private Integer quantity;
    private BigDecimal price;
    private Long productId;
    
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
