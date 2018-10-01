package cn.gov.nlc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.BinduserMapper;
import cn.gov.nlc.pojo.Binduser;
import cn.gov.nlc.pojo.BinduserExample;
import cn.gov.nlc.pojo.BinduserExample.Criteria;
import cn.gov.nlc.service.BinduserService;

@Service
public class BinduserServiceImpl implements BinduserService {

	@Autowired
	BinduserMapper binduserMapper;

	@Override
	public Binduser getNlcuserinfo(String token) {
		BinduserExample example = new BinduserExample();
		Criteria criteria = example.createCriteria();
		criteria.andTokenEqualTo(token);
		example.setOrderByClause("time desc");
		List<Binduser> list = binduserMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public void insertBinduser(Binduser binduser) {
		binduserMapper.insert(binduser);
	}

}
