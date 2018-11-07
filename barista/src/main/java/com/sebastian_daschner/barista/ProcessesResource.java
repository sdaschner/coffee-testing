package com.sebastian_daschner.barista;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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
