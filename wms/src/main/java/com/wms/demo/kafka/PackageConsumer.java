package com.wms.demo.kafka;

import com.wms.demo.service.DeliveryPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PackageConsumer {

    private static final Logger log = LoggerFactory.getLogger(PackageConsumer.class);
    private final DeliveryPackageService deliveryPackageService;

    public PackageConsumer(DeliveryPackageService deliveryPackageService){
        this.deliveryPackageService=deliveryPackageService;
    }

    @KafkaListener(topics="wms_package_topic",groupId = "wms")
    public void consume(String orderId){
        System.out.println("New Order Recieved to wms : "+orderId);
        if(orderId!=null){
            String state=deliveryPackageService.addPackage(orderId);
            log.info(state);
        }
    }

}
