package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.AppstartMapper;
import cn.gov.nlc.pojo.Appstart;
import cn.gov.nlc.service.AppstartService;

@Service
public class AppstartServiceImpl implements AppstartService {

	@Autowired
	AppstartMapper appstartMapper;

	@Override
	public void insertAppstart(Appstart appstart) {
		appstartMapper.insert(appstart);
	}

}
