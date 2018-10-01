package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.NlcnewscollectExample;
import cn.gov.nlc.pojo.NlcnoticeVo;
import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.NlctrailerExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

public interface NlctrailerService {

	/**
	 * 讲座分页展示
	 * 
	 * @param page
	 * @param rows
	 * @param nlctrailerExt
	 * @return
	 */
	public EasyUiDataGridResult getTrailerList(Integer page, Integer rows, NlctrailerExt nlctrailerExt, String sort, String order);

	/**
	 * 删除讲座，单个删除
	 */
	public int deleteSingleById(Integer id);

	/**
	 * 查询所有讲座信息
	 */
	public List<Nlctrailer> getAll();

	/**
	 * 添加讲座信息
	 */
	public void insertNlctrailer(Nlctrailer nlctrailer);

	/**
	 * 通过主键查询
	 */
	public Nlctrailer selectByPrimaryKey(Integer id);

	/**
	 * 修改讲座
	 */
	public void updateTrailer(Map<String, String[]> parameterMap, String username) throws Exception;

	public Nlctrailer selectByTrailerId(String trailerId);
	
	public Nlctrailer selectByTrailerTitle(String title);

	public EasyUiDataGridResult getAppTrailer(Integer page, Integer rows, String guanqu, Integer days);

	public boolean isExistCollect(String id, String loginAccount);
	
	public void addTrailerCollect(String id,String loginAccount);
	
	public void cancelCollect(String id, String loginAccount);
	
	public List<NlctrailerExt> getUserCollect(String loginAccount);
	
	public List<NlctrailerExt> getUserPraise(String loginAccount);
	
	public void clearPraise(String loginAccount);

	public boolean isExistPraise(String id, String loginAccount);
	
	public void addTrailerPraise(String id, String loginAccount);
	
	public void cancleTrailerPraise(String id, String loginAccount);
	
	public void updatePraiseCount(String newsId);
	
	public List<Nlctrailer> seaTrailerList(Integer page, Integer rows, String keyword);
	
	public Map<String,Object> getIndexTrailer();
	
	/**
	 * 讲座发布与取消
	 */
	public void publish(Integer id, String status, String username);

	public JSONObject pull();
}
