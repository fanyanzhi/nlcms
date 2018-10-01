package cn.gov.nlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.ListenbookMapper;
import cn.gov.nlc.pojo.Listenbook;
import cn.gov.nlc.pojo.ListenbookExample;
import cn.gov.nlc.service.ListenbookService;

@Service
public class ListenbookServiceImpl implements ListenbookService {

	@Autowired
	ListenbookMapper listenbookMapper;

	@Override
	public void insertListenbook(Listenbook listenbook) {
		listenbookMapper.insert(listenbook);
	}

	@Override
	public boolean existListenbook(String bookid) {
		ListenbookExample example = new ListenbookExample();
		cn.gov.nlc.pojo.ListenbookExample.Criteria criteria = example.createCriteria();
		criteria.andBookidEqualTo(bookid);
		if (listenbookMapper.countByExample(example) > 0)
			return true;
		return false;
	}

}
