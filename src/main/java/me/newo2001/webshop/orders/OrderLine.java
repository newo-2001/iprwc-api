package me.newo2001.webshop.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.newo2001.webshop.products.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name="order_lines")
@Entity
public class OrderLine implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "uuid", updatable = false)
    @JsonIgnore
    private UUID id;

    @OneToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    private int amount;

    public OrderLine(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public OrderLine() { }

    public Product getProduct() {
        return product;
    }

    public UUID getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
    public void addAmount(int amount) {
        this.amount += amount;
    }
}
