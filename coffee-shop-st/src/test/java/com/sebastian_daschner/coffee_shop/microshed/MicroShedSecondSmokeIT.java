package com.sebastian_daschner.coffee_shop.microshed;

import com.sebastian_daschner.coffee_shop.microshed.health.Health;
import com.sebastian_daschner.coffee_shop.microshed.metrics.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MicroShedSecondSmokeIT {

    private CoffeeShopApplication coffeeShop;

    @BeforeEach
    void setUp() {
        coffeeShop = new CoffeeShopApplication();
    }

    @Test
    void testIsSystemUp() {
        Health health = coffeeShop.health();
        assertThat(health.status).isEqualTo(Health.Status.UP);
        assertThat(health.getCheck("coffee-shop").status).isEqualTo(Health.Status.UP);
    }

    @Test
    void testGetMetrics() {
        Metrics metrics = coffeeShop.metrics();

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

    @Test
    void testGetTypes() {
        assertThat(coffeeShop.getTypes()).containsExactlyInAnyOrder("Espresso", "Pour_over", "Latte");
    }

    @Test
    void testGetTypeOrigins() {
        assertThat(coffeeShop.getOrigins("Espresso")).containsExactlyInAnyOrder("Colombia", "Ethiopia");
    }

}
