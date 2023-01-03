package me.new2001.webshop.orders;

import me.new2001.webshop.common.NotFoundException;
import me.new2001.webshop.common.pagination.Paginated;
import me.new2001.webshop.common.pagination.PaginationRequest;
import me.new2001.webshop.products.IProductRepository;
import me.new2001.webshop.products.Product;
import me.new2001.webshop.users.IUserRepository;
import me.new2001.webshop.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final IOrderBuilder orderBuilder;

    @Autowired
    public OrderService(IOrderRepository orderRepository,
                        IUserRepository userRepository,
                        IOrderBuilder orderBuilder) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderBuilder = orderBuilder;
    }

    @Override
    public Optional<Order> findOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public Paginated<Order> getOrderPageByUserId(UUID userId, PaginationRequest pageRequest) {
        return Paginated.fromPage(orderRepository.findAllByUserId(userId, pageRequest.asPageRequest()));
    }

    @Override
    public Order createOrder(UUID userId, CreateOrderDto dto) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        return orderRepository.save(orderBuilder.forUser(user).addItems(dto.items()).build());
    }

    @Override
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                        .orElseThrow(NotFoundException::new);
        orderRepository.delete(order);
    }
}
