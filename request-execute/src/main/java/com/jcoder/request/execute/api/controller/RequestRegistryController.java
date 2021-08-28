package com.jcoder.request.execute.api.controller;

import com.jcoder.request.execute.app.service.IRequestRegistryService;
import com.jcoder.request.execute.domain.entity.RequestRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * 用于自定义透传接口URL的注册和移除
 *
 * @author Qiu
 */
@RestController
@RequestMapping("/request")
public class RequestRegistryController {

    @Autowired
    private IRequestRegistryService requestRegistryService;

    @PostMapping("/registry")
    public ResponseEntity<?> registryRequest(@RequestBody RequestRegistry requestRegistry) {

        requestRegistryService.requestRegistry(requestRegistry);

        return ResponseEntity.ok(requestRegistry);
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeRequest(@RequestBody RequestRegistry requestRegistry) {

        requestRegistryService.removeRegistry(requestRegistry);

        return ResponseEntity.ok(requestRegistry);
    }

}
