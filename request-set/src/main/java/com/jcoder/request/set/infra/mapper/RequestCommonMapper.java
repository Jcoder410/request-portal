package com.jcoder.request.set.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcoder.request.set.domain.entity.RequestCommon;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请求通用信息DB交互
 *
 * @author Qiu
 */
@Mapper
public interface RequestCommonMapper extends BaseMapper<RequestCommon> {

}
