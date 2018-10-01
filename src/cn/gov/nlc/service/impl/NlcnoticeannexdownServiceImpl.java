package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.NlcnoticeannexdownMapper;
import cn.gov.nlc.pojo.Nlcnoticeannexdown;
import cn.gov.nlc.service.NlcnoticeannexdownService;

@Service
public class NlcnoticeannexdownServiceImpl implements NlcnoticeannexdownService {

	@Autowired
	NlcnoticeannexdownMapper nlcnoticeannexdownMapper;

	@Override
	public void insertNlcnoticeannexdown(Nlcnoticeannexdown nlcnoticeannexdown) {
		nlcnoticeannexdownMapper.insert(nlcnoticeannexdown);
	}

}
