package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class OriginRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Set<Origin> listForCoffeeType(CoffeeType type) {
        return listAll().stream()
                .filter(t -> t.getCoffeeTypes().contains(type))
                .collect(Collectors.toSet());
    }

    public Origin findById(String id) {
        return entityManager.find(Origin.class, id);
    }

    public List<Origin> listAll() {
        return entityManager.createQuery("select o from Origin o", Origin.class).getResultList();
    }

}
