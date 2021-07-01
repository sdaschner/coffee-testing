package com.sebastian_daschner.coffee_shop.backend.entity;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class OrderAssert extends AbstractAssert<OrderAssert, Order> {

    public OrderAssert(Order order) {
        super(order, OrderAssert.class);
    }

    public static OrderAssert assertThat(Order actual) {
        return new OrderAssert(actual);
    }

    public OrderAssert matchesOrderedData(Order other) {
        isNotNull();
        Assertions.assertThat(actual.getType()).isEqualTo(other.getType());
        Assertions.assertThat(actual.getOrigin()).isEqualTo(other.getOrigin());
        return this;
    }

}
