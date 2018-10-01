package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.RenewrecordMapper;
import cn.gov.nlc.pojo.Renewrecord;
import cn.gov.nlc.service.RenewrecordService;

@Service
public class RenewrecordServiceImpl implements RenewrecordService {

	@Autowired
	RenewrecordMapper renewrecordMapper;

	@Override
	public void insertRenewrecord(Renewrecord renewrecord) {
		renewrecordMapper.insert(renewrecord);
	}

}
