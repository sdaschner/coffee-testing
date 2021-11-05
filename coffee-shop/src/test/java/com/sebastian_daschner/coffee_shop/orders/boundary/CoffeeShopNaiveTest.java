package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;
import com.sebastian_daschner.coffee_shop.orders.control.OriginRepository;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sebastian_daschner.coffee_shop.orders.ReflectionSupport.setReflectiveField;
import static org.mockito.Mockito.*;

class CoffeeShopNaiveTest {

    private CoffeeShop coffeeShop;
    private OrderRepository orderRepository;
    private OriginRepository originRepository;

    @BeforeEach
    void setUp() {
        coffeeShop = new CoffeeShop();
        OrderProcessor orderProcessor = new OrderProcessor();

        coffeeShop.orderProcessor = orderProcessor;
        orderRepository = mock(OrderRepository.class);
        originRepository = mock(OriginRepository.class);
        coffeeShop.orderRepository = orderRepository;
        coffeeShop.originRepository = originRepository;
        setReflectiveField(orderProcessor, "orderRepository", orderRepository);

        when(originRepository.findById(anyString())).then(a -> new Origin(a.getArgument(0, String.class)));
    }

    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setOrigin(new Origin("Colombia"));
        coffeeShop.createOrder(order);
        verify(orderRepository).save(order);
    }

}
