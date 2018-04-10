package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.boundary.CoffeeShop;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class OrderProcessTimer {

    @Inject
    CoffeeShop coffeeShop;

    @Schedule(second = "*/20", minute = "*", hour = "*", persistent = false)
    public void processOrder() {
        coffeeShop.processUnfinishedOrders();
    }

}
