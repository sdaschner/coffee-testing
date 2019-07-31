package com.sebastian_daschner.coffee_shop.orders;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestData {

    private TestData() {
    }

    public static List<Order> unfinishedOrders() {
        Origin colombia = new Origin("Colombia");
        return List.of(
                new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, colombia),
                new Order(UUID.randomUUID(), CoffeeType.LATTE, colombia),
                new Order(UUID.randomUUID(), CoffeeType.POUR_OVER, colombia)
        );
    }

    public static Set<CoffeeType> validCoffeeTypes() {
        return EnumSet.allOf(CoffeeType.class);
    }

    public static List<Origin> validOrigins() {
        Set<CoffeeType> coffeeTypes = validCoffeeTypes();

        return Stream.of("Colombia", "Ethiopia")
                .map(Origin::new)
                .peek(o -> o.getCoffeeTypes().addAll(coffeeTypes))
                .collect(Collectors.toList());
    }

}