package com.sebastian_daschner.coffee_shop.orders.entity;

import org.assertj.core.api.AbstractAssert;

public class OrderAssert extends AbstractAssert<OrderAssert, Order> {

    public OrderAssert(Order task) {
        super(task, OrderAssert.class);
    }

    public static OrderAssert assertThat(Order actual) {
        return new OrderAssert(actual);
    }

    public OrderAssert isPreparing() {
        isNotNull();
        if (actual.getStatus() != OrderStatus.PREPARING) {
            failWithMessage("Expected the order to be in status PREPARING but was %s", actual.getStatus());
        }
        return this;
    }

    public OrderAssert containsMilk() {
        isNotNull();
        if (actual.getType() != CoffeeType.LATTE) {
            failWithMessage("Expected the coffee order to contain milk but the coffee type was %s", actual.getType());
        }
        return this;
    }

}
