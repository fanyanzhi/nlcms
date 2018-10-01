package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface HotwordService {
	public void insertHotword(Hotword hotword);
	
	public int getHotwordID(String hotword);
	
	public void updSeaCount(int id);
	
	public List<Hotword> getHotword();
	
	/**
	 * 热词的list
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getHotwordList(Integer page, Integer rows);
	
	/**
	 * 删除热词，单个删除
	 */
	public int deleteSingleById(Integer id);

	/**
	 * 修改热词值及排序
	 * @param reslist
	 */
	public void saveModi(List<Hotword> reslist);
}
