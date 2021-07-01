package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.Barista;
import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.sebastian_daschner.coffee_shop.orders.ReflectionSupport.setReflectiveField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CoffeeShopNaiveTest {

    private CoffeeShop coffeeShop;
    private OrderRepository orderRepository;
    private Barista barista;
    private ArgumentCaptor<Order> orderCaptor;

    @BeforeEach
    void setUp() {
        coffeeShop = new CoffeeShop();
        OrderProcessor orderProcessor = new OrderProcessor();

        coffeeShop.orderProcessor = orderProcessor;
        orderRepository = mock(OrderRepository.class);
        coffeeShop.orderRepository = orderRepository;

        barista = mock(Barista.class);
        setReflectiveField(orderProcessor, "orderRepository", orderRepository);
        setReflectiveField(orderProcessor, "barista", barista);
        orderCaptor = ArgumentCaptor.forClass(Order.class);

        when(barista.retrieveOrderStatus(orderCaptor.capture())).thenReturn(OrderStatus.PREPARING);
    }

    @Test
    void testCreateOrder() {
        Order order = new Order();
        coffeeShop.createOrder(order);
        verify(orderRepository).persist(order);
    }

    @Test
    void testProcessUnfinishedOrders() {
        List<Order> orders = Arrays.asList(new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Colombia")),
                new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Ethiopia")));

        when(orderRepository.listUnfinishedOrders()).thenReturn(orders);
        orders.forEach(o -> when(orderRepository.findById(o.getId())).thenReturn(o));

        coffeeShop.processUnfinishedOrders();

        verify(orderRepository).listUnfinishedOrders();

        verify(barista, times(orders.size())).retrieveOrderStatus(any());
        assertThat(orderCaptor.getAllValues()).containsExactlyElementsOf(orders);
    }

}
