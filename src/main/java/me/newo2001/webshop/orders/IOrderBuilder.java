package me.newo2001.webshop.orders;

import me.newo2001.webshop.users.User;

import java.util.Collection;

public interface IOrderBuilder {
    IOrderLineBuilder forUser(User user);

    interface IOrderLineBuilder {
        IOrderLineBuilder addItem(OrderItemDto item);
        IOrderLineBuilder addItems(Collection<OrderItemDto> items);
        Order build();
    }
}
