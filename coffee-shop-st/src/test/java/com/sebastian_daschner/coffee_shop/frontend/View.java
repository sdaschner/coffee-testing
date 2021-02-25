package com.sebastian_daschner.coffee_shop.frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class View {

    protected final WebDriver driver;

    public View(WebDriver driver) {
        this.driver = driver;
    }

    protected FluentWait<WebDriver> waitFor() {
        return new WebDriverWait(driver, 8);
    }

}
