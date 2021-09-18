package com.jcoder.request.set.infra.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcoder.request.set.domain.entity.RequestHeader;
import com.jcoder.request.set.domain.repository.RequestHeaderRepository;
import com.jcoder.request.set.infra.mapper.RequestHeaderMapper;
import org.springframework.stereotype.Service;

/**
 * @author Qiu
 */
@Service
public class RequestHeaderRepositoryImpl extends ServiceImpl<RequestHeaderMapper, RequestHeader> implements RequestHeaderRepository {

}
