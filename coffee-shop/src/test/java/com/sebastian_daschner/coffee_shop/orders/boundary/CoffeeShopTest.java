package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CoffeeShopTest {

    private CoffeeShop cut;

    @BeforeEach
    void setUp() {
        cut = new CoffeeShop();
    }

    @Test
    void testGetCoffeeTypes() {
        final Set<CoffeeType> coffeeTypes = cut.getCoffeeTypes();
        assertThat(coffeeTypes).hasSize(3);
        assertThat(coffeeTypes).containsOnly(CoffeeType.ESPRESSO, CoffeeType.LATTE, CoffeeType.POUR_OVER);
    }

}