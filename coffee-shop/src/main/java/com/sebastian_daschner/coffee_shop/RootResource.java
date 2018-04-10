package com.sebastian_daschner.coffee_shop;

import com.sebastian_daschner.coffee_shop.orders.control.EntityBuilder;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
