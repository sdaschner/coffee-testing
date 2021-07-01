package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Set;
import java.util.UUID;

@Path("order.html")
@Produces(MediaType.TEXT_HTML)
public class OrderCoffeeController {

    @Inject
    CoffeeShop coffeeShop;

    @Location("order.html")
    Template orderTemplate;

    @GET
    public TemplateInstance index() {
        Set<CoffeeType> types = coffeeShop.getCoffeeTypes();
        return orderTemplate.data("types", types);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response submit(@FormParam("type") @DefaultValue("") String type, @FormParam("origin") @DefaultValue("") String originName) {
        CoffeeType coffeeType = CoffeeType.fromString(type);
        Origin origin = new Origin(originName);
        Order order = new Order(UUID.randomUUID(), coffeeType, origin);

        if (!orderIsValid(order)) {
            Set<CoffeeType> types = coffeeShop.getCoffeeTypes();
            return Response.ok(orderTemplate
                    .data("failed", true)
                    .data("types", types))
                    .build();
        }

        coffeeShop.createOrder(order);

        return Response.seeOther(URI.create("/index.html")).build();
    }

    private boolean orderIsValid(Order order) {
        if (order.getType() == null || order.getOrigin() == null)
            return false;
        Origin origin = coffeeShop.getOrigin(order.getOrigin().getName());
        return origin != null;
    }

}
