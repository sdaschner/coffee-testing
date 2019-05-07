package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;
import org.assertj.core.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderProcessorTestDouble extends OrderProcessor {

    private final ArgumentCaptor<Order> orderCaptor;

    public OrderProcessorTestDouble() {
        entityManager = mock(EntityManager.class);
        barista = mock(Barista.class);
        orderCaptor = ArgumentCaptor.forClass(Order.class);

        when(barista.retrieveOrderStatus(orderCaptor.capture())).thenReturn(OrderStatus.PREPARING);
    }

    public void verifyProcessOrders(List<Order> orders) {
        verify(barista, times(orders.size())).retrieveOrderStatus(any());
        assertThat(orderCaptor.getAllValues()).containsExactlyElementsOf(orders);
    }

}
