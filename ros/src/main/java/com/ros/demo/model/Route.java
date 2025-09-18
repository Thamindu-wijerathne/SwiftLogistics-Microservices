package com.ros.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleId;
    private String driverId;
    @Lob
    private String routeDetails; // JSON or string representation of the optimized route
    private String status; // e.g., "PENDING", "ASSIGNED", "COMPLETED"
}