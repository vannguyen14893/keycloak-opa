package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
