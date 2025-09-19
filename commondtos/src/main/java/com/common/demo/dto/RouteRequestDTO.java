package com.common.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class RouteRequestDTO {
    private String vehicleId;
    private String driverId;
    private DeliveryDTO[] deliveries;
    public RouteRequestDTO() {}

}
