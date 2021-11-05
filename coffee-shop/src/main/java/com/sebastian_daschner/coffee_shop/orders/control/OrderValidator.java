package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.boundary.CoffeeShop;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import com.sebastian_daschner.coffee_shop.orders.entity.ValidOrder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@ApplicationScoped
public class OrderValidator implements ConstraintValidator<ValidOrder, JsonObject> {

    // https://github.com/eclipse-ee4j/beanvalidation-spec/issues/266
    CoffeeShop coffeeShop;

    public void initialize(ValidOrder constraint) {
        Instance<CoffeeShop> instance = CDI.current().select(CoffeeShop.class);
        if (!instance.isResolvable())
            throw new RuntimeException("Could not retrieve coffee-shop instance in OrderValidator.class");
        coffeeShop = instance.get();
    }

    public boolean isValid(JsonObject json, ConstraintValidatorContext context) {

        String type = json.getString("type", null);
        String origin = json.getString("origin", null);

        if (type == null || origin == null)
            return false;

        CoffeeType coffeeType = coffeeShop.getCoffeeTypes().stream()
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findAny().orElse(null);

        Origin coffeeOrigin = coffeeShop.getOrigin(origin);

        if (coffeeOrigin == null || coffeeType == null)
            return false;

        return coffeeOrigin.getCoffeeTypes().contains(coffeeType);
    }

}
