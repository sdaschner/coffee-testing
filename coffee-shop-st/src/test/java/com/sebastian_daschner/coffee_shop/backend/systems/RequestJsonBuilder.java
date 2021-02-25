package com.sebastian_daschner.coffee_shop.backend.systems;

import com.sebastian_daschner.coffee_shop.backend.entity.Order;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

class RequestJsonBuilder {

    JsonObject forOrder(Order order) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (order.getType() != null)
            builder.add("type", order.getType());
        else
            builder.addNull("type");
        if (order.getOrigin() != null)
            builder.add("origin", order.getOrigin());
        else
            builder.addNull("origin");

        return builder.build();
    }

}
