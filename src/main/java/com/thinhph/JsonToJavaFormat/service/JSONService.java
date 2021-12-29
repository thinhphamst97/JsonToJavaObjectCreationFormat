package com.thinhph.JsonToJavaFormat.service;

import java.util.LinkedHashMap;

public interface JSONService {

    String getJavaConstructorFormat(LinkedHashMap<String, String> json);
    String getJavaBuilderFormat(LinkedHashMap<String, String> json);
    String getJavaSetterFormat(LinkedHashMap<String, String> json);
}
