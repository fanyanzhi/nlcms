package cn.gov.nlc.mapper;

import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.NlcsubjectExt;

public interface ExtNlcsubjectMapper {
	public List<NlcsubjectExt> getMyCollect(String loginAccount);

	public void updatePraiseCount(String subjectId);

	public List<Nlcsubject> seaSubjectList(Map<String, Object> paramMap);
	
	public List<NlcsubjectExt> getMyPraise(String loginAccount);
	
	public List<Nlcsubject> getIndexSubject(int num);
	
	public void updateCollectCount(String subjectid);
	
	public void updateCollectCountDecrease(List<String> list);

}
