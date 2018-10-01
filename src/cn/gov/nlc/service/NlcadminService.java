package cn.gov.nlc.service;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface NlcadminService {

	/**
	 * 通过用户名查询用户信息
	 * @param username
	 * @return
	 */
	public Nlcadmin GetAdminByName(String username);
	
	/**
	 * 管理员列表内容的分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getAdminList(int page, int rows, Nlcadmin nlcadmin);
	
	/**
	 * 删除管理员信息，单个删除
	 */
	public int deleteSingleById(Integer id);
	
	/**
	 * 通过主键查询管理员信息
	 * @param id
	 * @return
	 */
	public Nlcadmin selectByPrimaryKey(Integer id);
	
	/**
	 * 更新管理员信息
	 */
	public void updateAdmin(Nlcadmin nlcadmin);
	
	/**
	 * 修改密码
	 */
	public void changePassword(Nlcadmin nlcadmin);
	
	/**
	 * 添加管理员
	 */
	public void insertAdmin(Nlcadmin nlcadmin);
	
	public void ttt(Nlcadmin nlcadmin);
}
