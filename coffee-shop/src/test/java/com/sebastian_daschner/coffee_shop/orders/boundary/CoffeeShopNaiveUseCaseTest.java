package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.Barista;
import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CoffeeShopNaiveUseCaseTest {

    private CoffeeShop coffeeShop;
    private EntityManager entityManager;
    private OrderProcessor orderProcessor;
    private Barista barista;
    private ArgumentCaptor<Order> orderCaptor;

    @BeforeEach
    void setUp() {
        coffeeShop = new CoffeeShop();
        orderProcessor = new OrderProcessor();

        coffeeShop.orderProcessor = orderProcessor;
        entityManager = mock(EntityManager.class);
        coffeeShop.entityManager = entityManager;

        barista = mock(Barista.class);
        setReflectiveField(orderProcessor, "entityManager", entityManager);
        setReflectiveField(orderProcessor, "barista", barista);
        orderCaptor = ArgumentCaptor.forClass(Order.class);

        when(barista.retrieveOrderStatus(orderCaptor.capture())).thenReturn(OrderStatus.PREPARING);
    }

    @Test
    void testCreateOrder() {
        Order order = new Order();
        coffeeShop.createOrder(order);
        verify(entityManager).merge(order);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testProcessUnfinishedOrders() {
        List<Order> orders = Arrays.asList(new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Colombia")),
                new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Ethiopia")));

        // answer for unfinished orders
        TypedQuery<Order> queryMock = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), eq(Order.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(orders);

        coffeeShop.processUnfinishedOrders();

        verify(entityManager).createNamedQuery(Order.FIND_UNFINISHED, Order.class);

        verify(barista, times(orders.size())).retrieveOrderStatus(any());
        assertThat(orderCaptor.getAllValues()).containsExactlyElementsOf(orders);
    }

    private static void setReflectiveField(Object object, String fieldName, Object value) {
        try {
            Field f1 = object.getClass().getDeclaredField(fieldName);
            f1.setAccessible(true);
            f1.set(object, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
