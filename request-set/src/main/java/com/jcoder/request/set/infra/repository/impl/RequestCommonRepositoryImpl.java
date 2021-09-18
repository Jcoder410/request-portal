package com.jcoder.request.set.infra.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcoder.request.set.domain.entity.RequestCommon;
import com.jcoder.request.set.domain.repository.RequestCommonRepository;
import com.jcoder.request.set.infra.mapper.RequestCommonMapper;
import org.springframework.stereotype.Service;

/**
 * @author Qiu
 */
@Service
public class RequestCommonRepositoryImpl extends ServiceImpl<RequestCommonMapper, RequestCommon> implements RequestCommonRepository {

}
