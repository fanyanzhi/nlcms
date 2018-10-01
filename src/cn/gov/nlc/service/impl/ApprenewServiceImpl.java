package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.ApprenewMapper;
import cn.gov.nlc.pojo.Apprenew;
import cn.gov.nlc.service.ApprenewService;

@Service
public class ApprenewServiceImpl implements ApprenewService {

	@Autowired
	ApprenewMapper appinrenewMapper;

	public void insertApprenew(Apprenew apprenew) {
		appinrenewMapper.insert(apprenew);
	}
}
