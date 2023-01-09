package me.newo2001.webshop.orders;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrderItemDto {
    @NotNull(message="Product id must not be null")
    private UUID productId;
    @NotNull(message="Amount must not be null")
    @Min(value=1, message="Amount must be positive")
    private int amount = 1;

    public OrderItemDto() { }

    public OrderItemDto(UUID productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public UUID getProductId() {
        return productId;
    }
}
