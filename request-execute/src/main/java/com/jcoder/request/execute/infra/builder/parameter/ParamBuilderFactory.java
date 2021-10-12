package com.jcoder.request.execute.infra.builder.parameter;

import com.jcoder.request.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 用于获取参数构造类对应的bean
 *
 * @author Qiu
 */
@Component
public class ParamBuilderFactory {

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * @param invokeType 透传类型
     * @param sourceType 来源接口类型
     * @return
     */
    public IHttpParamBuilder getBuilder(String invokeType, String sourceType) {

        String builderName = ParamBuilderEnum.getBuilderName(invokeType, sourceType);
        if (StringUtils.isEmpty(builderName)) {
            throw new CommonException("request.execute.param_builder_null");
        }
        return (IHttpParamBuilder) webApplicationContext.getBean(builderName);
    }
}
