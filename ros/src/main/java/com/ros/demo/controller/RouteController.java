package com.ros.demo.controller;

import com.ros.demo.dto.RouteRequestDTO;
import com.ros.demo.dto.RouteResponseDTO;
import com.ros.demo.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping("/optimize")
    public ResponseEntity<RouteResponseDTO> optimizeRoute(@RequestBody RouteRequestDTO requestDTO) {
        RouteResponseDTO responseDTO = routeService.createRoute(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    //Get route by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<RouteResponseDTO> getRoute(@PathVariable Long id) {
//        // implement findById logic here
//        return ResponseEntity.ok(new RouteResponseDTO());
//    }
}
