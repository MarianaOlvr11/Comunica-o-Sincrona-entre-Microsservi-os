package br.edu.ifsp.orderservice.model;

import java.time.LocalDateTime;

public class Order {
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private ProductInfo product;

    public Order() {}

    public Order(Long orderId, Long productId, Integer quantity, ProductInfo product) {
        this.orderId    = orderId;
        this.productId  = productId;
        this.quantity   = quantity;
        this.product    = product;
        this.totalPrice = product.getPrice() * quantity;
        this.status     = "CONFIRMED";
        this.createdAt  = LocalDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public ProductInfo getProduct() { return product; }
    public void setProduct(ProductInfo product) { this.product = product; }
}
