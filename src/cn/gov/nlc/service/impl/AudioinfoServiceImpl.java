package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.AudioinfoMapper;
import cn.gov.nlc.pojo.Audioinfo;
import cn.gov.nlc.service.AudioinfoService;

@Service
public class AudioinfoServiceImpl implements AudioinfoService {

	@Autowired
	private AudioinfoMapper audioinfoMapper;
	
	/**
	 * 插入
	 */
	@Override
	public void insertAudioinfo(Audioinfo audioinfo) {
		audioinfoMapper.insert(audioinfo);
	}
}

