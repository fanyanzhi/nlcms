package cn.gov.nlc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.NlcuserloginlogMapper;
import cn.gov.nlc.pojo.Nlcuserloginlog;
import cn.gov.nlc.pojo.NlcuserloginlogExample;
import cn.gov.nlc.pojo.NlcuserloginlogExample.Criteria;
import cn.gov.nlc.service.NlcuserloginlogService;

@Service
public class NlcuserloginlogServiceImpl implements NlcuserloginlogService {
	@Autowired
	NlcuserloginlogMapper nlcuserloginlogMapper;

	@Override
	public void insertNlcuserloginlog(Nlcuserloginlog nlcuserloginlog) {
		nlcuserloginlogMapper.insert(nlcuserloginlog);
	}

	/**
	 * 通过username获取
	 * @param username
	 * @return
	 */
	@Override
	public List<Nlcuserloginlog> getListByUsername(String username) {
		NlcuserloginlogExample nlcuserloginlogExample = new NlcuserloginlogExample();
		Criteria criteria = nlcuserloginlogExample.createCriteria();
		criteria.andUsernameEqualTo(username);
		nlcuserloginlogExample.setOrderByClause("time desc");
		List<Nlcuserloginlog> list = nlcuserloginlogMapper.selectByExample(nlcuserloginlogExample);
		return list;
	}
}
