package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class OrderProcessor {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    Barista barista;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void processOrder(Order order) {
        OrderStatus status = barista.retrieveOrderStatus(order);
        order.setStatus(status);
        entityManager.merge(order);
    }

}
