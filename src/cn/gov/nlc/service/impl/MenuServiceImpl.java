package cn.gov.nlc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.MenuMapper;
import cn.gov.nlc.pojo.Menu;
import cn.gov.nlc.pojo.MenuExample;
import cn.gov.nlc.pojo.MenuExample.Criteria;
import cn.gov.nlc.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 按照排序获得全部的左侧菜单项
	 * @return
	 */
	@Override
	public List<Menu> getAll(int role, String moduleid) {
		List<String> excluList = new ArrayList<String>();
		List<Integer> idInList = new ArrayList<Integer>();
		
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		
		if(role != 0) {	//不是超管，角色只是编辑或浏览
			if(StringUtils.isNotBlank(moduleid)) {
				String[] arr = moduleid.split(",");
				getChildNode(idInList, Arrays.asList(arr));
				criteria.andIdIn(idInList);
			}else {
				Menu fake = new Menu();
				fake.setId(999);
				fake.setPid(9999);
				fake.setTextcn("无任何权限");
				fake.setChecked("false");
				fake.setState("open");
				fake.setIconcls("icon_sys");
				List<Menu> tlist = new ArrayList<Menu>();
				tlist.add(fake);
				return tlist;
			}
		}
		
		if(role == 2) {	//角色为浏览
			//不显示  添加新闻  添加公告  添加讲座预告 特色专题发布
			excluList.add("tjxw");
			excluList.add("tjgg");
			excluList.add("tjjz");
			excluList.add("tsztfb");
		}
		
		if(role != 0) {	//角色值不为0，即角色是编辑或浏览
			//不显示  添加管理员
			excluList.add("tjgly");
			criteria.andTextenNotIn(excluList);
		}
		
		example.setOrderByClause("nodorder");
		List<Menu> list = menuMapper.selectByExample(example);
		return list;
	}
	
	private void getChildNode(List<Integer> idInList, List<String> paraList) {
		if(null != paraList && paraList.size() > 0) {
			for (String str : paraList) {
				idInList.add(Integer.valueOf(str));
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT id FROM `menu` where pid = ");
				List<String> list = jdbcTemplate.queryForList(sb.append(str).toString(), String.class);
				if(null != list && list.size() > 0) {
					getChildNode(idInList, list);
				}
			}
		}
	}
}
