package org.okarmus.repository;

import org.okarmus.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by mateusz on 30.11.16.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
}
