package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderProcessor {

    @Inject
    OrderRepository orderRepository;

    @Inject
    Barista barista;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void processOrder(Order order) {
        Order managedOrder = orderRepository.findById(order.getId());
        OrderStatus status = barista.retrieveOrderStatus(managedOrder);
        managedOrder.setStatus(status);
    }

}
