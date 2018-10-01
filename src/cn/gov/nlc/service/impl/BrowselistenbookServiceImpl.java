package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.BrowselistenbookMapper;
import cn.gov.nlc.pojo.Browselistenbook;
import cn.gov.nlc.service.BrowselistenbookService;

@Service
public class BrowselistenbookServiceImpl implements BrowselistenbookService {

	@Autowired
	BrowselistenbookMapper browselistenbookMapper;

	@Override
	public void insertBrowselistenbook(Browselistenbook browselistenbook) {
		browselistenbookMapper.insert(browselistenbook);
	}

}
