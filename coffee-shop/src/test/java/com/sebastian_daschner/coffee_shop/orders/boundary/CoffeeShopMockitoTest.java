package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.TestData;
import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoffeeShopMockitoTest {

    @InjectMocks
    CoffeeShop testObject;

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    private EntityManager entityManager;
    private OrderProcessor orderProcessor;

    CoffeeShopMockitoTest(@Mock EntityManager entityManager, @Mock OrderProcessor orderProcessor) {
        this.entityManager = entityManager;
        this.orderProcessor = orderProcessor;
    }

    @Test
    void testProcessUnfinishedOrders(@Mock TypedQuery<Order> mockQuery) {
        when(entityManager.createNamedQuery(Order.FIND_UNFINISHED, Order.class)).thenReturn(mockQuery);
        List<Order> desiredOrders = TestData.unfinishedOrders();
        when(mockQuery.getResultList()).thenReturn(desiredOrders);

        testObject.processUnfinishedOrders();

        verify(entityManager).createNamedQuery(Order.FIND_UNFINISHED, Order.class);
        verify(orderProcessor, times(desiredOrders.size())).processOrder(orderCaptor.capture());

        assertThat(orderCaptor.getAllValues()).containsExactlyElementsOf(desiredOrders);
    }

}