package com.thinhph.JsonToJavaFormat.service.impl;

import com.thinhph.JsonToJavaFormat.service.JSONService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class JSONServiceImpl implements JSONService {

    public static final String SETTER_FORMAT = "myObject.set";
    public static final String BUILDER_FORMAT = "MyObject.builder().";
    public static final String CONSTRUCTOR_FORMAT = "MyObject(";
    public static final char DASH = '_';
    public static final String LINE_BREAK = "\n";
    public static final String NULL_VALUE = "null";
    public static final String END_BUILDER = ").build();";
    public static final String EMPTY = "\"\"";
    public static final String QUOTATION_MARK = "\"";
    public static final String COMMA = ", ";

    @Override
    public String getJavaConstructorFormat(LinkedHashMap<String, String> jsonMap) {
        StringBuilder constructor = new StringBuilder();
        constructor.append(CONSTRUCTOR_FORMAT);
        jsonMap.keySet().forEach(key -> {
            if (jsonMap.get(key) == null)
                setNextAttributeConstructor(jsonMap, constructor, key, NULL_VALUE);
            else if (jsonMap.get(key).equals(""))
                setNextAttributeConstructor(jsonMap, constructor, key, EMPTY);
            else if (StringUtils.isNumeric(jsonMap.get(key)))
                setNextAttributeConstructor(jsonMap, constructor, key, null);
            else
                setNextAttributeConstructor(jsonMap, constructor, key, getStringValue(jsonMap, key));
        });
        return constructor.substring(0, constructor.length() - 2) + ");";
    }

    @Override
    public String getJavaBuilderFormat(LinkedHashMap<String, String> jsonMap) {
        StringBuilder builder = new StringBuilder();
        builder.append(BUILDER_FORMAT);
        jsonMap.keySet().forEach(key -> {
            if (jsonMap.get(key) == null)
                setNextBuilder(jsonMap, builder, key, NULL_VALUE);
            else if (jsonMap.get(key).equals(""))
                setNextBuilder(jsonMap, builder, key, EMPTY);
            else if (StringUtils.isNumeric(jsonMap.get(key)))
                setNextBuilder(jsonMap, builder, key, null);
            else
                setNextBuilder(jsonMap, builder, key, getStringValue(jsonMap, key));
        });
        return builder.substring(0, builder.length() - 1) + END_BUILDER;
    }

    @Override
    public String getJavaSetterFormat(LinkedHashMap<String, String> jsonMap) {
        StringBuilder setter = new StringBuilder();
        jsonMap.keySet().forEach(key -> {
            if (jsonMap.get(key) == null) {
                setNextSetterKey(jsonMap, setter, key, NULL_VALUE);
            } else if (jsonMap.get(key).equals("")) {
                setNextSetterKey(jsonMap, setter, key, EMPTY);
            } else if (StringUtils.isNumeric(jsonMap.get(key)))
                setNextSetterKey(jsonMap, setter, key, null);
            else
                setNextSetterKey(jsonMap, setter, key, getStringValue(jsonMap, key));
        });
        return setter.toString();
    }

    private String getStringValue(LinkedHashMap<String, String> jsonMap, String key) {
        return QUOTATION_MARK + jsonMap.get(key) + QUOTATION_MARK;
    }

    private void setNextAttributeConstructor(LinkedHashMap<String, String> jsonMap, StringBuilder constructor, String key, String otherValue) {
        if(StringUtils.isEmpty(otherValue))
            constructor.append(jsonMap.get(key));
        else
            constructor.append(otherValue);
        constructor.append(COMMA);
    }

    private void setNextBuilder(LinkedHashMap<String, String> jsonMap, StringBuilder builder, String key, String otherValue) {
        builder.append(LINE_BREAK + ".")
                .append(getNextBuilderKey(key))
                .append("(");
        if(StringUtils.isEmpty(otherValue))
                builder.append(jsonMap.get(key));
        else
            builder.append(otherValue);
        builder.append(")");
    }

    private void setNextSetterKey(LinkedHashMap<String, String> jsonMap, StringBuilder setter, String key, String otherValue) {
        setter.append(LINE_BREAK).append(SETTER_FORMAT).append(getNextObjectKey(key)).append("(");
        if(StringUtils.isEmpty(otherValue))
            setter.append(jsonMap.get(key));
        else
            setter.append(otherValue);
        setter.append(");");
    }

    private String getNextBuilderKey(String key) {
        String result = getNextObjectKey(key);
        return result.substring(0, 1).toLowerCase().concat(result.substring(1));
    }

    private String getNextObjectKey(String key) {
        StringBuilder result = new StringBuilder();
        result.append(key.substring(0, 1).toUpperCase());
        for (int i = 1; i < key.length(); i++) {
            if (key.charAt(i - 1) == DASH) result.append(key.substring(i, i + 1).toUpperCase());
            else if (key.charAt(i) != DASH) result.append(key.charAt(i));
        }
        return result.toString();
    }
}
