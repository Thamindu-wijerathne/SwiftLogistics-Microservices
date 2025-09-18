package com.ros.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ros.demo.dto.RouteRequestDTO;
import com.ros.demo.dto.RouteResponseDTO;
import com.ros.demo.model.Route;
import com.ros.demo.repository.RouteRepository;
import com.ros.demo.util.RouteOptimizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteOptimizer routeOptimizer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RouteResponseDTO createRoute(RouteRequestDTO requestDTO) {
        // Optimize route
        String optimizedRoute = routeOptimizer.calculateOptimalRoute(requestDTO.getDeliveries());

        // Save to DB
        Route route = new Route();
        route.setVehicleId(requestDTO.getVehicleId());
        route.setDriverId(requestDTO.getDriverId());
        route.setRouteDetails(optimizedRoute);
        route.setStatus("ASSIGNED");

        Route savedRoute = routeRepository.save(route);

        // Prepare response
        RouteResponseDTO responseDTO = new RouteResponseDTO();
        responseDTO.setRouteId(savedRoute.getId());
        responseDTO.setRouteDetails(savedRoute.getRouteDetails());
        responseDTO.setStatus(savedRoute.getStatus());

        return responseDTO;
    }
}