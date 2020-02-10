package com.sebastian_daschner.coffee_shop.microshed;

import com.sebastian_daschner.coffee_shop.microshed.health.Health;
import com.sebastian_daschner.coffee_shop.microshed.metrics.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class MicroShedSmokeIT {

    private Client client;
    private WebTarget ordersTarget;
    private MicroShedApplication app;

    @BeforeEach
    void setUp() {
        String host = System.getProperty("coffee-shop.test.host", "localhost");
        String port = System.getProperty("coffee-shop.test.port", "8001");
        URI baseUri = UriBuilder.fromUri("http://{host}:{port}/").build(host, port);

        app = MicroShedApplication.withBaseUri(baseUri).build();
    }

    @Test
    void testIsSystemUp() {
        Health health = app.health();
        assertThat(health.status).isEqualTo(Health.Status.UP);
        assertThat(health.getCheck("coffee-shop").status).isEqualTo(Health.Status.UP);
    }

    @Test
    void testGetMetrics() {
        Metrics metrics = app.metrics();

        System.out.println("metrics = " + metrics.metrics.size());

        assertThat(metrics.metrics).containsKeys("vendor_memory_freeSwapSize_bytes");
        double freeSwapSizeMemory = Double.parseDouble(metrics.metrics.get("vendor_memory_freeSwapSize_bytes"));
        assertThat(freeSwapSizeMemory).isGreaterThan(1000000000D);
        System.out.println("freeSwapSizeMemory = " + freeSwapSizeMemory);

        assertThat(metrics.metrics).containsKeys("base_cpu_processCpuLoad_percent");
        double processCpuLoad = Double.parseDouble(metrics.metrics.get("base_cpu_processCpuLoad_percent"));
        assertThat(freeSwapSizeMemory).isGreaterThan(0D);
        System.out.println("processCpuLoad = " + processCpuLoad);
    }

}
