package com.sebastian_daschner.coffee_shop.orders.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.UUID;

class OrderIT {

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        entityManager = Persistence.createEntityManagerFactory("it").createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @Test
    void testPersist() {
        transaction.begin();
        Origin colombia = new Origin("Colombia");
        Order order = new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, colombia);

        entityManager.persist(colombia);
        entityManager.persist(order);
        transaction.commit();
    }

}