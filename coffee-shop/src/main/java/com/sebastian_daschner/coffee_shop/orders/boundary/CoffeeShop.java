package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class CoffeeShop {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    OrderProcessor orderProcessor;

    public Set<CoffeeType> getCoffeeTypes() {
        return EnumSet.of(CoffeeType.ESPRESSO, CoffeeType.LATTE, CoffeeType.POUR_OVER);
    }

    public Set<Origin> getOrigins(final CoffeeType type) {
        return entityManager.createNamedQuery(Origin.FIND_ALL, Origin.class)
                .getResultList().stream()
                .filter(t -> t.getCoffeeTypes().contains(type))
                .collect(Collectors.toSet());
    }

    public Origin getOrigin(String name) {
        return entityManager.find(Origin.class, name);
    }

    public void createOrder(Order order) {
        entityManager.merge(order);
        entityManager.flush();
    }

    public Order getOrder(UUID id) {
        return entityManager.find(Order.class, id.toString());
    }

    public List<Order> getOrders() {
        return entityManager.createNamedQuery(Order.FIND_ALL, Order.class)
                .getResultList();
    }

    @Scheduled(every = "2s")
    public void processUnfinishedOrders() {
        entityManager.createNamedQuery(Order.FIND_UNFINISHED, Order.class)
                .getResultList()
                .forEach(orderProcessor::processOrder);
    }

    public void updateOrder(UUID id, Order order) {
        Order managedOrder = entityManager.find(Order.class, id.toString());
        managedOrder.setType(order.getType());
        managedOrder.setOrigin(order.getOrigin());
    }

}
