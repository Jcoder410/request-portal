package com.jcoder.request.set.infra.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcoder.request.set.domain.entity.RequestPathVariable;
import com.jcoder.request.set.domain.repository.RequestPathVariableRepository;
import com.jcoder.request.set.infra.mapper.RequestPathVariableMapper;
import org.springframework.stereotype.Service;

/**
 * @author Qiu
 */
@Service
public class RequestPathVariableRepositoryImpl extends ServiceImpl<RequestPathVariableMapper, RequestPathVariable> implements RequestPathVariableRepository {

}
