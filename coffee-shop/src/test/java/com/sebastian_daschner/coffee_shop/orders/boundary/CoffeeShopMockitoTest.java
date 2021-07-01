package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.TestData;
import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoffeeShopMockitoTest {

    @InjectMocks
    CoffeeShop testObject;

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    private final OrderRepository orderRepository;
    private final OrderProcessor orderProcessor;

    CoffeeShopMockitoTest(@Mock OrderRepository orderRepository, @Mock OrderProcessor orderProcessor) {
        this.orderRepository = orderRepository;
        this.orderProcessor = orderProcessor;
    }

    @Test
    void testProcessUnfinishedOrders() {
        List<Order> orders = TestData.unfinishedOrders();
        when(orderRepository.listUnfinishedOrders()).thenReturn(orders);

        testObject.processUnfinishedOrders();

        verify(orderRepository).listUnfinishedOrders();

        verify(orderProcessor, times(orders.size())).processOrder(orderCaptor.capture());
        assertThat(orderCaptor.getAllValues()).containsExactlyElementsOf(orders);
    }

}