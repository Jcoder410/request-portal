package com.jcoder.request.execute.app.service;

import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;

/**
 * @author Qiu
 */
public interface ISoapInvokeService {

    /**
     * soap接口透传
     *
     * @param param
     * @return
     */
    SoapResponse soapInvoke(SoapRequestParam param);

}
