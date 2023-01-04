package me.newo2001.webshop.orders;

import me.newo2001.webshop.common.NotFoundException;
import me.newo2001.webshop.common.pagination.Paginated;
import me.newo2001.webshop.common.pagination.PaginationRequest;
import me.newo2001.webshop.users.IUserRepository;
import me.newo2001.webshop.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Paginated<Order> getOrderPageByUser(User user, PaginationRequest pageRequest) {
        return Paginated.fromPage(orderRepository.findAllByUser(user, pageRequest.asPageRequest()));
    }

    @Override
    public Order createOrder(UUID userId, CreateOrderDto dto) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        Order order = orderBuilder.forUser(user).addItems(dto.items()).build();
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                        .orElseThrow(NotFoundException::new);
        orderRepository.delete(order);
    }
}
