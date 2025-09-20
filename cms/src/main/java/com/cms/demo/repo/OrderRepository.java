package com.cms.demo.repo;

import com.cms.demo.dto.OrderDTO;
import com.cms.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.id, o.address FROM Order o WHERE o.status <> 'DELIVERED'")
    List<Object[]> findAddressAndOrderIdForNotDelivered();

    @Query("SELECT o FROM Order o WHERE o.userId = :id")
    List<Order> getOrdersByUserId(@Param("id") String id);

}
