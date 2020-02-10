package com.sebastian_daschner.coffee_shop.microshed.openapi;

import java.util.HashMap;
import java.util.Map;

public class OpenApi {

    public String openapi;
    public Map<String, String> info = new HashMap<>();

    // TODO implement properly, just an example for now
    public Map<String, Object> paths = new HashMap<>();
    public Map<String, Object> components = new HashMap<>();

}
