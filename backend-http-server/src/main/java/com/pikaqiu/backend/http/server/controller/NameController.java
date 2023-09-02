package com.pikaqiu.backend.http.server.controller;

import com.pikaqiu.gateway.client.core.ApiInvoker;
import com.pikaqiu.gateway.client.core.ApiProperties;
import com.pikaqiu.gateway.client.core.ApiProtocol;
import com.pikaqiu.gateway.client.core.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NameController {

    @Autowired
    private ApiProperties apiProperties;

    @GetMapping("/name")
    public String name(String name) {
        log.info("{}", apiProperties);
        return name;
    }
}
