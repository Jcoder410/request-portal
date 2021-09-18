package com.jcoder.request.set.infra.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcoder.request.set.domain.entity.RequestParam;
import com.jcoder.request.set.domain.repository.RequestParamRepository;
import com.jcoder.request.set.infra.mapper.RequestParamMapper;
import org.springframework.stereotype.Service;

/**
 * @author Qiu
 */
@Service
public class RequestParamRepositoryImpl extends ServiceImpl<RequestParamMapper, RequestParam> implements RequestParamRepository {

}
