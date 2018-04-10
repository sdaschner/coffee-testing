package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessorComponent;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CoffeeShopComponent extends CoffeeShop {

    public CoffeeShopComponent(OrderProcessorComponent orderProcessorComponent) {
        entityManager = mock(EntityManager.class);
        orderProcessor = orderProcessorComponent;
    }

    public void verifyCreateOrder(Order order) {
        verify(entityManager).merge(order);
    }

    public void verifyProcessUnfinishedOrders() {
        verify(entityManager).createNamedQuery(Order.FIND_UNFINISHED, Order.class);
    }

    public void answerForUnfinishedOrders(List<Order> orders) {
        @SuppressWarnings("unchecked")
        TypedQuery<Order> queryMock = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), eq(Order.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(orders);
    }
}
