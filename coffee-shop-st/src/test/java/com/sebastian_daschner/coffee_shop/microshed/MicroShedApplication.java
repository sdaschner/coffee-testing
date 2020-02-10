package com.sebastian_daschner.coffee_shop.microshed;

import com.sebastian_daschner.coffee_shop.microshed.health.Health;
import com.sebastian_daschner.coffee_shop.microshed.metrics.Metrics;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Objects;

public class MicroShedApplication {

    private final String healthPath;
    private final String metricsPath;

    private final Client client;
    private final WebTarget rootTarget;

    private MicroShedApplication(URI baseUri, String healthPath, String metricsPath) {
        this.healthPath = healthPath;
        this.metricsPath = metricsPath;

        client = ClientBuilder.newBuilder().build();
        this.rootTarget = client.target(baseUri);
    }

    public Health health() {
        return rootTarget.path(healthPath)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Health.class);
    }

    public Metrics metrics() {
        String contents = rootTarget.path(metricsPath)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class);
        return new Metrics(contents);
    }

    public static Builder withBaseUri(URI baseUri) {
        Objects.requireNonNull(baseUri, "Base URI must not be null");
        Builder builder = new Builder();
        builder.baseUri = baseUri;
        return builder;
    }

    public static class Builder {

        private URI baseUri;
        private String healthPath = "/health";
        private String metricsPath = "/metrics";

        public Builder healthPath(String healthPath) {
            this.healthPath = healthPath;
            return this;
        }

        public Builder metricsPath(String metricsPath) {
            this.metricsPath = metricsPath;
            return this;
        }

        public MicroShedApplication build() {
            return new MicroShedApplication(baseUri, healthPath, metricsPath);
        }
    }
}
