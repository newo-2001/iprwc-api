package me.newo2001.webshop.orders;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderDto(
        @NotNull(message="Items must not be null")
        @NotEmpty(message="Order has to contain at least one item")
        @Valid
        List<OrderItemDto> items
) { }
