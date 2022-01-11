package com.thinhph.JsonToJavaFormat.service.impl;

import com.thinhph.JsonToJavaFormat.service.JSONService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class JSONServiceImpl implements JSONService {

    public static final String SETTER_FORMAT = "myObject.set";
    public static final String BUILDER_FORMAT = "MyObject.builder().";
    public static final String CONSTRUCTOR_FORMAT = "myObject(";

    @Override
    public String getJavaConstructorFormat(LinkedHashMap<String, String> jsonMap) {
        StringBuilder constructor = new StringBuilder();
        constructor.append(CONSTRUCTOR_FORMAT);
        jsonMap.keySet().forEach(key -> {

            if (jsonMap.get(key) == null)
                constructor.append("null").append(", ");
            else if (jsonMap.get(key).equals(""))
                constructor.append("\"\"").append(", ");
            else if (StringUtils.isNumeric(jsonMap.get(key)))
                constructor.append(jsonMap.get(key)).append(", ");
            else
                constructor.append("\"").append(jsonMap.get(key)).append("\"").append(", ");
        });
        return constructor.substring(0, constructor.length() - 2) + ");";
    }

    @Override
    public String getJavaBuilderFormat(LinkedHashMap<String, String> jsonMap) {
        StringBuilder builder = new StringBuilder();
        builder.append(BUILDER_FORMAT);
        jsonMap.keySet().forEach(key -> {
            if (jsonMap.get(key) == null)
                builder.append("\n.").append(getBuilderKey(key)).append("(null)");
            else if (jsonMap.get(key).equals(""))
                builder.append("\n.").append(getBuilderKey(key)).append("(\"\")");
            else if (StringUtils.isNumeric(jsonMap.get(key)))
                    builder.append("\n.").append(getBuilderKey(key)).append("(").append(jsonMap.get(key)).append(")");
                else
                    builder.append("\n.").append(getBuilderKey(key)).append("(\"").append(jsonMap.get(key)).append("\")");
        });
        return builder.substring(0, builder.length() - 1) + ").build();";
    }

    @Override
    public String getJavaSetterFormat(LinkedHashMap<String, String> jsonMap) {
        StringBuilder setter = new StringBuilder();
        jsonMap.keySet().forEach(key -> {
            if(jsonMap.get(key) == null){
                setter.append("\n").append(SETTER_FORMAT).append(getKey(key))
                        .append("(null);");
            }else if(jsonMap.get(key).equals("")){
                setter.append("\n").append(SETTER_FORMAT).append(getKey(key)).append("\"\"");
            }else if(StringUtils.isNumeric(jsonMap.get(key)))
                setter.append("\n").append(SETTER_FORMAT).append(getKey(key)).append("(").append(jsonMap.get(key)).append(");");
            else
                setter.append("\n").append(SETTER_FORMAT).append(getKey(key)).append("(\"").append(jsonMap.get(key)).append("\");");
        });
        return setter.toString();
    }

    private String getKey(String key) {
        StringBuilder result = new StringBuilder();
        result.append(key.substring(0, 1).toUpperCase());
        for (int i = 1; i < key.length(); i++) {
            if(key.charAt(i-1) == '_') result.append(key.substring(i, i+1).toUpperCase());
            else if(key.charAt(i) != '_') result.append(key.charAt(i));
        }
        return result.toString();
    }

    private String getBuilderKey(String key){
        String result = getKey(key);
        return result.substring(0,1).toLowerCase().concat(result.substring(1));
    }
}
