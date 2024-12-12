package com.warehouse.WMS.order;

import com.warehouse.WMS.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getAllByStatusId(Integer id);

    List<Order> findOrdersByStatusId(int i);
}
