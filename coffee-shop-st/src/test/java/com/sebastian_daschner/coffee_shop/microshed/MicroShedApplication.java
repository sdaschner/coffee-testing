package com.sebastian_daschner.coffee_shop.microshed;

import com.sebastian_daschner.coffee_shop.microshed.health.Health;
import com.sebastian_daschner.coffee_shop.microshed.metrics.Metrics;
import com.sebastian_daschner.coffee_shop.microshed.openapi.OpenApi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Objects;

/**
 * Provides access to an application that implements MicroProfile Health and Metrics.
 * This class can be sub-classed and extended for type-safe, business-specific methods within the project's test scope.
 * <p>
 * <p>
 * Usage:
 * <pre>
 *     MicroShedApplication app = MicroShedApplication.withBaseUri(baseUri).build();
 *     Health health = app.health();
 * </pre>
 * <p>
 * Or a sub-classed version:
 * <pre>
 *     class MyApplication extends MicroShedApplication {
 *
 *         MyApplication() {
 *             super(URI.create("http://my-app:8080/"));
 *         }
 *
 *         // add business-related methods
 *
 *         public List<MyCustomer> getMyCustomers { ... }
 *     }
 *
 *     // in the test code, access health checks, metrics, or business-related methods
 *
 *     MyApplication app = new MyApplication();
 *     Health health = app.health();
 *     ...
 *     app.getMyCustomers();
 *     ...
 * </pre>
 */
public class MicroShedApplication {

    private static final String HEALTH_PATH = "/health";
    private static final String METRICS_PATH = "/metrics";
    private static final String OPEN_API_PATH = "/openapi";

    private final String healthPath;
    private final String metricsPath;
    private final String openApiPath;

    protected final Client client;
    protected final WebTarget rootTarget;

    protected MicroShedApplication(URI baseUri) {
        this(baseUri, HEALTH_PATH, METRICS_PATH, OPEN_API_PATH);
    }

    private MicroShedApplication(URI baseUri, String healthPath, String metricsPath, String openApiPath) {
        this.healthPath = healthPath;
        this.metricsPath = metricsPath;
        this.openApiPath = openApiPath;

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

    public OpenApi openApi() {
        return rootTarget.path(openApiPath)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(OpenApi.class);
    }

    public static Builder withBaseUri(URI baseUri) {
        Objects.requireNonNull(baseUri, "Base URI must not be null");
        Builder builder = new Builder();
        builder.baseUri = baseUri;
        return builder;
    }

    public static class Builder {

        private URI baseUri;
        private String healthPath = HEALTH_PATH;
        private String metricsPath = METRICS_PATH;
        private String openApiPath = OPEN_API_PATH;

        public Builder healthPath(String healthPath) {
            this.healthPath = healthPath;
            return this;
        }

        public Builder metricsPath(String metricsPath) {
            this.metricsPath = metricsPath;
            return this;
        }

        public Builder openApiPath(String openApiPath) {
            this.openApiPath = openApiPath;
            return this;
        }

        public MicroShedApplication build() {
            return new MicroShedApplication(baseUri, healthPath, metricsPath, openApiPath);
        }
    }
}
