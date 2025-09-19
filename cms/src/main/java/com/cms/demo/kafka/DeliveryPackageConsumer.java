package com.cms.demo.kafka;

import com.cms.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPackageConsumer {
    private static final Logger log = LoggerFactory.getLogger(DeliveryPackageConsumer.class);
    private final OrderService orderService;

    public DeliveryPackageConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics="delivered_pkg_topic",groupId = "cms") //listning to package delivered topic
    public void consume(String orderId){
        try{
            orderService.compleOrder(Long.valueOf(orderId));
            log.info("Package with the ID is delivered  and updated ID : {} ", orderId); //completing the order
        }
        catch (Exception e){
            log.error("ERROR",e);
        }

    }

}
