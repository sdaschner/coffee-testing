package com.sebastian_daschner.coffee_shop.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class IndexView extends View {

    public IndexView(WebDriver driver) {
        super(driver);
    }

    public String getPageHeader() {
        return driver.findElement(By.cssSelector("body > h1")).getText();
    }

    public List<Order> getListedOrders() {
        return driver.findElements(By.cssSelector("body > table tr")).stream()
                .map(el -> el.findElements(By.cssSelector("td")))
                .filter(list -> !list.isEmpty())
                .map(list -> new Order(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()))
                .collect(Collectors.toList());
    }

    public OrderView followCreateOrderLink() {
        String href = driver.findElements(By.tagName("a")).stream()
                .filter(el -> el.getText().contains("Create"))
                .findAny()
                .orElseThrow().getAttribute("href");
        driver.navigate().to(href);
        return new OrderView(driver);
    }

}
