package com.sebastian_daschner.coffee_shop.backend;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class Dockers {

    static final GenericContainer<?> coffeeShop;

    static {
        File file = Paths.get("coffee-shop/target/quarkus-app/quarkus-run.jar").toFile();
        System.out.println(file);
        System.out.println(file.getAbsoluteFile());
        ImageFromDockerfile image = new ImageFromDockerfile("hello")
                .withDockerfile(Paths.get("./Dockerfile.hello"))

//                .withFileFromPath("quarkus-app/", Paths.get("coffee-shop/target/quarkus-app/"))

//                .withFileFromFile("quarkus-run.jar", file)

                ;

        System.out.println(image.getBuildArgs());
        System.out.println(image.getTransferables());

        System.out.println("<<<<<<<<<<<<");
        System.out.println("<<<<<<<<<<<<");
        System.out.println(image.getDockerImageName());
        System.out.println("<<<<<<<<<<<<");
        System.out.println("<<<<<<<<<<<<");

        coffeeShop = new GenericContainer<>(image
        );
    }
//            .dependsOn(barista, coffeeShopDB);

    public static void main(String[] args) {

        Consumer<OutputFrame> consumer = (OutputFrame frame) -> System.out.print(frame.getUtf8String());
        coffeeShop.withLogConsumer(consumer);

        coffeeShop.start();

        LockSupport.parkNanos(200_000_000_000L);
    }

}
