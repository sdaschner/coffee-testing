package com.sebastian_daschner.coffee_shop.microshed.metrics;

import java.util.HashMap;
import java.util.Map;

import static com.sebastian_daschner.coffee_shop.microshed.metrics.Metric.State.*;

public class Metric {

    public String name;
    public String value;
    Map<String, String> labels;

    private Metric(String name, String value, Map<String, String> labels) {
        this.name = name;
        this.value = value;
        this.labels = labels;
    }

    static Metric parseLine(String line) {
        Map<String, String> labels = new HashMap<>();

        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        StringBuilder labelNameBuilder = new StringBuilder();
        StringBuilder labelValueBuilder = new StringBuilder();

        State state = State.NAME;

        for (int index = 0; index < line.length(); index++) {
            char charAt = line.charAt(index);

            switch (state) {
                case NAME:
                    if (charAt == '{')
                        state = START_OF_LABEL_NAME;
                    else if (charAt == ' ' || charAt == '\t')
                        state = END_OF_NAME;
                    else
                        nameBuilder.append(charAt);
                    break;
                case END_OF_LABELS:
                    if (charAt != ' ' && charAt != '\t') {
                        valueBuilder.append(charAt);
                        state = VALUE;
                    }
                    break;
                case END_OF_NAME:
                    if (charAt == '{')
                        state = START_OF_LABEL_NAME;
                    else if (charAt != ' ' && charAt != '\t') {
                        valueBuilder.append(charAt);
                        state = VALUE;
                    }
                    break;
                case LABEL_NAME:
                    if (charAt == '=')
                        state = LABEL_VALUE_QUOTE;
                    else if (charAt == '}')
                        state = END_OF_LABELS;
                    else if (charAt == ' ' || charAt == '\t')
                        state = LABEL_VALUE_EQUALS;
                    else
                        labelNameBuilder.append(charAt);
                    break;
                case LABEL_VALUE:
                    if (charAt == '\\')
                        state = LABEL_VALUE_SLASH;
                    else if (charAt == '"') {
                        labels.put(labelNameBuilder.toString(), labelValueBuilder.toString());
                        labelNameBuilder.setLength(0);
                        labelValueBuilder.setLength(0);
                        state = NEXT_LABEL;
                    } else
                        labelValueBuilder.append(charAt);
                    break;
                case LABEL_VALUE_EQUALS:
                    if (charAt == '=')
                        state = LABEL_VALUE_QUOTE;
                    else if (charAt != ' ' && charAt != '\t')
                        throw new IllegalArgumentException("Invalid input line: " + line);
                    break;
                case LABEL_VALUE_QUOTE:
                    if (charAt == '"')
                        state = LABEL_VALUE;
                    else if (charAt != ' ' && charAt != '\t')
                        throw new IllegalArgumentException("Invalid input line: " + line);
                    break;
                case LABEL_VALUE_SLASH:
                    state = LABEL_VALUE;
                    if (charAt == '\\')
                        labelValueBuilder.append('\\');
                    else if (charAt == 'n')
                        labelValueBuilder.append('\n');
                    else if (charAt == '"')
                        labelValueBuilder.append('"');
                    else
                        labelValueBuilder.append('\\').append(charAt);
                    break;
                case NEXT_LABEL:
                    if (charAt == ',')
                        state = LABEL_NAME;
                    else if (charAt == '}')
                        state = END_OF_LABELS;
                    else if (charAt != ' ' && charAt != '\t')
                        throw new IllegalArgumentException("Invalid input line: " + line);
                    break;
                case START_OF_LABEL_NAME:
                    if (charAt == '}') {
                        state = END_OF_LABELS;
                    } else if (charAt != ' ' && charAt != '\t') {
                        labelNameBuilder.append(charAt);
                        state = LABEL_NAME;
                    }
                    break;
                case VALUE:
                    if (charAt != ' ' && charAt != '\t')
                        valueBuilder.append(charAt);
                    break;
            }
        }

        return new Metric(nameBuilder.toString(), valueBuilder.toString(), labels);
    }

    public enum State {
        NAME,
        END_OF_LABELS,
        END_OF_NAME,
        LABEL_NAME,
        LABEL_VALUE,
        LABEL_VALUE_EQUALS,
        LABEL_VALUE_QUOTE,
        LABEL_VALUE_SLASH,
        NEXT_LABEL,
        START_OF_LABEL_NAME,
        VALUE
    }
}
