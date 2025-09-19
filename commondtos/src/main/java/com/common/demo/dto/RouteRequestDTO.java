package com.common.demo.dto;

import lombok.Data;

@Data
public class RouteRequestDTO {
    private String vehicleId;
    private String driverId;
    private DeliveryDTO[] deliveries;
}

