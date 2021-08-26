package com.sebastian_daschner.coffee_shop;

import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

public class ConfigExample {

    @Test
    void test() {
        String value = ConfigProvider.getConfig().getValue("config.value", String.class);
        System.out.println("property = " + value);
    }

}
