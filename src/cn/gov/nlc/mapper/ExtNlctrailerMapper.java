package cn.gov.nlc.mapper;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.NlctrailerExt;

public interface ExtNlctrailerMapper {
	public List<NlctrailerExt> getMyCollect(String loginAccount);
	
	public void updatePraiseCount(String trailerId);
	
	public List<Nlctrailer> seaTrailerList(Map<String,Object> paramMap);
	
	public List<NlctrailerExt> getMyPraise(String loginAccount);
	
	public Map<String,Object> getIndexTrailer();
	
	public void updateCollectCount(String trailerid);
	
	public void updateCollectCountDecrease(List<String> list);
}
