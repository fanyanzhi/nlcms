package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.NlcnewsExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

public interface NlcnewsService {

	/**
	 * 新闻管理展示的分页查询
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getnewsList(int page, int rows, NlcnewsExt nlcnewsExt, String sort, String order);
	
	/**
	 * 新闻管理展示的分页查询
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getnewsList(int page, int rows, Integer id);

	/**
	 * 删除新闻信息，单个删除
	 */
	public int deleteSingleById(Integer id);

	/**
	 * 查询所有的日志信息
	 */
	public List<Nlcnews> getAll();

	/**
	 * 添加新闻
	 * 
	 * @param nlcnews
	 * @return
	 */
	public void insertNews(Nlcnews nlcnews);

	/**
	 * 通过主键查找新闻
	 */
	public Nlcnews selectByPrimaryKey(Integer id);

	/**
	 * 修改新闻
	 */
	public void updateNews(Map<String, String[]> parameterMap) throws Exception;

	public Nlcnews getNlcnewsByNewsId(String newsId);

	public List<Nlcnews> getRelatedNews(String id);

	public void addNewsCollect(String id, String loginAccount);

	public void cancelNewsCollect(String id, String loginAccount);

	public List<NlcnewsExt> getUserCollect(String loginAccount);
	
	public List<NlcnewsExt> getUserPraise(String loginAccount);
	
	public void clearPraise(String loginAccount);

	public boolean isExistCollect(String id, String loginAccount);

	public boolean isExistPraise(String id, String loginAccount);
	
	public void addNewsPraise(String id, String loginAccount);
	
	public void cancleNewsPraise(String id, String loginAccount);
	
	public void updatePraiseCount(String newsId);
	
	public List<Nlcnews> seaNewsList(int page, int rows, String keyword);
	
	public Map<String,Object> getIndexNews();
	
	/**
	 * 新闻发布与取消
	 */
	public void publish(Integer id, String status);
	
	/**
	 * 新闻管理页面分享页面的分页查询
	 */
	public EasyUiDataGridResult getShareNewsList(Integer id, int rows);
	
	/**
	 *app新闻页面
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getnewsList(int page, int rows);
	
	/**
	 * 置顶新闻
	 */
	public void settop(Integer id);
	
	/**
	 * 取消置顶新闻
	 */
	public void cantop(Integer id);
	
	public JSONObject pull();
}
