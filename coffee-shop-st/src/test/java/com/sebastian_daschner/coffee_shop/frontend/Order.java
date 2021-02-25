package com.sebastian_daschner.coffee_shop.frontend;

public class Order {

    public final String status;
    public final String type;
    public final String origin;

    public Order(String status, String type, String origin) {
        this.status = status;
        this.type = type;
        this.origin = origin;
    }

}
