package com.wms.demo.dto;

import lombok.Getter;

@Getter
public class PresentDeliverPackage implements DeliveryPackageResponse {
    private final DeliveryPackageDTO deliveryPackageDTO;


    public PresentDeliverPackage(DeliveryPackageDTO deliveryPackageDTO) {
        this.deliveryPackageDTO = deliveryPackageDTO;
    }
}
