package cn.gov.nlc.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.NlcnoticeExt;

public interface ExtNlcnoticeMapper {
	public List<NlcnoticeExt> getMyCollect(String loginAccount);
	
	public void updatePraiseCount(String noticeId);
	
	public List<Nlcnotice> seaNoticeList(Map<String,Object> paramMap);
	
	public List<NlcnoticeExt> getMyPraise(String loginAccount);
	
	/**
	 * 得到新闻分享页面的list
	 * @param loginAccount
	 * @return
	 */
	public List<Nlcnotice> getShareNoticeList(@Param("noticeid") String noticeid, @Param("rows") int rows);
	
	public Map<String, Object> getIndexNotice();
	public Map<String, Object> getIndex2Notice();
	
	public void updateCollectCount(String noticeid);
	public void updateCollectCountDecrease(List<String> list);
	
}
