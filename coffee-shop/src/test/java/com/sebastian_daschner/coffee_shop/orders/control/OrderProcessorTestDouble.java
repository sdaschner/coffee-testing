package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderProcessorTestDouble extends OrderProcessor {

    private final ArgumentCaptor<Order> orderCaptor;

    public OrderProcessorTestDouble() {
        orderRepository = mock(OrderRepository.class);
        barista = mock(Barista.class);
        orderCaptor = ArgumentCaptor.forClass(Order.class);

        when(barista.retrieveOrderStatus(orderCaptor.capture())).thenReturn(OrderStatus.PREPARING);
    }

    public void verifyProcessOrders(List<Order> orders) {
        verify(barista, times(orders.size())).retrieveOrderStatus(any());
        assertThat(orderCaptor.getAllValues()).containsExactlyElementsOf(orders);
    }

    @Override
    public void processOrder(Order order) {
        when(orderRepository.findById(order.getId())).thenReturn(order);
        super.processOrder(order);
    }
}
