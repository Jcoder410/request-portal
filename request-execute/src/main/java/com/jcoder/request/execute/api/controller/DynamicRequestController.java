package com.jcoder.request.execute.api.controller;

import com.jcoder.request.execute.app.service.IExecuteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理自定义url地址的访问请求
 *
 * @author Qiu
 */
@Controller
public class DynamicRequestController {

    @Autowired
    private IExecuteRequestService executeRequestService;

    /**
     * 通用的请求处理方法
     *
     * @param requestParams
     * @param requestBody
     * @param pathParams
     * @param requestHeader
     * @return
     */
    @ResponseBody
    public ResponseEntity<?> commonMethod(@RequestParam(required = false) Map<String, Object> requestParams,
                                          @RequestBody(required = false) Object requestBody,
                                          @PathVariable(required = false) Map<String, Object> pathParams,
                                          @RequestHeader(required = false) Map<String, Object> requestHeader) {

        Map<String, Object> resultMap = new HashMap<>();
        /*resultMap.put("requestParam", requestParams);
        resultMap.put("requestBody", requestBody);
        resultMap.put("pathVariable", pathParams);
        resultMap.put("requestHeader", requestHeader);*/

        String requestUrl = String.valueOf(requestHeader.get("baseurl"));

        ResponseEntity<Object> responseEntity = executeRequestService.executeRestRequest(requestUrl,
                (String) requestHeader.get("httpmethod"),
                requestParams,
                requestBody,
                pathParams,
                requestHeader);

        resultMap.put("payload", responseEntity.getBody());

        return ResponseEntity.ok(resultMap);
    }

}
