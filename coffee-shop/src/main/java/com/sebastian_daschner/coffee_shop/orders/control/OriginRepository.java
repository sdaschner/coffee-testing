package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class OriginRepository implements PanacheRepositoryBase<Origin, String> {

    public Set<Origin> listForCoffeeType(CoffeeType type) {
        return streamAll()
                .filter(t -> t.getCoffeeTypes().contains(type))
                .collect(Collectors.toSet());
    }

}
