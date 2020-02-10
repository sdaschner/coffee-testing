package com.sebastian_daschner.coffee_shop.microshed;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class CoffeeShopApplication extends MicroShedApplication {

    public CoffeeShopApplication() {
        super(UriBuilder.fromUri("http://{host}:{port}/")
                .build(System.getProperty("coffee-shop.test.host", "localhost"),
                        System.getProperty("coffee-shop.test.port", "8001")));
    }

    public List<String> getTypes() {
        return rootTarget.path("types")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonArray.class).getValuesAs(JsonObject.class).stream()
                .map(o -> o.getString("type"))
                .collect(Collectors.toList());
    }

    public List<String> getOrigins(String type) {
        URI typeOriginsUri = retrieveTypeOriginsLink(type);

        return retrieveOriginsForType(typeOriginsUri);
    }

    private URI retrieveTypeOriginsLink(String type) {
        return rootTarget.path("types")
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

}
