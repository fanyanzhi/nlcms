package cn.gov.nlc.service;

import java.util.List;
import java.util.Map;
import cn.gov.nlc.pojo.Faq;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface FaqService {
	
	public EasyUiDataGridResult getList(int page, int rows, Faq faq);
	
	public void insert(Faq faq);
	
	public void publish(Integer id, String status);
	
	public int deleteSingleById(Integer id);
	
	public Faq selectByPrimaryKey(Integer id);
	
	public void updateFaq(Map<String, String[]> parameterMap) throws Exception;
	
	public void sortFaq(Integer id, Integer cseq);
	
	public void updateObj(Map<String, String[]> parameterMap) throws Exception;
	
	public List<Faq> getAllList();
}
