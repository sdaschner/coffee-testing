package com.sebastian_daschner.coffee_shop;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public final class DotEnvFile {

    public static void writeDotEnvFile(int coffeeShopPort, int baristaPort) {
        Path path = Paths.get(System.getProperty("user.dir"), ".env");
        System.out.println("Writing port config to " + path);

        List<String> lines = List.of(
                "COFFEE_SHOP_TEST_HOST=localhost",
                "COFFEE_SHOP_TEST_PORT=" + coffeeShopPort,
                "BARISTA_TEST_HOST=localhost",
                "BARISTA_TEST_PORT=" + baristaPort
        );

        try {
            Files.write(path, lines, CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
