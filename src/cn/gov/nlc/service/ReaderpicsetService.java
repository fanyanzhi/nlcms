package cn.gov.nlc.service;

import java.util.List;
import cn.gov.nlc.pojo.Readerpicset;

public interface ReaderpicsetService {

	/**
	 * 查出所有的读者画像展示设置
	 */
	public List<Readerpicset> getAll();
	
	/**
	 * 修改读者画像展示设置状态位
	 */
	public void modifyStatus(String typeidarr);
}
