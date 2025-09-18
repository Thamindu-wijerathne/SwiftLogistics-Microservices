package com.wms.demo.service;

import com.wms.demo.dto.*;
import com.wms.demo.model.DeliveryPackage;
import com.wms.demo.repo.DeliveryPackageRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class DeliveryPackageService {
    @Autowired
    DeliveryPackageRepo packageRepo;

    @Autowired
    TCPService tcpService;

    public String addPackage(String packageId){
        DeliveryPackage newPackage=new DeliveryPackage();
        newPackage.setPackageId(packageId);
        newPackage.setStatus("Received");
        newPackage.setRemarks("Package received to the warehouse");
        newPackage.setCreatedAt(LocalDateTime.now());
        newPackage.setUpdatedAt(LocalDateTime.now());
        try{
            DeliveryPackage addedPackage=packageRepo.save(newPackage);
            if(addedPackage.getId()!=null){
                DeliveryPackageDTO deliveryPackageDTO=new DeliveryPackageDTO(packageId, newPackage.getStatus(), newPackage.getRemarks(), newPackage.getCreatedAt(),newPackage.getUpdatedAt());
                tcpService.SendTCPMessage(deliveryPackageDTO); // sending the insertion to the clients and other services through the tcp connection
                return "Package Added with ID"+addedPackage.getId().toString();
            }
            return "Package Adding failed";
        }
        catch (Exception e){
            log.error("ADDING ERROR: ", e);
            return "Package Adding failed";
        }
    }

    public DeliveryPackageResponse viewPackage(String packageId){
        Optional <DeliveryPackage> deliveryPackage = packageRepo.findByPackageId(packageId);
        if(deliveryPackage.isPresent()){
            DeliveryPackage packageDetails=deliveryPackage.get();
            DeliveryPackageDTO detailstoSend=new DeliveryPackageDTO(packageDetails.getPackageId(),packageDetails.getStatus(),packageDetails.getRemarks(),packageDetails.getCreatedAt(),packageDetails.getUpdatedAt());
            return new PresentDeliverPackage(detailstoSend);
        }
        else {
            return new NoDeliverPackage("No Package found with that id");

        }
    }


    public boolean updatePackage(UpdateStateDTO updateStateDTO){
        String packageId=updateStateDTO.getOrderid();
        String state=updateStateDTO.getState();
        String remarks= updateStateDTO.getRemarks();
        Optional <DeliveryPackage> deliveryPackage = packageRepo.findByPackageId(packageId);
        if(deliveryPackage.isPresent()){
            DeliveryPackage pkgToUpdate=deliveryPackage.get();
            try{
                pkgToUpdate.setStatus(state);
                pkgToUpdate.setUpdatedAt(LocalDateTime.now());
                pkgToUpdate.setRemarks(remarks);
                DeliveryPackage updatedPackage=packageRepo.save(pkgToUpdate);
                if(updatedPackage.getId()!=null){
                    DeliveryPackageDTO deliveryPackageDTO=new DeliveryPackageDTO(packageId, updatedPackage.getStatus(), updatedPackage.getRemarks(), updatedPackage.getCreatedAt(),updatedPackage.getUpdatedAt());
                    tcpService.SendTCPMessage(deliveryPackageDTO); // sending the insertion to the clients and other services through the tcp connection
                    return true;

                }
                return false;

            }
            catch (Exception e){
                log.error("ERROR : ",e);
                return false;
            }
        }
        else {
            return false;
        }
    }



}

