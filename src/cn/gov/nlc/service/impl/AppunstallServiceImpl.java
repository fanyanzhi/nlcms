package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.AppunstallMapper;
import cn.gov.nlc.pojo.Appunstall;
import cn.gov.nlc.service.AppunstallService;

@Service
public class AppunstallServiceImpl implements AppunstallService {

	@Autowired
	AppunstallMapper appunstallMapper;

	public void insertAppunstall(Appunstall appunstall) {
		appunstallMapper.insert(appunstall);
	}
}
