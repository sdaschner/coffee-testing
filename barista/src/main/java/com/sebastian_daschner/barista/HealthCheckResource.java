package com.sebastian_daschner.barista;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("health")
public class HealthCheckResource {

    @GET
    public String healthCheck() {
        return "OK";
    }

}
