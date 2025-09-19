package com.ros.demo.dto;

import lombok.Data;

@Data
public class RouteResponseDTO {
    private Long routeId;
    private String routeDetails;
    private String status;
}
