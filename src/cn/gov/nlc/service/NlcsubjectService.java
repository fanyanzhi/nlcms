package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.NlcsubjectExt;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

public interface NlcsubjectService {

	/**
	 * 专题分页展示
	 */
	public EasyUiDataGridResult getSubjectList(Integer page, Integer rows);

	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getSubjectListWithBlobtt(Integer page, Integer rows);

	public Nlcsubject getNlcSubjectBySubjectId(String subjectId);

	/**
	 * 删除专题，单个删除
	 */
	public int deleteSingleById(Integer id);

	/**
	 * 添加专题信息和专题目录的root项
	 */
	public void inertNlcsubjectAndCatalogRoot(Nlcsubject nlcsubject);

	/**
	 * 通过主键查询
	 */
	public Nlcsubject selectByPrimaryKey(Integer id);

	/**
	 * 修改专题
	 */
	public void updateSubject(Map<String, String[]> parameterMap) throws Exception;

	/**
	 * 通过subjectid查询
	 */
	public Nlcsubject selectBySubjectid(String subjectid);

	public boolean isExistCollect(String id, String loginAccount);

	public void addSubjectCollect(String id, String loginAccount);

	public void cancelCollect(String id, String loginAccount);

	public List<NlcsubjectExt> getUserCollect(String loginAccount);
	
	public List<NlcsubjectExt> getUserPraise(String loginAccount);
	
	public void clearPraise(String loginAccount);

	public boolean isExistPraise(String id, String loginAccount);

	public void addSubjectPraise(String id, String loginAccount);

	public void cancleSubjectPraise(String id, String loginAccount);

	public void updatePraiseCount(String subjectId);

	public List<Nlcsubject> seaSubjectList(Integer page, Integer rows, String keyword);

	public List<Wjreader> seaWenJinList(Integer page, Integer rows, String keyword);
	
	/**
	 * 特色专题上下架
	 */
	public void shelfAds(Integer id);
	
	public List<Nlcsubject> getIndexSubject();
	
	public int getPraiseCount(String subjectId);
	
	/**
	 * 获取全部的专题
	 */
	public List<Nlcsubject> getAllList();
	
	/**
	 * 专题排序的保存
	 * @param id
	 * @param sort
	 */
	public void sortSubject(Integer id, Integer sort);
	
	public JSONObject pull();
}
