package com.warehouse.WMS.security;

import com.warehouse.WMS.security.model.WmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WmsUserRepository extends JpaRepository<WmsUser, String> {
}
