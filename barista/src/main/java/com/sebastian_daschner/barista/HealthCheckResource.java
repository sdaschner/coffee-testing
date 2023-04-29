package com.sebastian_daschner.barista;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("health")
public class HealthCheckResource {

    @GET
    public String healthCheck() {
        return "OK";
    }

}
