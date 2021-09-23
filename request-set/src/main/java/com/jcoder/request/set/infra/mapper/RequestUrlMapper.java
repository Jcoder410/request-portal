package com.jcoder.request.set.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcoder.request.set.domain.entity.RequestUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义url列表DB交互
 *
 * @author Qiu
 */
@Mapper
public interface RequestUrlMapper extends BaseMapper<RequestUrl> {

    /**
     * 检查自定义url是否存在
     *
     * @param requestMethod
     * @param requestUrl
     * @param requestCommonId
     * @return
     */
    Integer checkUrlExisted(@Param("requestMethod") String requestMethod,
                            @Param("requestUrl") String requestUrl,
                            @Param("requestCommonId") Long requestCommonId);

}