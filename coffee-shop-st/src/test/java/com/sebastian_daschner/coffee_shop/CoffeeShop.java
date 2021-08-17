package com.sebastian_daschner.coffee_shop;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.lifecycle.Startables;

import java.io.IOException;
import java.nio.file.Paths;

import static com.sebastian_daschner.coffee_shop.DotEnvFile.writeDotEnvFile;

public class CoffeeShop {

    static Network network = Network.newNetwork();

    static GenericContainer<?> barista = new GenericContainer<>("rodolpheche/wiremock:2.6.0")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/__admin/"))
            .withNetwork(network)
            .withNetworkAliases("barista");

    static PostgreSQLContainer<?> coffeeShopDb = new PostgreSQLContainer<>("postgres:9.5")
            .withExposedPorts(5432)
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withNetwork(network)
            .withNetworkAliases("coffee-shop-db");

    static GenericContainer<?> coffeeShop = new GenericContainer<>(new ImageFromDockerfile("coffee-shop")
            .withDockerfile(Paths.get(System.getProperty("user.dir"), "../coffee-shop/Dockerfile")))
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/q/health"))
            .withNetwork(network)
            .withNetworkAliases("coffee-shop")
            .dependsOn(barista, coffeeShopDb);

    @Test
    void startContainers() throws IOException {

        Startables.deepStart(coffeeShop, barista, coffeeShopDb).join();

        int coffeeShopPort = coffeeShop.getMappedPort(8080);
        int baristaPort = barista.getMappedPort(8080);

        writeDotEnvFile(coffeeShopPort, baristaPort);

        System.out.println("The coffee-shop URLs is: http://localhost:" + coffeeShopPort + "/index.html");
        System.out.println("\nContainers started. Press any key or kill process to stop");
        System.in.read();
    }

}
