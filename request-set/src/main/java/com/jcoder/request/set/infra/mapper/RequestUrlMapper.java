package com.jcoder.request.set.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcoder.request.set.domain.entity.RequestUrl;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自定义url列表DB交互
 *
 * @author Qiu
 */
@Mapper
public interface RequestUrlMapper extends BaseMapper<RequestUrl> {

}