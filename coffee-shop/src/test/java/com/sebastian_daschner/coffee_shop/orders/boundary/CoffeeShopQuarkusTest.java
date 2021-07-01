package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.TestData;
import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.mockito.Mockito.*;

@QuarkusTest
@Disabled("too slow")
class CoffeeShopQuarkusTest {

    @Inject
    CoffeeShop coffeeShop;

    @InjectMock
    OrderRepository orderRepository;

    @InjectSpy
    OrderProcessor orderProcessor;

    @Test
    void testProcessUnfinishedOrders() {
        List<Order> orders = TestData.unfinishedOrders();
        when(orderRepository.listUnfinishedOrders()).thenReturn(orders);
        orders.forEach(o -> when(orderRepository.findById(o.getId())).thenReturn(o));

        coffeeShop.processUnfinishedOrders();

        verify(orderProcessor, times(orders.size())).processOrder(any());
    }

}