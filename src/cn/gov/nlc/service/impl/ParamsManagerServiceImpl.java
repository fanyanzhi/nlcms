package cn.gov.nlc.service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import cn.gov.nlc.mapper.MenuMapper;
import cn.gov.nlc.pojo.Menu;
import cn.gov.nlc.pojo.MenuExample;
import cn.gov.nlc.pojo.MenuExample.Criteria;
import cn.gov.nlc.service.ParamsManagerService;

public class ParamsManagerServiceImpl implements ParamsManagerService{

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.ParamsManagerServiceImpl.class);

	@Autowired
	private MenuMapper menuMapper;
	
	/**
	 * 得到功能模块
	 */
	@Override
	public List<Menu> getModule() {
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdLessThanOrEqualTo(10);
		example.setOrderByClause("nodorder");
		List<Menu> list = menuMapper.selectByExample(example);
		return list;
	}
	
	
}
