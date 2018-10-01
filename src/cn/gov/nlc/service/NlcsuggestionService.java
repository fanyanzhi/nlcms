package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Nlcsuggestion;
import cn.gov.nlc.pojo.NlcsuggestionExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface NlcsuggestionService {

	/**
	 * 意见反馈展示页面
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getSuggestionList(int page, int rows, NlcsuggestionExt nlcsuggestionExt);
	
	/**
	 * 删除意见，单个删除
	 */
	public int deleteSingleById(Integer id);
	
	/**
	 * 查询所有的意见信息
	 */
	public List<Nlcsuggestion> getAll();
	
	/**
	 * 通过主键查找意见
	 */
	public Nlcsuggestion selectByPrimaryKey(Integer id);
	
	/**
	 * 意见编辑/回复
	 * flag 1是编辑，2是回复
	 */
	public void updateSuggestion(Integer id, String flag, String answer, String adminname);
	
	/**
	 * 插入意见
	 */
	public void insertSuggestion(Nlcsuggestion nlcsuggestion);
	
	/**
	 * 通过username查找list
	 */
	public List<Nlcsuggestion> getListByUsername(String username);
	
	public List<Nlcsuggestion> getSelect(String ids);
	
}
