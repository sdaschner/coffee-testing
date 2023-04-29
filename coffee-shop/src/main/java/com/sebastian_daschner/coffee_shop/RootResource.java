package com.sebastian_daschner.coffee_shop;

import com.sebastian_daschner.coffee_shop.orders.control.EntityBuilder;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

@Path("/")
public class RootResource {

    @Context
    UriInfo uriInfo;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    public JsonObject getIndex() {
        return entityBuilder.buildIndex(this.uriInfo);
    }

}
