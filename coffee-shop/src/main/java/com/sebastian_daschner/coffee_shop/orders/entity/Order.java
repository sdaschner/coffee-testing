package com.sebastian_daschner.coffee_shop.orders.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private UUID id;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private CoffeeType type;

    @ManyToOne(optional = false)
    private Origin origin;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PREPARING;

    public Order() {
    }

    public Order(UUID id, CoffeeType type, Origin origin) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(origin);
        this.id = id;
        this.type = type;
        this.origin = origin;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CoffeeType getType() {
        return type;
    }

    public void setType(CoffeeType type) {
        this.type = type;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id='" + id + '\'' +
               ", type='" + type + '\'' +
               ", origin='" + origin + '\'' +
               ", status=" + status +
               '}';
    }

}
