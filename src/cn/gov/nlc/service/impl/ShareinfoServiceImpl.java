package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.ShareinfoMapper;
import cn.gov.nlc.pojo.Shareinfo;
import cn.gov.nlc.service.ShareinfoService;

@Service
public class ShareinfoServiceImpl implements ShareinfoService {

	@Autowired
	ShareinfoMapper shareinfoMapper;

	@Override
	public void insertShareinfo(Shareinfo shareinfo) {
		shareinfoMapper.insert(shareinfo);
	}

}
