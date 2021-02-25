package com.sebastian_daschner.coffee_shop.orders.entity;

import java.util.stream.Stream;

public enum CoffeeType {

    ESPRESSO("Espresso"),
    POUR_OVER("Pour over"),
    LATTE("Latte");

    private final String description;

    CoffeeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static CoffeeType fromString(String string) {
        return Stream.of(CoffeeType.values())
                .filter(t -> t.name().equalsIgnoreCase(string))
                .findAny().orElse(null);
    }

}
