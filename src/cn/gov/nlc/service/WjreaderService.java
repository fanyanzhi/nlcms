package cn.gov.nlc.service;

import java.util.Date;
import java.util.Map;

import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.pojo.WjreaderExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

public interface WjreaderService {
	public void insertWjreader(Wjreader wjreader);
	
	public Wjreader getWjreaderToday();
	
	public Wjreader getWjreaderYestoday();
	
	public Wjreader getWjreader(String date);
	
	/**
	 * 通过wjdate获取
	 * @param wjdate
	 * @return
	 */
	public Wjreader getWjreaderBywjdate(Date wjdate);
	
	public EasyUiDataGridResult getWjreaderList(int page, int rows, WjreaderExt wjreaderExt);

	public void publish(Integer id, String status);

	public int deleteSingleById(Integer id);

	public JSONObject pull();
	
	public boolean JudgeExistToday();
	
	public Wjreader selectByPrimaryKey(Integer id);

	public void updateObj(Map<String, String[]> parameterMap) throws Exception;
}
