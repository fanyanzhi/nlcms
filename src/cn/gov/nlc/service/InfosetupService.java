package cn.gov.nlc.service;

import java.util.List;
import cn.gov.nlc.pojo.Infosetup;

public interface InfosetupService {

	/**
	 * 获取全部列表
	 * 
	 * @return
	 */
	public List<Infosetup> getAllList();

	/**
	 * 根据主键修改
	 */
	public void updateBypk(Infosetup infosetup);

	public Infosetup getInfoBytype(int type);
}
