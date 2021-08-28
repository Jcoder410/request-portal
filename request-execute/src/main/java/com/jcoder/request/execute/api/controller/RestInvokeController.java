package com.jcoder.request.execute.api.controller;

import com.jcoder.request.execute.domain.entity.RestRequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * rest接口透传
 *
 * @author Qiu
 */
@RestController
@RequestMapping("/invoke")
public class RestInvokeController {

    private void printMapData(Map<String, Object> datas) {
        for (String name : datas.keySet()) {
            System.out.println(name + " = " + datas.get(name).toString());
        }
    }

    @GetMapping(value = "/get", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getInvoke(@RequestParam(required = false) Map<String, Object> params) {

        printMapData(params);

        List datas = new ArrayList();
        datas.add(params);
        datas.add(params);

        return ResponseEntity.ok(datas);
    }

    @PostMapping(value = "/post", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> postInvoke(@RequestBody(required = false) Object params) {

        if (params instanceof Map) {
            printMapData((Map<String, Object>) params);
        } else {
            for (int i = 0; i < ((List) params).size(); i++) {
                System.out.println();
                printMapData((Map<String, Object>) ((List) params).get(i));
            }
        }
        return ResponseEntity.ok(params);
    }

    @PostMapping(value = "/multi/params", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> multiParamInvoke(@RequestBody(required = false) RestRequestParam params) {

        return ResponseEntity.ok(params);
    }

}
