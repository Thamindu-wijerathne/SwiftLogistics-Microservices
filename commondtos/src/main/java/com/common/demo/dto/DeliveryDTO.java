package com.common.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DeliveryDTO {
    private String packageId;
    private String address;
    public DeliveryDTO() {}

    public DeliveryDTO(String packageId, String address) {
        this.packageId = packageId;
        this.address = address;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}