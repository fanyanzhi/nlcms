package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.AppstatistMapper;
import cn.gov.nlc.pojo.Appstatist;
import cn.gov.nlc.service.AppstatistService;

@Service
public class AppstatistServiceImpl implements AppstatistService {

	@Autowired
	AppstatistMapper appstatistMapper;

	@Override
	public void insertAppstatist(Appstatist appstatist) {
		appstatistMapper.insert(appstatist);
	}

}
