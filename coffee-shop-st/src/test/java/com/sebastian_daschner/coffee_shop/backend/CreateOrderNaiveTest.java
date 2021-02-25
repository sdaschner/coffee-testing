package com.sebastian_daschner.coffee_shop.backend;

import com.sebastian_daschner.coffee_shop.backend.entity.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CreateOrderNaiveTest {

    private Client client;
    private WebTarget ordersTarget;

    @BeforeEach
    void setUp() {
        client = ClientBuilder.newClient();
        ordersTarget = client.target(buildUri());
    }

    private URI buildUri() {
        String host = System.getProperty("coffee-shop.test.host", "localhost");
        String port = System.getProperty("coffee-shop.test.port", "8001");
        return UriBuilder.fromUri("http://{host}:{port}/orders")
                .build(host, port);
    }

    @Test
    void createVerifyOrder() {
        Order order = new Order("Espresso", "Colombia");
        JsonObject requestBody = createJson(order);

        Response response = ordersTarget.request().post(Entity.json(requestBody));

        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)
            throw new AssertionError("Status was not successful: " + response.getStatus());

        URI orderUri = response.getLocation();

        Order loadedOrder = client.target(orderUri)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Order.class);

        Assertions.assertThat(loadedOrder).isEqualToComparingOnlyGivenFields(order, "type", "origin");

        List<URI> orders = ordersTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonArray.class).getValuesAs(JsonObject.class).stream()
                .map(o -> o.getString("_self"))
                .map(URI::create)
                .collect(Collectors.toList());

        assertThat(orders).contains(orderUri);
    }

    JsonObject createJson(Order order) {
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
