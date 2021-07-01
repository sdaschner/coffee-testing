package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessorTestDouble;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;
import com.sebastian_daschner.coffee_shop.orders.control.OriginRepository;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;

import java.util.List;

import static org.mockito.Mockito.*;

public class CoffeeShopTestDouble extends CoffeeShop {

    public CoffeeShopTestDouble(OrderProcessorTestDouble orderProcessorTestDouble) {
        orderRepository = mock(OrderRepository.class);
        originRepository = mock(OriginRepository.class);
        orderProcessor = orderProcessorTestDouble;
    }

    public void verifyCreateOrder(Order order) {
        verify(orderRepository).persist(order);
    }

    public void verifyProcessUnfinishedOrders(List<Order> orders) {
        verify(orderRepository).listUnfinishedOrders();
        ((OrderProcessorTestDouble) orderProcessor).verifyProcessOrders(orders);
    }

    public void answerForUnfinishedOrders(List<Order> orders) {
        when(orderRepository.listUnfinishedOrders()).thenReturn(orders);
    }
}
