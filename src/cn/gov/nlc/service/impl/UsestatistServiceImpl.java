package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.UsestatistMapper;
import cn.gov.nlc.pojo.Usestatist;
import cn.gov.nlc.service.UsestatistService;

@Service
public class UsestatistServiceImpl implements UsestatistService {
	@Autowired
	UsestatistMapper usestatistMapper;

	@Override
	public void insertUsestatis(Usestatist usestatist) {
		usestatistMapper.insert(usestatist);
	}

}
