package com.sebastian_daschner.coffee_shop.orders.entity;

import java.util.stream.Stream;

public enum OrderStatus {

    PREPARING,
    FINISHED,
    COLLECTED;

    public static OrderStatus fromString(String string) {
        return Stream.of(OrderStatus.values())
                .filter(t -> t.name().equalsIgnoreCase(string))
                .findAny().orElse(null);
    }

}
