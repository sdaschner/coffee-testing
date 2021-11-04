package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.TestData;
import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessorTestDouble;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class CoffeeShopUseCaseTest {

    private CoffeeShopTestDouble coffeeShop;

    @BeforeEach
    void setUp() {
        OrderProcessorTestDouble orderProcessor = new OrderProcessorTestDouble();
        coffeeShop = new CoffeeShopTestDouble(orderProcessor);
    }

    @Test
    @AllureId("44")
    void testRenamedCreateOrder() {
        Order order = new Order();
        coffeeShop.createOrder(order);
        coffeeShop.verifyCreateOrder(order);
    }

    @Test
    @AllureId("37")
    void testProcessUnfinishedOrders() {
        List<Order> orders = TestData.unfinishedOrders();

        coffeeShop.answerForUnfinishedOrders(orders);

        coffeeShop.processUnfinishedOrders();
        coffeeShop.verifyProcessUnfinishedOrders(orders);
    }

}
