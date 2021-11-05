package com.sebastian_daschner.coffee_shop.orders.entity;

import com.sebastian_daschner.coffee_shop.orders.control.Strings;

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

    public String getIdentifier() {
        return Strings.capitalize(name());
    }

    public static CoffeeType fromString(String string) {
        return Stream.of(CoffeeType.values())
                .filter(t -> t.name().equalsIgnoreCase(string))
                .findAny().orElse(null);
    }

}
