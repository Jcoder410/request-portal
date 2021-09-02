package com.jcoder.request.execute.api.controller;

import com.jcoder.request.execute.app.service.IDefaultInvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 默认的rest接口透传处理类
 *
 * @author Qiu
 */
@RestController
@RequestMapping("/default")
public class DefaultInvokeController {

    @Autowired
    private IDefaultInvokeService defaultInvokeService;

    @RequestMapping(value = "/invoke/{*path}",
            produces = "application/json;charset=UTF-8",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> getInvoke(@RequestParam Map<String, Object> requestParams,
                                       @RequestBody(required = false) Object requestBody,
                                       @PathVariable(required = false) String path,
                                       @RequestHeader(required = false) Map<String, Object> requestHeader) {

        ResponseEntity responseEntity = defaultInvokeService.executeInvoke(requestParams,
                requestBody,
                path,
                requestHeader);

        return responseEntity;
    }
}
