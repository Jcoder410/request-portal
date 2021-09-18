package com.jcoder.request.set.infra.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcoder.request.set.domain.entity.RequestUrl;
import com.jcoder.request.set.domain.repository.RequestUrlRepository;
import com.jcoder.request.set.infra.mapper.RequestUrlMapper;
import org.springframework.stereotype.Service;

/**
 * @author Qiu
 */
@Service
public class RequestUrlRepositoryImpl extends ServiceImpl<RequestUrlMapper, RequestUrl> implements RequestUrlRepository {

}
