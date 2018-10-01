package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.Nlcads;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface NlcadsService {

	/**
	 * 广告展示的分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getAdsList(int page, int rows);
	
	/**
	 * 删除广告信息，单个删除
	 */
	public int deleteSingleById(Integer id);
	
	/**
	 * 广告上下架
	 */
	public void shelfAds(Integer id);
	
	/**
	 * 添加广告
	 */
	public void insertAds(Nlcads nlcads);
	
	/**
	 * 通过主键查询
	 */
	public Nlcads selectByPrimaryKey(Integer id);
	
	/**
	 * 修改广告
	 */
	public void updateAds(Map<String, String[]> parameterMap) throws Exception;
	
	public List<Nlcads> getAppNlcAds();
}
