package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Order order) {
        entityManager.merge(order);
    }

    public List<Order> listUnfinishedOrders() {
        return entityManager.createQuery("select o from Order o where o.status <> com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus.COLLECTED", Order.class)
                .getResultList();
    }

    public Order findById(UUID id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> listAll() {
        return entityManager.createQuery("select o from Order o", Order.class).getResultList();
    }

}
