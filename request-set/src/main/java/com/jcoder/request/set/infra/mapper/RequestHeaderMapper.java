package com.jcoder.request.set.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcoder.request.set.domain.entity.RequestHeader;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请求头信息DB交互
 *
 * @author Qiu
 */
@Mapper
public interface RequestHeaderMapper extends BaseMapper<RequestHeader> {

}
