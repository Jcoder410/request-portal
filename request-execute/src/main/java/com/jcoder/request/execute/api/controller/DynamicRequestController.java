package com.jcoder.request.execute.api.controller;

import com.jcoder.request.common.BaseController;
import com.jcoder.request.execute.app.service.impl.RestExecutorServiceImpl;
import com.jcoder.request.execute.domain.entity.RestParameter;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用于处理自定义url地址的访问请求
 *
 * @author Qiu
 */
@Controller
public class DynamicRequestController extends BaseController {

    @Autowired
    private RestExecutorServiceImpl restExecutorService;

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
                                          @RequestHeader(required = false) Map<String, String> requestHeader) throws DocumentException {

        RestParameter restParameter = new RestParameter();
        restParameter.setHeaderParams(requestHeader);
        restParameter.setPathParams(pathParams);
        restParameter.setRequestBody(requestBody);
        restParameter.setRequestParams(requestParams);

        ResponseEntity response = restExecutorService.execute(restParameter);

        return response;
    }

}
