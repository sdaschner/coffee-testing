package com.sebastian_daschner.coffee_shop.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@Disabled("too slow")
class CreateOrderQuarkusSmokeIT {

    private CoffeeOrderSystem coffeeOrderSystem;

    @BeforeEach
    void setUp() {
        System.setProperty("coffee-shop.test.port", "8081");
        coffeeOrderSystem = new CoffeeOrderSystem();
    }

    @Test
    void testIsSystemUp() {
        assertThat(coffeeOrderSystem.isSystemUp()).isTrue();
    }

    @Test
    void testGetTypes() {
        assertThat(coffeeOrderSystem.getTypes()).containsExactlyInAnyOrder("Espresso", "Pour_over", "Latte");
    }

    @Test
    void testGetTypeOrigins() {
        assertThat(coffeeOrderSystem.getOrigins("Espresso")).containsExactlyInAnyOrder("Colombia", "Ethiopia");
    }

    @AfterEach
    void tearDown() {
        coffeeOrderSystem.close();
    }

}