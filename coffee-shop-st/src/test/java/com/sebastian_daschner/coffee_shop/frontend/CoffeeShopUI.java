package com.sebastian_daschner.coffee_shop.frontend;

import org.openqa.selenium.Cookie;

import javax.ws.rs.core.UriBuilder;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CoffeeShopUI {

    public void init() {
        open(uriBuilder().path("index.html").toString());
        getWebDriver().manage().addCookie(new Cookie("session", "123"));
    }

    private UriBuilder uriBuilder() {
        String host = System.getProperty("coffee-shop.test.host", "localhost");
        String port = System.getProperty("coffee-shop.test.port", "8001");
        return UriBuilder.fromUri("http://{host}:{port}/")
                .resolveTemplate("host", host)
                .resolveTemplate("port", port);
    }

    public IndexView index() {
        open(uriBuilder().path("index.html").toString());
        return new IndexView();
    }

}
