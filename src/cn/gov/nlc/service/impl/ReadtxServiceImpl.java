package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.ReadtxMapper;
import cn.gov.nlc.pojo.Readtx;
import cn.gov.nlc.pojo.ReadtxExample;
import cn.gov.nlc.service.ReadtxService;

@Service
public class ReadtxServiceImpl implements ReadtxService {

	@Autowired
	ReadtxMapper readtxMapper;

	@Override
	public void insertReadtx(Readtx readtx) {
		readtxMapper.insert(readtx);
	}

	@Override
	public boolean existReadtx(String magazineid) {
		ReadtxExample example = new ReadtxExample();
		cn.gov.nlc.pojo.ReadtxExample.Criteria criteria = example.createCriteria();
		criteria.andMagazineidEqualTo(magazineid);
		if (readtxMapper.countByExample(example) > 0)
			return true;
		return false;
	}
}
