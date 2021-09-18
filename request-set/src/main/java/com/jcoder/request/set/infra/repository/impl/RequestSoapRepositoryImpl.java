package com.jcoder.request.set.infra.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcoder.request.set.domain.entity.RequestSoap;
import com.jcoder.request.set.domain.repository.RequestSoapRepository;
import com.jcoder.request.set.infra.mapper.RequestSoapMapper;
import org.springframework.stereotype.Service;

/**
 * @author Qiu
 */
@Service
public class RequestSoapRepositoryImpl extends ServiceImpl<RequestSoapMapper, RequestSoap> implements RequestSoapRepository {

}
