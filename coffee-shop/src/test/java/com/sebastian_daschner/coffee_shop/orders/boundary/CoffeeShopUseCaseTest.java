package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessorTestDouble;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoffeeShopUseCaseTest {

    private CoffeeShopTestDouble coffeeShop;

    @BeforeEach
    void setUp() {
        OrderProcessorTestDouble orderProcessor = new OrderProcessorTestDouble();
        coffeeShop = new CoffeeShopTestDouble(orderProcessor);
    }

    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setOrigin(new Origin("Colombia"));
        coffeeShop.createOrder(order);
        coffeeShop.verifyCreateOrder(order);
    }

}
