package com.sebastian_daschner.coffee_shop;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class CoffeeOrderSystem {

    private final Client client;
    private final WebTarget ordersTarget;

    public CoffeeOrderSystem() {
        client = ClientBuilder.newClient();
        ordersTarget = client.target("http://coffee-shop.test.kubernetes.local/coffee-shop/resources/orders");
    }

    public List<URI> getOrders() {
        return ordersTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonArray.class).getValuesAs(JsonObject.class).stream()
                .map(o -> o.getString("_self"))
                .map(URI::create)
                .collect(Collectors.toList());
    }

    public URI createOrder(Order order) {
        Response response = ordersTarget.request()
                .post(Entity.json(order));
        verifyStatus(response);
        return response.getLocation();
    }

    private void verifyStatus(Response response) {
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)
            throw new AssertionError("Status was not successful: " + response.getStatus());
    }

    public Order getOrder(URI orderUri) {
        return client.target(orderUri)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Order.class);
    }

}
