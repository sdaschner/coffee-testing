package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.TestData;
import com.sebastian_daschner.coffee_shop.orders.boundary.CoffeeShop;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepDefs {

    private OrderValidator testObject;
    private ConstraintValidatorContext context;
    private JsonObject jsonObject;

    @Before
    public void setUp() {
        testObject = new OrderValidator();
        testObject.coffeeShop = mock(CoffeeShop.class);
        context = mock(ConstraintValidatorContext.class);

        List<Origin> origins = TestData.validOrigins();
        Set<CoffeeType> coffeeTypes = TestData.validCoffeeTypes();

        when(testObject.coffeeShop.getCoffeeTypes()).thenReturn(coffeeTypes);
        when(testObject.coffeeShop.getOrigin(anyString())).then(invocation -> origins.stream()
                .filter(o -> o.getName().equals(invocation.<String>getArgument(0)))
                .findAny()
                .orElse(null));
    }

    @When("^I create an order with ([^ ]*) from ([^ ]*)$")
    public void i_create_an_order(String type, String origin) {
        jsonObject = Json.createObjectBuilder()
                .add("type", type)
                .add("origin", origin)
                .build();
    }

    @Then("^The order should be accepted$")
    public void accepted_order() {
        assertThat(testObject.isValid(jsonObject, context)).isTrue();
    }

    @Then("^The order should be rejected$")
    public void rejected_order() {
        assertThat(testObject.isValid(jsonObject, context)).isFalse();
    }

}
