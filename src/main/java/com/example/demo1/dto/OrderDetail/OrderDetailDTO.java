package com.example.demo1.dto.OrderDetail;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;


public class OrderDetailDTO {
    private Long orderId;
    private Long productId;
    private int quantity;
    private BigDecimal price;

    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    // Getters y setters
}   
