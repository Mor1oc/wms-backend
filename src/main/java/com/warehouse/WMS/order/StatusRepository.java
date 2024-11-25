package com.warehouse.WMS.order;

import com.warehouse.WMS.order.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
