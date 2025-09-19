package com.ros.demo.util;

import com.ros.demo.dto.DeliveryDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class RouteOptimizer {

    public String calculateOptimalRoute(DeliveryDTO[] deliveries) {
        // Mock logic: order them as given
        // Later can add distance optimization (e.g., Google Maps, OR-Tools, etc.)
        return Arrays.stream(deliveries)
                .map(d -> d.getPackageId() + " -> " + d.getAddress())
                .collect(Collectors.joining(" | "));
    }
}
