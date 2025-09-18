package com.cms.demo.service;


import com.cms.demo.dto.OrderDTO;
import com.cms.demo.model.Item;
import com.cms.demo.model.Order;
import com.cms.demo.repo.ItemRepository;
import com.cms.demo.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    // Convert Entity -> DTO
    private OrderDTO mapToDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .address(order.getAddress())
                .userId(order.getUserId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .shippingFee(order.getShippingFee())
                .orderDate(order.getOrderDate())
                .paymentMethod(order.getPaymentMethod())
                .item(order.getItem() != null ?
                        com.cms.demo.dto.ItemDTO.builder()
                                .id(order.getItem().getId())
                                .name(order.getItem().getName())
                                .description(order.getItem().getDescription())
                                .price(order.getItem().getPrice())
                                .sku(order.getItem().getSku())
                                .build() : null)
                .build();
    }

    // Create Order
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Item item = itemRepository.findById(orderDTO.getItem().getId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Order order = Order.builder()
                .status(orderDTO.getStatus())
                .address(orderDTO.getAddress())
                .userId(orderDTO.getUserId())
                .quantity(orderDTO.getQuantity())
                .totalPrice(orderDTO.getTotalPrice())
                .shippingFee(orderDTO.getShippingFee())
                .orderDate(LocalDateTime.now())
                .paymentMethod(orderDTO.getPaymentMethod())
                .item(item)
                .build();

        return mapToDTO(orderRepository.save(order));
    }

    // Get all Orders
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get Order by ID
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Delete Order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
