package cn.gov.nlc.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.DownloadrecordMapper;
import cn.gov.nlc.pojo.Downloadrecord;
import cn.gov.nlc.service.DownloadrecordService;

@Service
public class DownloadrecordServiceImpl implements DownloadrecordService{

	@Autowired
	DownloadrecordMapper downloadrecordMapper;

	/**
	 * 插入数据
	 * @param method
	 */
	@Override
	public void insertDownloadrecord(String method) {
		Downloadrecord ele = new Downloadrecord();
		ele.setMethod(method);
		ele.setTime(new Date());
		downloadrecordMapper.insert(ele);
	}
}
