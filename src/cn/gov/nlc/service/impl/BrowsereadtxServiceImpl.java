package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.BrowsereadtxMapper;
import cn.gov.nlc.pojo.Browsereadtx;
import cn.gov.nlc.service.BrowsereadtxService;

@Service
public class BrowsereadtxServiceImpl implements BrowsereadtxService {

	@Autowired
	BrowsereadtxMapper browsereadtxMapper;

	@Override
	public void insertBrowsereadtx(Browsereadtx browsereadtx) {
		browsereadtxMapper.insert(browsereadtx);
	}

}
