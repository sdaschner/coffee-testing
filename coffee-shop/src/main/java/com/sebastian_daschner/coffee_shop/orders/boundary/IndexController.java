package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("index.html")
@ApplicationScoped
public class IndexController {

    @Inject
    CoffeeShop coffeeShop;

    @Inject
    Models models;

    @GET
    @Controller
    public String index() {
        List<Order> orders = coffeeShop.getOrders();
        models.put("orders", orders);
        return "index.jsp";
    }

}
