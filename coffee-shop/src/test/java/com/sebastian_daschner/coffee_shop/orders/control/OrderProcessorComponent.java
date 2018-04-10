package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderProcessorComponent extends OrderProcessor {

    public OrderProcessorComponent() {
        target = mock(WebTarget.class);
        entityManager = mock(EntityManager.class);

        Invocation.Builder mockBuilder = mock(Invocation.Builder.class);
        when(target.request()).thenReturn(mockBuilder);
        Invocation mockInvocation = mock(Invocation.class);
        when(mockBuilder.buildPost(any())).thenReturn(mockInvocation);
        Response mockResponse = mock(Response.class);
        when(mockResponse.getStatusInfo()).thenReturn(Response.Status.OK);
        when(mockResponse.readEntity(JsonObject.class)).thenReturn(Json.createObjectBuilder().add("status", "PREPARING").build());
        when(mockInvocation.invoke()).thenReturn(mockResponse);
    }

    public void verifyProcessOrders(List<Order> orders) {
        verify(target, times(orders.size())).request();
    }

}
