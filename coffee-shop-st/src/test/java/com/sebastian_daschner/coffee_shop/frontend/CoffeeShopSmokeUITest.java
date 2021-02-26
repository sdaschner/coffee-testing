package com.sebastian_daschner.coffee_shop.frontend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoffeeShopSmokeUITest {

    private final CoffeeShopUI coffeeShop = new CoffeeShopUI();

    @BeforeEach
    void setUp() {
        coffeeShop.init();
    }

    @Test
    void index_view_page_header_empty_table() {
        IndexView index = coffeeShop.index();
        assertThat(index.getPageHeader()).isEqualTo("All coffee orders");
        assertThat(index.getListedOrders()).isEmpty();
    }

}
