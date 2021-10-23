package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sebastian_daschner.coffee_shop.orders.ReflectionSupport.setReflectiveField;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CoffeeShopNaiveTest {

    private CoffeeShop coffeeShop;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        coffeeShop = new CoffeeShop();
        OrderProcessor orderProcessor = new OrderProcessor();

        coffeeShop.orderProcessor = orderProcessor;
        orderRepository = mock(OrderRepository.class);
        coffeeShop.orderRepository = orderRepository;
        setReflectiveField(orderProcessor, "orderRepository", orderRepository);
    }

    @Test
    void testCreateOrder() {
        Order order = new Order();
        coffeeShop.createOrder(order);
        verify(orderRepository).save(order);
    }

}
