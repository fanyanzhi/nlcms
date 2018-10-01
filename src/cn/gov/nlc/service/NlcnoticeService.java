package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.NlcnewsExt;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.NlcnoticeExt;
import cn.gov.nlc.pojo.NlcnoticeVo;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

public interface NlcnoticeService {

	/**
	 * 公告展示的分页查询
	 * @param page
	 * @param rows
	 * @param nlcnoticeExt
	 * @return
	 */
	public EasyUiDataGridResult getNoticeList(Integer page, Integer rows, NlcnoticeExt nlcnoticeExt);
	
	/**
	 * 公告分页查询
	 */
	public EasyUiDataGridResult getNoticeList(Integer page, Integer rows);

	/**
	 * 删除公告，单个删除
	 */
	public int deleteSingleById(String id);
	
	/**
	 * 查询所有的公告
	 */
	public List<Nlcnotice> getAll();
	
	/**
	 * 添加公告
	 */
	public void insertNotice(Nlcnotice nlcnotice);
	
	/**
	 * 通过主键查询
	 */
	public Nlcnotice selectByPrimaryKey(String id);
	
	/**
	 * 修改公告
	 */
	public void updateNotice(Map<String, String[]> parameterMap) throws Exception;

	public NlcnoticeVo selectByNoticeId(String noticeId);
	
	public boolean isExistCollect(String id, String loginAccount);
	
	public void addNoticeCollect(String id, String loginAccount);
	
	public void cancelNoticeCollect(String id, String loginAccount);
	
	public List<NlcnoticeExt> getUserCollect(String loginAccount);
	
	public boolean isExistPraise(String id, String loginAccount);
	
	public void addNoticePraise(String id, String loginAccount);
	
	public void cancleNewsPraise(String id, String loginAccount);
	
	public List<NlcnoticeExt> getUserPraise(String loginAccount);
	
	public void clearPraise(String loginAccount);
	
	public void updatePraiseCount(String newsId);
	
	public List<Nlcnotice> seaNoticeList(Integer page, Integer rows, String keyword);
	
	public Map<String,Object> getIndexNotice();
	
	/**
	 * 公告发布与取消
	 */
	public void publish(String noticeid, String status);

	/**
	 * 公告分享页面的分页查询
	 */
	public EasyUiDataGridResult getShareNoticeList(String noticeid, int rows);
	
	public int getNlcnoticeByNoticeId(String nlcnoticeId);
	
	/**
	 * app公告
	 */
	public EasyUiDataGridResult getAppNoticeList(Integer page, Integer rows);
	
	/**
	 * 置顶公告
	 */
	public void settop(String noticeid);
	
	/**
	 * 取消置顶公告
	 */
	public void cantop(String noticeid);
	
	public JSONObject pull();
}
