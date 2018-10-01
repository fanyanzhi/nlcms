package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.AppinstallMapper;
import cn.gov.nlc.pojo.Appinstall;
import cn.gov.nlc.service.AppinstallService;

@Service
public class AppinstallServiceImpl implements AppinstallService {

	@Autowired
	AppinstallMapper appinstallMapper;

	@Override
	public void insertAppinstall(Appinstall appinstall) {
		appinstallMapper.insert(appinstall);
	}
}
