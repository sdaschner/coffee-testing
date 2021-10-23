package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.ValidOrder;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@ApplicationScoped
public class OrderValidator implements ConstraintValidator<ValidOrder, JsonObject> {

//    @Inject
//    CoffeeShop coffeeShop;

    public void initialize(ValidOrder constraint) {
        // nothing to do
    }

    public boolean isValid(JsonObject json, ConstraintValidatorContext context) {

        final String type = json.getString("type", null);
        final String origin = json.getString("origin", null);

        if (type == null || origin == null)
            return false;

        CoffeeType coffeeType = CoffeeType.fromString(type);
//        final CoffeeType coffeeType = coffeeShop.getCoffeeTypes().stream()
//                .filter(t -> t.name().equalsIgnoreCase(type))
//                .findAny().orElse(null);

//        final Origin coffeeOrigin = coffeeShop.getOrigin(origin);

//        if (coffeeOrigin == null || coffeeType == null)
//            return false;

        if (origin.isBlank() || coffeeType == null)
            return false;

//        return coffeeOrigin.getCoffeeTypes().contains(coffeeType);
        return true;
    }

}
