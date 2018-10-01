package cn.gov.nlc.service;

import java.util.List;
import cn.gov.nlc.pojo.Menu;

public interface ParamsManagerService {

	/**
	 * 从menu中得到功能模块
	 */
	public List<Menu> getModule();
}
