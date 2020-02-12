package com.sebastian_daschner.coffee_shop;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestConfigTest {

    @Test
    void testGetProperty() {
        String host = TestConfig.getProperty("coffee-shop.test.host");
        assertThat(host).isEqualTo("localhost");
    }

    @Test
    void testGetPropertyProfile() {
        TestConfig.setProfile("staging");

        String host = TestConfig.getProperty("coffee-shop.test.host");
        assertThat(host).isEqualTo("1.2.3.4");
    }

    @Test
    void testGetPropertyExplicitProfile() {
        String host = TestConfig.getProperty("coffee-shop.test.host", null, "staging");
        assertThat(host).isEqualTo("1.2.3.4");
    }

    @Test
    void testGetPropertyDefaultValue() {
        String host = TestConfig.getProperty("coffee-shop.test.foo", "hello");
        assertThat(host).isEqualTo("hello");
    }

    @Test
    void testGetPropertySystemProperty() {
        System.setProperty("foobar", "foo");
        String host = TestConfig.getProperty("foobar");
        assertThat(host).isEqualTo("foo");
    }

}
