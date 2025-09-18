package com.wms.demo.repo;

import com.wms.demo.model.DeliveryPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryPackageRepo extends JpaRepository<DeliveryPackage,Long> {
    Optional<DeliveryPackage> findByPackageId(String packageId);

    List<DeliveryPackage> findByStatus(String status);
}
