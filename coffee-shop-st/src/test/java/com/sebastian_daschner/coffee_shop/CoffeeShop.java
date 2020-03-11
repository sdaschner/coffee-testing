package com.sebastian_daschner.coffee_shop;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.lifecycle.Startables;

import java.io.IOException;
import java.util.Set;

public class CoffeeShop {

    static Network.NetworkImpl network = Network.builder().id("dkrnet").build();

    static GenericContainer<?> barista = new GenericContainer<>("rodolpheche/wiremock:2.6.0")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/__admin/"))
            .withNetwork(network)
            .withNetworkAliases("barista")
            .withReuse(true);

    static PostgreSQLContainer<?> coffeeShopDb = new PostgreSQLContainer<>()
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withReuse(true)
            .withNetwork(network)
            .withNetworkAliases("coffee-shop-db");

    static GenericContainer<?> coffeeShop = new GenericContainer<>("coffee-shop")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/health"))
            .withNetwork(network)
            .withNetworkAliases("coffee-shop")
            .withReuse(true)
            .dependsOn(barista, coffeeShopDb);

    public static void main(String[] args) throws IOException {
        var containers = Set.of(coffeeShop, barista, coffeeShopDb);
        Startables.deepStart(containers).join();

        System.out.println("Containers started. Press any key to stop");
        System.in.read();

        containers.forEach(Startable::stop);
    }

}
