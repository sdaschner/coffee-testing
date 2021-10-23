package com.sebastian_daschner.coffee_shop;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@Liveness
@Readiness
public class Health implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("coffee-shop");
    }

}
