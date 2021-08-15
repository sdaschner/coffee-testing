package com.sebastian_daschner.coffee_shop.backend;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.lifecycle.Startables;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class TestContainers {

    static final PostgreSQLContainer<?> coffeeShopDB = new PostgreSQLContainer<>("postgres:9.5")
            .withUsername("postgres")
            .withPassword("postgres");
    static final GenericContainer<?> barista = new GenericContainer<>("rodolpheche/wiremock:2.6.0");
    static final GenericContainer<?> coffeeShop = new GenericContainer<>(new ImageFromDockerfile("coffee-shop")
            .withDockerfile(Paths.get("./coffee-shop/Dockerfile.dev"))
    ).dependsOn(barista, coffeeShopDB);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Network network = Network.newNetwork();

        coffeeShopDB.addExposedPort(5432);
        coffeeShopDB.setNetwork(network);
        coffeeShopDB.setNetworkAliases(List.of("coffee-shop-db"));
        coffeeShopDB.withReuse(true);

        barista.addExposedPort(8080);
        barista.setNetwork(network);
        barista.setNetworkAliases(List.of("barista"));
        barista.withReuse(true);

        coffeeShop.addExposedPort(8080);
        coffeeShop.setNetwork(network);
        coffeeShop.setNetworkAliases(List.of("coffee-shop"));
        Consumer<OutputFrame> consumer = (OutputFrame frame) -> System.out.print(frame.getUtf8String());
        coffeeShop.withLogConsumer(consumer);
        coffeeShop.withReuse(true);

        Startables.deepStart(List.of(coffeeShop, coffeeShopDB, barista)).join();

        long time = System.currentTimeMillis() - start;
        System.out.println("starting took " + time + " ms");

        LockSupport.parkNanos(86_400_000_000_000L); // 1 day
    }

}
