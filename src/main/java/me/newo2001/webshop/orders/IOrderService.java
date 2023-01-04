package me.newo2001.webshop.orders;

import me.newo2001.webshop.common.pagination.Paginated;
import me.newo2001.webshop.common.pagination.PaginationRequest;
import me.newo2001.webshop.users.User;

import java.util.Optional;
import java.util.UUID;

public interface IOrderService {
    Optional<Order> findOrderById(UUID id);
    Paginated<Order> getOrderPageByUser(User userId, PaginationRequest page);
    Order createOrder(UUID userId, CreateOrderDto dto);
    void deleteOrder(UUID id);
}
