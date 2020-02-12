package com.sebastian_daschner.coffee_shop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    private static final String PROFILE_KEY = "microshed-profile";
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = TestConfig.class.getResourceAsStream("/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load test configuration from /application.properties");
            e.printStackTrace();
        }
    }

    public static void setProfile(String profile) {
        System.setProperty(PROFILE_KEY, profile);
    }

    public static void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public static String getProperty(String key) {
        String profile = System.getProperty(PROFILE_KEY);
        return resolveProperty(key, null, profile);
    }

    public static String getProperty(String key, String defaultValue) {
        String profile = System.getProperty(PROFILE_KEY);
        return resolveProperty(key, defaultValue, profile);
    }

    public static String getProperty(String key, String defaultValue, String profile) {
        return resolveProperty(key, defaultValue, profile);
    }

    /**
     * Will resolve the property in the following order:
     * <ul>
     *     <li>System property with {@code key}</li>
     *     <li>Property in {@code application.properties} with profiled {@code key} ({@code %{profile}.{key}})</li>
     *     <li>Property in {@code application.properties} with {@code key}</li>
     *     <li>Provided {@code defaultValue}</li>
     *     <li>or else {@code null}</li>
     * </ul>
     */
    private static String resolveProperty(String key, String defaultValue, String profile) {
        String sysProperty = System.getProperty(key);
        if (sysProperty != null)
            return sysProperty;

        if (profile != null && !profile.isBlank()) {
            String profileKey = '%' + profile + '.' + key;
            if (properties.containsKey(profileKey))
                return properties.getProperty(profileKey);
        }

        return properties.getProperty(key, defaultValue);
    }

}
