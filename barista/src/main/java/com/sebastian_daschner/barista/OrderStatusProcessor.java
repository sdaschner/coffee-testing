package com.sebastian_daschner.barista;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderStatusProcessor {

    public String process(final String status) {
        switch (status) {
            case "PREPARING":
                return "FINISHED";
            case "FINISHED":
                return "COLLECTED";
            case "COLLECTED":
                return "COLLECTED";
            default:
                throw new IllegalArgumentException("Unknown status " + status);
        }
    }

}
