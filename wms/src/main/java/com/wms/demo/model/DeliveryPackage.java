package com.wms.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String packageId;
    private String status; // RECEIVED, IN_WAREHOUSE, LOADED, DELIVERED , NOT_DELIVERED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String remarks;
}
