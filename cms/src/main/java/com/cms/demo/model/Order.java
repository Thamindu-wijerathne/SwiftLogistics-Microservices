package com.cms.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;          // pending, shipped, delivered, canceled
    private String address;
    private Long userId;            // Reference to User (if no direct relation yet)

    private Integer quantity;
    private Double totalPrice;
    private Double shippingFee;

    private LocalDateTime orderDate;    // Extra relevant attribute
    private String paymentMethod;       // e.g., card, cash-on-delivery

    // One order has one item
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;
}