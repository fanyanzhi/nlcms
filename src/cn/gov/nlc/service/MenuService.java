package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Menu;

public interface MenuService {

	/**
	 * 按照排序获得全部的左侧菜单项
	 * @return
	 */
	public List<Menu> getAll(int role, String moduleid);
}
