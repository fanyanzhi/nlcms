package cn.gov.nlc.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.NlcnewsExt;

public interface ExtNlcnewsMapper {

	public List<Nlcnews> getRelatedNews(String newsid);

	public List<NlcnewsExt> getMyCollect(String loginAccount);
	
	public void updatePraiseCount(String newsId);
	
	public void updateCollectCount(String newsId);
	
	public void updateCollectCountDecrease(List<String> list);
	
	public List<Nlcnews> seaNewsList(Map<String,Object> paramMap);
	
	public List<NlcnewsExt> getMyPraise(String loginAccount);
	
	/**
	 * 得到新闻分享页面的list
	 * @param loginAccount
	 * @return
	 */
	public List<NlcnewsExt> getShareNewsList(@Param("id") Integer id, @Param("rows") int rows);
	
	public Map<String,Object> getIndexNews();
	public Map<String,Object> getIndex2News();
	
}
