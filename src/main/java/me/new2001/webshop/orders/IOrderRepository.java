package me.new2001.webshop.orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByUserId(UUID userId, Pageable pageable);
}
