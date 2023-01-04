package me.newo2001.webshop.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.newo2001.webshop.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="orders")
public class Order {
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<OrderLine> items;

    @OneToOne
    @NotNull
    private User user;

    @NotNull
    private final LocalDateTime orderTime;

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    public Order() {
        this.orderTime = LocalDateTime.now();
    }

    public Order(User user) {
        this();
        this.user = user;
        this.items = Set.of();
    }

    public Order(User user, Set<OrderLine> orderLines) {
        this();
        this.user = user;
        this.items = orderLines;
    }

    public Set<OrderLine> getItems() {
        return items;
    }

    public void setOrderLines(Set<OrderLine> items) {
        this.items = items;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public UUID getUserId() {
        return user.getId();
    }

    public UUID getId() {
        return id;
    }
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
}
