package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
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