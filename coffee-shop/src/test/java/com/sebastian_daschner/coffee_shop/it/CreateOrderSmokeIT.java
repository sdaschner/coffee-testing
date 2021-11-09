package com.sebastian_daschner.coffee_shop.it;

import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateOrderSmokeIT {

    private CoffeeOrderSystem coffeeOrderSystem;

    @BeforeEach
    void setUp() {
        coffeeOrderSystem = new CoffeeOrderSystem();
    }

    @Test
    @AllureId("49")
    void testIsSystemUp() {
        assertThat(coffeeOrderSystem.isSystemUp()).isTrue();
    }

    @Test
    @AllureId("50")
    void testGetTypes() {
        assertThat(coffeeOrderSystem.getTypes()).containsExactlyInAnyOrder("Espresso", "Pour_over", "Latte");
    }

    @Test
    @AllureId("47")
    void testGetTypeOrigins() {
        assertThat(coffeeOrderSystem.getOrigins("Espresso")).containsExactlyInAnyOrder("Colombia", "Ethiopia");
    }

    @AfterEach
    void tearDown() {
        coffeeOrderSystem.close();
    }

}