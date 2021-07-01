package com.sebastian_daschner.coffee_shop.orders;

import java.lang.reflect.Field;

public final class ReflectionSupport {

    private ReflectionSupport() {
    }

    public static void setReflectiveField(Object object, String fieldName, Object value) {
        try {
            Field f1 = object.getClass().getDeclaredField(fieldName);
            f1.setAccessible(true);
            f1.set(object, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}