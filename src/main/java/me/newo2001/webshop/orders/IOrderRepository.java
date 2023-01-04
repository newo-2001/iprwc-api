package me.newo2001.webshop.orders;

import me.newo2001.webshop.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByUser(User user, Pageable pageable);
}
