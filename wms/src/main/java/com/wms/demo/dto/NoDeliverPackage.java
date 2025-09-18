package com.wms.demo.dto;

import lombok.Getter;

@Getter
public class NoDeliverPackage implements DeliveryPackageResponse{
    private final String Error;

    public NoDeliverPackage(String error) {
        Error = error;
    }
}
