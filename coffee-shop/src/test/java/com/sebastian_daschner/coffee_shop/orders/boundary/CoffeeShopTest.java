package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CoffeeShopTest {

    private CoffeeShop testObject;

    @BeforeEach
    void setUp() {
        testObject = new CoffeeShop();
        testObject.entityManager = mock(EntityManager.class);
        testObject.orderProcessor = mock(OrderProcessor.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    void test() {
        List<Order> desiredOrders = TestUtils.unfinishedOrders();

        TypedQuery mockQuery = mock(TypedQuery.class);
        when(testObject.entityManager.createNamedQuery(Order.FIND_UNFINISHED, Order.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(desiredOrders);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        testObject.processUnfinishedOrders();

        verify(testObject.entityManager).createNamedQuery(Order.FIND_UNFINISHED, Order.class);
        verify(testObject.orderProcessor, times(desiredOrders.size())).processOrder(orderCaptor.capture());

        assertThat(orderCaptor.getAllValues()).containsExactlyElementsOf(desiredOrders);
    }

}