package com.cms.demo.controller;

import com.cms.demo.dto.OrderDTO;
import com.cms.demo.model.Order;
import com.cms.demo.service.OrderService;
import com.common.demo.dto.RouteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create Order
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {

        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    // Get All Orders
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Get Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<OrderDTO> completeOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.compleOrder(id));
    }

    @GetMapping("/getDeliveryAddresses")
    public ResponseEntity<RouteResponseDTO> getDeliveryAddresses(){
        return ResponseEntity.ok(orderService.getNotDeliveredAddresses());
    }

    @GetMapping("/getOrders/{userid}")
    public List<Order> getOrders(@PathVariable("userid") String id){
        return orderService.getOrdersByUserId(id);
    }
}
