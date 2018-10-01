package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.AccesslogMapper;
import cn.gov.nlc.pojo.Accesslog;
import cn.gov.nlc.service.AccesslogService;

@Service
public class AccesslogServiceImpl implements AccesslogService {

	@Autowired
	private AccesslogMapper accesslogMapper;

	@Override
	public void insertAccesslog(Accesslog accesslog) {
		accesslogMapper.insert(accesslog);
	}

}
