package com.sebastian_daschner.coffee_shop.orders.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

import static com.sebastian_daschner.coffee_shop.orders.entity.Order.FIND_ALL;
import static com.sebastian_daschner.coffee_shop.orders.entity.Order.FIND_UNFINISHED;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = FIND_UNFINISHED, query = "select o from Order o where o.status <> 'COLLECTED'"),
        @NamedQuery(name = FIND_ALL, query = "select o from Order o")})
public class Order {

    public static final String FIND_UNFINISHED = "Order.findUnfinished";
    public static final String FIND_ALL = "Order.findAll";

    @Id
    private String id;

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
        this.id = id.toString();
        this.type = type;
        this.origin = origin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
