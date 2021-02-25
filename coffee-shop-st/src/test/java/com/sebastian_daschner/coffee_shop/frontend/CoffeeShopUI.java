package com.sebastian_daschner.coffee_shop.frontend;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.ws.rs.core.UriBuilder;

public class CoffeeShopUI {

    private final WebDriver driver;

    public CoffeeShopUI() {
        driver = new ChromeDriver();
    }

    public void init() {
        driver.get(uriBuilder().path("index.html").toString());
        driver.manage().addCookie(new Cookie("session", "123"));
    }

    private UriBuilder uriBuilder() {
        String host = System.getProperty("coffee-shop.test.host", "localhost");
        String port = System.getProperty("coffee-shop.test.port", "8080");
        return UriBuilder.fromUri("http://{host}:{port}/")
                .resolveTemplate("host", host)
                .resolveTemplate("port", port);
    }

    public IndexView index() {
        driver.navigate().to(uriBuilder().path("index.html").toString());
        return new IndexView(driver);
    }

}
