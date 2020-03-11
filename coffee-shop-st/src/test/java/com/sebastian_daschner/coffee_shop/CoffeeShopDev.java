package com.sebastian_daschner.coffee_shop;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.lifecycle.Startables;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

public class CoffeeShopDev {

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

    static GenericContainer<?> coffeeShop = new GenericContainer<>(
            new ImageFromDockerfile()
                    .withFileFromPath("Dockerfile", Paths.get("coffee-shop/Dockerfile.dev"))
                    .withFileFromPath("pom.xml", Paths.get("coffee-shop/pom.xml"))
                    .withFileFromPath("src", Paths.get("coffee-shop/src")))
            .withExposedPorts(8080)
            .withFileSystemBind("/home/sebastian/.m2/", "/root/.m2/")
            .withNetwork(network)
            .withNetworkAliases("coffee-shop")
            .withReuse(true)
            .dependsOn(barista, coffeeShopDb)
            .withLogConsumer(o -> System.out.println(o.getUtf8String()));

    public static void main(String[] args) throws IOException {
        var containers = Set.of(coffeeShop, barista, coffeeShopDb);
        Startables.deepStart(containers).join();
        Integer coffeeShopPort = coffeeShop.getMappedPort(8080);

        System.out.println("Now you can connect to Quarkus remote-dev by executing the following in /coffee-shop:");
        System.out.println("mvn compile quarkus:remote-dev -Dquarkus.live-reload.url=http://localhost:" + coffeeShopPort + " -Dquarkus.live-reload.password=123");
        System.out.println("Containers started. Press any key to stop");
        System.in.read();

        containers.forEach(Startable::stop);
    }

}
