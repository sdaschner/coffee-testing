package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("index.html")
@Produces(MediaType.TEXT_HTML)
public class IndexController {

    @Inject
    CoffeeShop coffeeShop;

    @Location("index.html")
    Template index;

    @GET
    public TemplateInstance index() {
        List<Order> orders = coffeeShop.getOrders();
        return index.data("orders", orders);
    }

}
