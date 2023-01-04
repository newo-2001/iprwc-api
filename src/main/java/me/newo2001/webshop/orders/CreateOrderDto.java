package me.newo2001.webshop.orders;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public record CreateOrderDto(
        @NotEmpty(message="Order has to contain at least one item")
        List<OrderItemDto> items
) { }
