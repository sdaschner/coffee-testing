package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;
import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class Barista {

    WebTarget target;

    @Inject
    @ConfigProperty(name = "barista.url")
    String baristaUrl;

    @PostConstruct
    void initClient() {
        final Client client = ClientBuilder.newClient();
        target = client.target(baristaUrl);
    }

    public OrderStatus retrieveOrderStatus(Order order) {
        JsonObject requestJson = buildRequestJson(order);

        JsonObject responseJson = sendRequest(requestJson);

        return readStatus(responseJson);
    }

    private JsonObject buildRequestJson(Order order) {
        return Json.createObjectBuilder()
                .add("order", order.getId().toString())
                .add("type", order.getType().name().toUpperCase())
                .add("origin", order.getOrigin().getName().toUpperCase())
                .add("status", order.getStatus().name().toUpperCase())
                .build();
    }

    private JsonObject sendRequest(final JsonObject requestBody) {
        Response response = target.request().buildPost(Entity.json(requestBody)).invoke();

        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            throw new RuntimeException("Could not successfully process order, response from " + target.getUri() + " was " + response.getStatus());
        }

        return response.readEntity(JsonObject.class);
    }

    private OrderStatus readStatus(final JsonObject responseJson) {
        final OrderStatus status = OrderStatus.fromString(responseJson.getString("status", null));

        if (status == null)
            throw new RuntimeException("Could not read known status from response" + responseJson);

        return status;
    }

}
