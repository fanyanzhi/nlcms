package cn.gov.nlc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.InfosetupMapper;
import cn.gov.nlc.pojo.Infosetup;
import cn.gov.nlc.pojo.InfosetupExample;
import cn.gov.nlc.pojo.InfosetupExample.Criteria;
import cn.gov.nlc.service.InfosetupService;

@Service
public class InfosetupServiceImpl implements InfosetupService {

	@Autowired
	private InfosetupMapper infosetupMapper;

	/**
	 * 获取全部列表
	 * 
	 * @return
	 */
	@Override
	public List<Infosetup> getAllList() {
		InfosetupExample example = new InfosetupExample();
		List<Infosetup> list = infosetupMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据主键修改
	 */
	@Override
	public void updateBypk(Infosetup infosetup) {
		infosetupMapper.updateByPrimaryKeySelective(infosetup);
	}

	public Infosetup getInfoBytype(int type) {
		InfosetupExample example = new InfosetupExample();
		Criteria criteria = example.createCriteria();
		criteria.andTypeidEqualTo(type);
		List<Infosetup> list = infosetupMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}
}
