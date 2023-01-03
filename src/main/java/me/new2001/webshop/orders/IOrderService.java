package me.new2001.webshop.orders;

import me.new2001.webshop.common.pagination.Paginated;
import me.new2001.webshop.common.pagination.PaginationRequest;
import me.new2001.webshop.products.Product;

import java.util.Optional;
import java.util.UUID;

public interface IOrderService {
    Optional<Order> findOrderById(UUID id);
    Paginated<Order> getOrderPageByUserId(UUID userId, PaginationRequest page);
    Order createOrder(UUID userId, CreateOrderDto dto);
    void deleteOrder(UUID id);
}
