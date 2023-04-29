package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.EntityBuilder;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import io.quarkus.arc.Unremovable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Unremovable // https://github.com/quarkusio/quarkus/issues/5314
public class OriginsResource {

    @Inject
    CoffeeShop coffeeShop;

    @PathParam("type")
    CoffeeType type;

    @Context
    UriInfo uriInfo;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    public JsonArray getOrigins() {
        return coffeeShop.getOrigins(type).stream()
                .map(o -> entityBuilder.buildOrigin(uriInfo, o, type))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add).build();
    }

}
