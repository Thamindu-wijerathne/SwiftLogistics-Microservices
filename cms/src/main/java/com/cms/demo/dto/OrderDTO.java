package com.cms.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private String status;
    private String address;
    private Long userId;
    private Integer quantity;
    private Double totalPrice;
    private Double shippingFee;
    private LocalDateTime orderDate;
    private String paymentMethod;
    private ItemDTO item;   // nested DTO
}
