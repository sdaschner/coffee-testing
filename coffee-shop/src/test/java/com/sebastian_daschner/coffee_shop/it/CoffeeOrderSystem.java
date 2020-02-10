package com.sebastian_daschner.coffee_shop.it;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

class CoffeeOrderSystem {

    private final Client client;
    private final WebTarget baseTarget;

    CoffeeOrderSystem() {
        client = ClientBuilder.newClient();
        baseTarget = client.target(buildBaseUri());
    }

    private URI buildBaseUri() {
        String host = System.getProperty("coffee-shop.test.host", "localhost");
        String port = System.getProperty("coffee-shop.test.port", "8001");
        return UriBuilder.fromUri("http://{host}:{port}/").build(host, port);
    }

    boolean isSystemUp() {
        JsonObject healthJson = retrieveHealthStatus();

        String status = healthJson.getString("status");
        if (!"UP".equalsIgnoreCase(status))
            return false;

        return "UP".equalsIgnoreCase(extractStatus(healthJson, "coffee-shop"));
    }

    private JsonObject retrieveHealthStatus() {
        return baseTarget.path("health")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonObject.class);
    }

    private String extractStatus(JsonObject healthJson, String name) {
        return healthJson.getJsonArray("checks")
                .getValuesAs(JsonObject.class)
                .stream()
                .filter(o -> o.getString("name").equalsIgnoreCase(name))
                .map(o -> o.getString("status"))
                .findAny().orElse(null);
    }

    List<String> getTypes() {
        return baseTarget.path("types")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonArray.class).getValuesAs(JsonObject.class).stream()
                .map(o -> o.getString("type"))
                .collect(Collectors.toList());
    }

    List<String> getOrigins(String type) {
        URI typeOriginsUri = retrieveTypeOriginsLink(type);

        return retrieveOriginsForType(typeOriginsUri);
    }

    private URI retrieveTypeOriginsLink(String type) {
        return baseTarget.path("types")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonArray.class).getValuesAs(JsonObject.class).stream()
                .filter(o -> o.getString("type").equalsIgnoreCase(type))
                .map(o -> o.getJsonObject("_links").getString("origins"))
                .map(URI::create)
                .findAny().orElseThrow(() -> new IllegalStateException("Could not get link to origins for " + type));
    }

    private List<String> retrieveOriginsForType(URI typeOriginsUri) {
        return client.target(typeOriginsUri)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonArray.class).getValuesAs(JsonObject.class).stream()
                .map(o -> o.getString("origin"))
                .collect(Collectors.toList());
    }

    void close() {
        client.close();
    }
}
