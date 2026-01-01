package com.example.demo.dto;

public class PaymentDTO {
    private Long id;
    private Long orderId;
    private String status;

    public PaymentDTO() { }
    public PaymentDTO(Long id, Long orderId, String status) {
        this.id = id; this.orderId = orderId; this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}