package com.cms.demo.service;


import com.cms.demo.dto.ItemDTO;
import com.cms.demo.dto.OrderDTO;
import com.cms.demo.kafka.DeliveryPackageProducer;
import com.cms.demo.model.Item;
import com.cms.demo.model.Order;
import com.cms.demo.repo.ItemRepository;
import com.cms.demo.repo.OrderRepository;
import com.common.demo.dto.DeliveryDTO;
import com.common.demo.dto.RouteRequestDTO;
import com.common.demo.dto.RouteResponseDTO;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//@Slf4j
@Service

public class OrderService {
    private final WebClient webClient;

    @Autowired
    DeliveryPackageProducer deliveryPackageProducer;

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;


    @Autowired
    private ItemService itemService;

    public OrderService(WebClient.Builder webClientBuilder, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api/routes").build();
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }


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
                        ItemDTO.builder()
                                .id(order.getItem().getId())
                                .name(order.getItem().getName())
                                .description(order.getItem().getDescription())
                                .price(order.getItem().getPrice())
                                .sku(order.getItem().getSku())
                                .stock(order.getItem().getStock())
                                .build() : null)
                .build();
    }

    // Create Order
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Item item = itemRepository.findById(orderDTO.getItem().getId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Decrease stock after order placed
        itemService.decreaseStock(orderDTO.getItem().getId(), orderDTO.getQuantity());

        Order order = Order.builder()
                .status("NOT_DELIVERED")
                .address(orderDTO.getAddress())
                .userId(orderDTO.getUserId())
                .quantity(orderDTO.getQuantity())
                .totalPrice(orderDTO.getTotalPrice())
                .shippingFee(orderDTO.getShippingFee())
                .orderDate(LocalDateTime.now())
                .paymentMethod(orderDTO.getPaymentMethod())
                .item(item)
                .build();

        Order savedOrder = orderRepository.save(order);
        deliveryPackageProducer.SendMessageOnCreateOrder(savedOrder.getId().toString());
        return mapToDTO(savedOrder);
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

    public OrderDTO compleOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("DELIVERED");

        order = orderRepository.save(order);

        return mapToDTO(order);

    }

    //get not delivered orders
    public RouteResponseDTO getNotDeliveredAddresses(){

        List<Object[]> addresses = orderRepository.findAddressAndOrderIdForNotDelivered();
        if (addresses == null || addresses.isEmpty()) {
//            log.warn("No not-delivered orders found.");
            return null;
        }
        List<DeliveryDTO> deliveryDTOList = new ArrayList<>();
        for (Object[] row : addresses) {

            Long itemId = (Long) row[0];
            String address = (String) row[1];
            DeliveryDTO deliveryDTO = new DeliveryDTO();
            deliveryDTO.setPackageId(String.valueOf(itemId));
            deliveryDTO.setAddress(address);

            deliveryDTOList.add(deliveryDTO);

        }
        DeliveryDTO[] deliveryArray = deliveryDTOList.toArray(new DeliveryDTO[0]);
        RouteRequestDTO routeRequestDTO=new RouteRequestDTO();
        routeRequestDTO.setVehicleId("SWIFT_VEHICLE");
        routeRequestDTO.setDriverId("SWIFT_DRIVER");
        routeRequestDTO.setDeliveries(deliveryArray);
        System.out.println(routeRequestDTO);

        try{
            RouteResponseDTO response=webClient.post()
                    .uri("/optimize")
                    .bodyValue(routeRequestDTO)
                    .retrieve()
                    .bodyToMono(RouteResponseDTO.class)
                    .block();

            return response;
        }
        catch (Exception e){
//            log.error("ERROR",e);
            return null;
        }
    }

    // Get orders by user
    public List<Order> getOrdersByUserId(String id) {
        return orderRepository.getOrdersByUserId(id);
    }
}
