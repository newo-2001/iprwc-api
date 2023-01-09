package me.newo2001.webshop.orders;

import me.newo2001.webshop.common.NotFoundException;
import me.newo2001.webshop.products.IProductRepository;
import me.newo2001.webshop.products.Product;
import me.newo2001.webshop.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderBuilder implements IOrderBuilder {
    private final IProductRepository productRepository;

    @Autowired
    public OrderBuilder(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public IOrderLineBuilder forUser(User user) {
        return new OrderLineBuilder(new Order(user));
    }

    public class OrderLineBuilder implements IOrderLineBuilder {
        private final Map<UUID, Integer> orderLines = new HashMap<>();
        private final Order order;

        public OrderLineBuilder(Order order) {
            this.order = order;
        }

        public IOrderLineBuilder addItem(OrderItemDto item) {
            UUID productId = item.getProductId();
            if (orderLines.containsKey(productId)) {
                orderLines.put(productId, orderLines.get(productId) + item.getAmount());
            } else {
                orderLines.put(productId, item.getAmount());
            }
            return this;
        }

        public IOrderLineBuilder addItems(Collection<OrderItemDto> items) {
            items.forEach(this::addItem);
            return this;
        }

        public Order build() {
            Map<UUID, Product> products = productRepository.findAllById(orderLines.keySet())
                    .stream().collect(Collectors.toMap(Product::getId, item -> item));

            if (!orderLines.keySet().stream().allMatch(products::containsKey)) {
                throw new IllegalArgumentException("Order contained invalid product id");
            }

            order.setOrderLines(orderLines.entrySet().stream().map(entry -> new OrderLine(
                    products.get(entry.getKey()),
                    entry.getValue()
            )).collect(Collectors.toSet()));

            return order;
        }
    }
}
