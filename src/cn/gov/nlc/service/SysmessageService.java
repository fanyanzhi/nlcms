package cn.gov.nlc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.pojo.SysmessageExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface SysmessageService {
	
	
	public void insertSysmessage(int type, String message,String loginAccount);

	public int getSysmessageCountByType(int type, String clientid);
	
	public int getSysmessageCountByTypeAndAccount(int type, String loginAccount, String clientid);

	public List<Sysmessage> getSysmessage(int type, String clientid);
	
	public List<Sysmessage> getSysmessageByAccount(int type, String loginAccount, String clientid);

	/**
	 * 站内信展示的list
	 * @param page
	 * @param rows
	 * @param sysmessage
	 * @return
	 */
	public EasyUiDataGridResult getmessageList(Integer page, Integer rows, SysmessageExt sysmessageExt);

	/**
	 * 通过主键删除站内信
	 * @param id
	 * @return
	 */
	public int deleteSingleById(Integer id);
	
	/**
	 * 在后台插入站内信，类型都是0
	 */
	public void insertMessage(Sysmessage sysmessage);
	
	/**
	 * 通过主键查询
	 */
	public Sysmessage selectByPrimaryKey(Integer id);
	
	/**
	 * 修改站内信
	 */
	public void updateMessage(Map<String, String[]> parameterMap, String pubname) throws Exception;
	
	/**
	 * 通过4大类插入站内信(在4大类添加的时候)
	 */
	public void insertMessageThfour(String pushmethod, Byte type, String message, String fid, Date time, String pubname, String title, String sort);
	
	public void insertSysmessageObject(Sysmessage sysmessage);
}
