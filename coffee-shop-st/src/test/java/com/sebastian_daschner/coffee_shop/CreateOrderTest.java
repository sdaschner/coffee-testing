package com.sebastian_daschner.coffee_shop;

import com.sebastian_daschner.coffee_shop.entity.Order;
import com.sebastian_daschner.coffee_shop.systems.BaristaSystem;
import com.sebastian_daschner.coffee_shop.systems.CoffeeOrderSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class CreateOrderTest {

    private CoffeeOrderSystem coffeeOrderSystem;
    private BaristaSystem baristaSystem;

    @BeforeEach
    void setUp() {
        coffeeOrderSystem = new CoffeeOrderSystem();
        baristaSystem = new BaristaSystem();
    }

    @Test
    void createVerifyOrder() {
        Order order = new Order("Espresso", "Colombia");
        URI orderUri = coffeeOrderSystem.createOrder(order);

        Order loadedOrder = coffeeOrderSystem.getOrder(orderUri);
        assertThat(loadedOrder).isEqualToComparingOnlyGivenFields(order, "type", "origin");

        assertThat(coffeeOrderSystem.getOrders()).contains(orderUri);
    }

    @Test
    void createOrderCheckStatusUpdate() {
        Order order = new Order("Espresso", "Colombia");
        URI orderUri = coffeeOrderSystem.createOrder(order);

        baristaSystem.answerForOrder(orderUri, "PREPARING");

        Order loadedOrder = coffeeOrderSystem.getOrder(orderUri);
        assertThat(loadedOrder).isEqualToComparingOnlyGivenFields(order, "type", "origin");

        loadedOrder = waitForProcessAndGet(orderUri, "PREPARING");
        assertThat(loadedOrder.getStatus()).isEqualTo("Preparing");

        baristaSystem.answerForOrder(orderUri, "FINISHED");

        loadedOrder = waitForProcessAndGet(orderUri, "FINISHED");
        assertThat(loadedOrder.getStatus()).isEqualTo("Finished");
    }

    private Order waitForProcessAndGet(URI orderUri, String requestedStatus) {
        baristaSystem.waitForInvocation(orderUri, requestedStatus);
        return coffeeOrderSystem.getOrder(orderUri);
    }

    @AfterEach
    void close() {
        coffeeOrderSystem.close();
    }

}