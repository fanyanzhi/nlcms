package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.HoldrecordMapper;
import cn.gov.nlc.pojo.Holdrecord;
import cn.gov.nlc.service.HoldrecordService;

@Service
public class HoldrecordServiceImpl implements HoldrecordService {

	@Autowired
	HoldrecordMapper holdrecordMapper;

	public void insertHoldrecord(Holdrecord holdrecord) {
		holdrecordMapper.insert(holdrecord);
	}
}
