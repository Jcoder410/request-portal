package com.jcoder.request.set.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcoder.request.set.domain.entity.RequestPathVariable;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请求路径参数DB交互
 *
 * @author Qiu
 */
@Mapper
public interface RequestPathVariableMapper extends BaseMapper<RequestPathVariable> {
}
