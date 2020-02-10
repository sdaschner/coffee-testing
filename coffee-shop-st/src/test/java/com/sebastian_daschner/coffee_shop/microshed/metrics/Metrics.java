package com.sebastian_daschner.coffee_shop.microshed.metrics;

import java.util.Map;
import java.util.stream.Collectors;

public class Metrics {

    public Map<String, String> metrics;

    public Metrics(String content) {
        metrics = content.lines()
                .filter(l -> !l.startsWith("#"))
                .map(Metric::parseLine)
                .collect(Collectors.toMap(m -> m.name, m -> m.value, (s1, s2) -> s1));
    }

}
