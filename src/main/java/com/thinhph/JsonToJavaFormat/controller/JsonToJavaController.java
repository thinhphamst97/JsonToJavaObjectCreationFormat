package com.thinhph.JsonToJavaFormat.controller;

import com.thinhph.JsonToJavaFormat.service.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class JsonToJavaController {

    @Autowired
    JSONService jsonService;

    @PostMapping("/constructor")
    public ResponseEntity convertJsonToConstrucor(@RequestBody LinkedHashMap<String, String> json){
        return ResponseEntity.ok(jsonService.getJavaConstructorFormat(json));
    }
    @PostMapping("/builder")
    public ResponseEntity convertJsonToBuilder(@RequestBody LinkedHashMap<String, String> json){
        return ResponseEntity.ok(jsonService.getJavaBuilderFormat(json));
    }
    @PostMapping("/setter")
    public ResponseEntity convertJsonToBuilderSetter(@RequestBody LinkedHashMap<String, String> json){
        return ResponseEntity.ok(jsonService.getJavaSetterFormat(json));
    }
}
