package com.sebastian_daschner.coffee_shop.orders;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;

import java.util.List;
import java.util.UUID;

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
}