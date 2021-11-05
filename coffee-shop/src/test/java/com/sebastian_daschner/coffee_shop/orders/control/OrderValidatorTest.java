package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.boundary.CoffeeShop;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.ConstraintValidatorContext;
import java.io.StringReader;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderValidatorTest {

    private OrderValidator testObject;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        testObject = new OrderValidator();
        testObject.coffeeShop = mock(CoffeeShop.class);
        context = mock(ConstraintValidatorContext.class);

        Set<CoffeeType> coffeeTypes = EnumSet.allOf(CoffeeType.class);
        Origin colombia = new Origin("Colombia");
        colombia.getCoffeeTypes().addAll(coffeeTypes);

        when(testObject.coffeeShop.getCoffeeTypes()).thenReturn(coffeeTypes);
        when(testObject.coffeeShop.getOrigin("Colombia")).thenReturn(colombia);
    }

    @ParameterizedTest
    @MethodSource("validData")
    void testIsValid(String json) {
        JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();

        assertThat(testObject.isValid(jsonObject, context)).isTrue();
    }

    private static Collection<String> validData() {
        return List.of(
                "{\"type\":\"ESPRESSO\",\"origin\":\"Colombia\"}",
                "{\"type\":\"Espresso\",\"origin\":\"Colombia\"}",
                "{\"type\":\"LATTE\",\"origin\":\"Colombia\"}",
                "{\"type\":\"Latte\",\"origin\":\"Colombia\"}",
                "{\"type\":\"POUR_OVER\",\"origin\":\"Colombia\"}",
                "{\"type\":\"Pour_over\",\"origin\":\"Colombia\"}");
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    void testIsInvalid(String json) {
        JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();

        assertThat(testObject.isValid(jsonObject, context)).isFalse();
    }

    private static Collection<String> invalidData() {
        return List.of(
                "{\"type\":\"SIPHON\",\"origin\":\"Colombia\"}",
                "{\"type\":null,\"origin\":\"Colombia\"}",
                "{\"origin\":\"Colombia\"}",
                "{\"type\":\"ESPRESSO\",\"origin\":\"Ethiopia\"}");
    }

}