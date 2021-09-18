package com.jcoder.request.set.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcoder.request.set.domain.entity.RequestSoap;
import org.apache.ibatis.annotations.Mapper;

/**
 * soap类型请求特有参数DB交互
 *
 * @author Qiu
 */
@Mapper
public interface RequestSoapMapper extends BaseMapper<RequestSoap> {

}
