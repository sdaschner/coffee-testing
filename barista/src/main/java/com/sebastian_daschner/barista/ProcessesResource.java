package com.sebastian_daschner.barista;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("processes")
public class ProcessesResource {

    @Inject
    OrderStatusProcessor processor;

    @POST
    public JsonObject process(JsonObject order) {
        final String status = order.getString("status", null);
        String newStatus = processor.process(status);
        return Json.createObjectBuilder().add("status", newStatus).build();
    }

}
