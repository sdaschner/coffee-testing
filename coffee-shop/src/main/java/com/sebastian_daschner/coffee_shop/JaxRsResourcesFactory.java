package com.sebastian_daschner.coffee_shop;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

// JAX-RS sub resource with injection fails in Payara https://github.com/payara/Payara/issues/5467
@Provider
@RequestScoped
public class JaxRsResourcesFactory implements ClientRequestFilter {

    @Produces
    @RequestScoped
    @Context UriInfo ctx;
    
    @Override
    public void filter(ClientRequestContext arg0) throws IOException {
        // empty - we just need to inject resources
    }
    
}
