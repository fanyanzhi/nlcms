package cn.gov.nlc.service;

import java.util.Date;
import java.util.List;
import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.pojo.Nlcuserloginlog;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface NlcuserService {

	public void accountLogin(String userName, String password, String ip);

	public boolean isExistNlcuser(String loginAccount);

	public void insertNlcuser(Nlcuser nlcuser);

	public void updateNlcuser(Nlcuser nlcuser);

	/**
	 * 用户展示页面，包括高级查询
	 */
	public EasyUiDataGridResult getUserList(Integer page, Integer rows, Nlcuser nlcuser, Date pstartDate, Date pendDate);

	/**
	 * 根据id单个删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteSingleById(Integer id);

	/**
	 * 查询所有nlcuser
	 */
	public List<Nlcuser> getAll();

	/*	*//**
			 * 添加在线用户
			 * 
			 * @param nlcuseronline
			 *//*
			 * public void addNlcuseronline(Nlcuseronline nlcuseronline);
			 * 
			 * public boolean isExistUserOnline(String loginAccount);
			 * 
			 * public void updateNlcuseronline(Nlcuseronline nlcuseronline);
			 */

	/**
	 * 
	 */
	public void addNlcuserloginlog(Nlcuserloginlog nlcuserloginlog);

	public String CreateUserToken(String UserName);

	/**
	 * 根据id得到user
	 */
	public Nlcuser getById(Integer id);
	
	public Nlcuser getByAccount(String loginAccount);

	public List<String> getCardUser();
	
	public void updateByPrimaryKey(Nlcuser nlcuser);
	
	public boolean checkPhone(String phone);
	
}
