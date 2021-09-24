package com.jcoder.request.execute.api.controller;

import com.jcoder.request.common.BaseController;
import com.jcoder.request.execute.app.service.IDefaultInvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 默认的rest接口透传处理类
 *
 * @author Qiu
 */
@RestController
@RequestMapping("/invoke")
public class DefaultRestInvokeController extends BaseController {

    @Autowired
    private IDefaultInvokeService defaultInvokeService;

    @RequestMapping(value = "/default/**",
            produces = "application/json;charset=UTF-8",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> getInvoke(@RequestParam Map<String, Object> requestParams,
                                       @RequestBody(required = false) Object requestBody,
                                       @RequestHeader(required = false) Map<String, String> requestHeader,
                                       HttpServletRequest request) {

        System.out.println(request.getRequestURI());

        ResponseEntity responseEntity = defaultInvokeService.executeInvoke(requestParams,
                requestBody,
                request.getRequestURI(),
                requestHeader);

        return responseEntity;
    }
}
