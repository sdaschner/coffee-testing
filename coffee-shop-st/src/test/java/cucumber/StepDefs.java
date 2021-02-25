package cucumber;

import com.sebastian_daschner.coffee_shop.backend.entity.Order;
import com.sebastian_daschner.coffee_shop.backend.systems.CoffeeOrderSystem;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefs {

    private CoffeeOrderSystem coffeeOrderSystem;
    private Order order;

    @Before
    public void setUp() {
        coffeeOrderSystem = new CoffeeOrderSystem();
    }

    @When("^I create an order with ([^ ]*) from ([^ ]*)$")
    public void i_create_an_order(String type, String origin) {
        order = new Order(type, origin);
    }

    @Then("^The order should be accepted$")
    public void accepted_order() {
        URI id = coffeeOrderSystem.createOrder(order);
        Order order = coffeeOrderSystem.getOrder(id);
        assertOrderMatches(this.order, order);
    }

    @Then("^The order should be rejected$")
    public void rejected_order() {
        coffeeOrderSystem.createInvalidOrder(order);
    }

    private void assertOrderMatches(Order actual, Order expected) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "type", "origin");
    }

    @After
    public void close() {
        coffeeOrderSystem.close();
    }

}
