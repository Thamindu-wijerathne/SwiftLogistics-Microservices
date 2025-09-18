package com.wms.demo.controller;

import com.wms.demo.dto.DeliveryPackageResponse;
import com.wms.demo.dto.UpdateStateDTO;
import com.wms.demo.service.DeliveryPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wms/v1")
@CrossOrigin(origins = "*")  // allow all origins for this controller
public class DeliveryPackageController {

    @Autowired
    DeliveryPackageService deliveryPackageService;

    @GetMapping("/add/{packageID}")
    public String addPackage(@PathVariable String packageID){
        return deliveryPackageService.addPackage(packageID);

    }

    @GetMapping("/get/{packageID}")
    public DeliveryPackageResponse getPackageDetails(@PathVariable String packageID){
        return deliveryPackageService.viewPackage(packageID);
    }

    @PostMapping("/updatePackageState")
    public ResponseEntity<String> updatePackage(@RequestBody UpdateStateDTO updateStateDTO){
              boolean state=  deliveryPackageService.updatePackage(updateStateDTO);
              if(state==true){
                  return ResponseEntity.status(200).body("Updated Successfully");
              }
              else {
                  return ResponseEntity.status(500).body(("Failed To Update - Item not found"));
              }
    }






}
