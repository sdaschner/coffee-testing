package com.sebastian_daschner.coffee_shop;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class Health implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("coffee-shop");
    }

}
