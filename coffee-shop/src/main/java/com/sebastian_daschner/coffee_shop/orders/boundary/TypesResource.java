package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.EntityBuilder;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("types")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TypesResource {

    @Inject
    CoffeeShop coffeeShop;

    @Inject
    EntityBuilder entityBuilder;

    @Context
    ResourceContext resourceContext;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonArray getCoffeeTypes() {
        return coffeeShop.getCoffeeTypes().stream()
                .map(t -> entityBuilder.buildType(t, uriInfo))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add).build();
    }

    @Path("{type}/origins")
    public OriginsResource originsResource() {
        return resourceContext.getResource(OriginsResource.class);
    }

}
