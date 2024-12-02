package com.warehouse.WMS.order;

import com.warehouse.WMS.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getAllByStatusId(Integer id);
}
