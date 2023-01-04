package me.newo2001.webshop.orders;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrderItemDto {
    @NotNull
    private UUID productId;
    @Min(1)
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
