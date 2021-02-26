package com.sebastian_daschner.coffee_shop.frontend;

import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.stream.Collectors.toList;

public class IndexView {

    public String getPageHeader() {
        return $("body > h1").text();
    }

    public List<Order> getListedOrders() {
        return $$("body > table tr").stream()
                .map(el -> el.findAll("td"))
                .filter(list -> !list.isEmpty())
                .map(list -> new Order(list.get(0).getText(), list.get(1).getText(), list.get(2).getText()))
                .collect(toList());
    }

    public OrderView followCreateOrderLink() {
        createOrderLink().click();
        return new OrderView();
    }

    private SelenideElement createOrderLink() {
        return $$("a").findBy(text("Create"));
    }

}
