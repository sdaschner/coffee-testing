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

        //                .withFileFromPath("target/quarkus-app/quarkus-run.jar", Paths.get("./coffee-shop/target/quarkus-app/quarkus-run.jar"))
//                .withFileFromPath("target/quarkus-app/lib/", Paths.get("./coffee-shop/target/quarkus-app/lib/"))
//                .withFileFromPath("target/quarkus-app/app/", Paths.get("./coffee-shop/target/quarkus-app/app/"))
//                .withFileFromPath("target/quarkus-app/quarkus/", Paths.get("./coffee-shop/target/quarkus-app/quarkus/"));


    public static void main(String[] args) {

        Network network = Network.newNetwork();

        coffeeShopDB.addExposedPort(5432);
        coffeeShopDB.setNetwork(network);
        coffeeShopDB.setNetworkAliases(List.of("coffee-shop-db"));

        barista.addExposedPort(8080);
        barista.setPortBindings(List.of("8002:8080/tcp"));
        barista.setNetwork(network);
        barista.setNetworkAliases(List.of("barista"));

        coffeeShop.addExposedPort(8080);
        coffeeShop.setPortBindings(List.of("8001:8080/tcp"));
        coffeeShop.setNetwork(network);
        coffeeShop.setNetworkAliases(List.of("coffee-shop"));
        Consumer<OutputFrame> consumer = (OutputFrame frame) -> System.out.print(frame.getUtf8String());
        coffeeShop.withLogConsumer(consumer);

        Startables.deepStart(coffeeShop, coffeeShopDB, barista).join();

        LockSupport.parkNanos(200_000_000_000L);
    }

}
