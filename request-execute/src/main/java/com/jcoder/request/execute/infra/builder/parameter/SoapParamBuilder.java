package com.jcoder.request.execute.infra.builder.parameter;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 来源和透传出去的接口类型都为soap的情况
 * soap接口请求参数构建
 *
 * @author Qiu
 */
@Component
public class SoapParamBuilder implements IHttpParamBuilder<List<String>, SetCacheEntity> {

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public HttpParameter builder(List<String> paramList, SetCacheEntity cacheEntity) {

        HttpParameter httpParameter = new HttpParameter();
        httpParameter.setHeaderParams(new HashMap<>());
        httpParameter.setUrl(cacheEntity.getSourceRequestUrl());
        httpParameter.setMethodCode(cacheEntity.getSourceMethodCode());
        httpParameter.setReturnType(String.class);

        //获取请求体信息
        if (StringUtils.isEmpty(cacheEntity.getSoapParamTemplate())) {
            StringBuilder bodyBuilder = new StringBuilder("");
            for (String xmlNode : paramList) {
                bodyBuilder.append(xmlNode);
            }
            httpParameter.setRequestBody(bodyBuilder.toString());
        } else {
            if (StringUtils.isEmpty(cacheEntity.getSoapParamNodeName())) {
                throw new CommonException("request.execute.param_node_null_err");
            }
            try {
                String soapBody = httpMessageUtil.buildSoapRequestBody(paramList,
                        cacheEntity.getSoapParamTemplate(),
                        cacheEntity.getSoapParamNodeName());
                httpParameter.setRequestBody(soapBody);
            } catch (DocumentException e) {
                throw new CommonException("request.execute.build_soap_body_err", e);
            }
        }
        return httpParameter;
    }
}
