package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.boundary.OrdersResource;
import com.sebastian_daschner.coffee_shop.orders.boundary.TypesResource;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;

import javax.json.*;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.UUID;

public class EntityBuilder {

    public JsonArray buildOrders(List<Order> orders, UriInfo uriInfo) {
        return orders.stream()
                .map(o -> buildOrderTeaser(o, uriInfo))
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();
    }

    private JsonObject buildOrderTeaser(Order order, UriInfo uriInfo) {
        return Json.createObjectBuilder()
                .add("_self", uriInfo.getBaseUriBuilder()
                        .path(OrdersResource.class)
                        .path(OrdersResource.class, "getOrder")
                        .build(order.getId())
                        .toString())
                .add("origin", order.getOrigin().getName())
                .add("status", capitalize(order.getStatus().name()))
                .build();
    }

    public JsonObject buildOrder(Order order) {
        return Json.createObjectBuilder()
                .add("type", capitalize(order.getType().name()))
                .add("origin", order.getOrigin().getName())
                .add("status", capitalize(order.getStatus().name()))
                .build();
    }

    public Order buildOrder(JsonObject json) {
        final CoffeeType type = CoffeeType.fromString(json.getString("type"));
        final Origin origin = new Origin(json.getString("origin"));

        return new Order(UUID.randomUUID(), type, origin);
    }

    public JsonObject buildIndex(UriInfo uriInfo) {
        final URI typesUri = uriInfo.getBaseUriBuilder().path(TypesResource.class).build();
        final URI ordersUri = uriInfo.getBaseUriBuilder().path(OrdersResource.class).build();
        return Json.createObjectBuilder()
                .add("_links", Json.createObjectBuilder()
                        .add("types", typesUri.toString()))
                .add("_actions", Json.createObjectBuilder()
                        .add("order-coffee", Json.createObjectBuilder()
                                .add("method", "POST")
                                .add("href", ordersUri.toString())
                                .add("fields", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                .add("name", "type")
                                                .add("type", "text"))
                                        .add(Json.createObjectBuilder()
                                                .add("name", "origin")
                                                .add("type", "text"))
                                )))
                .build();
    }

    public JsonObject buildOrigin(UriInfo uriInfo, Origin origin, CoffeeType type) {
        final URI ordersUri = uriInfo.getBaseUriBuilder().path(OrdersResource.class).build();
        return Json.createObjectBuilder()
                .add("origin", origin.getName())
                .add("_actions", Json.createObjectBuilder()
                        .add("order-coffee", Json.createObjectBuilder()
                                .add("method", "POST")
                                .add("href", ordersUri.toString())
                                .add("fields", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                .add("name", "type")
                                                .add("value", capitalize(type.name())))
                                        .add(Json.createObjectBuilder()
                                                .add("name", "origin")
                                                .add("type", origin.getName()))
                                )))
                .build();
    }

    public JsonObjectBuilder buildType(CoffeeType type, UriInfo uriInfo) {
        return Json.createObjectBuilder()
                .add("type", capitalize(type.name()))
                .add("_links", Json.createObjectBuilder()
                        .add("origins", uriInfo.getBaseUriBuilder()
                                .path(TypesResource.class)
                                .path(TypesResource.class, "originsResource")
                                .build(type).toString().toLowerCase()));
    }

    private String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }

}
