package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class OrderProcessTimer {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderProcessor orderProcessor;

    @Schedule(second = "*/2", minute = "*", hour = "*", persistent = false)
    public void processUnfinishedOrders() {
        orderRepository.listUnfinishedOrders().forEach(orderProcessor::processOrder);
    }

}