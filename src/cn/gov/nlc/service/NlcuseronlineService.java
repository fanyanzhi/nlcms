package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Nlcuseronline;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface NlcuseronlineService {
	/**
	 * 添加在线用户
	 * 
	 * @param nlcuseronline
	 */
	public void addNlcuseronline(Nlcuseronline nlcuseronline);

	/**
	 * 判斷用戶是否存在
	 * 
	 * @param loginAccount
	 * @return
	 */
	public boolean isExistUserOnline(String loginAccount, String clientId);

	/**
	 * 更新在线用户信息
	 * 
	 * @param nlcuseronline
	 */
	public void updateNlcuseronline(Nlcuseronline nlcuseronline);

	/**
	 * 获取登录账号
	 * 
	 * @param userToken
	 * @return
	 */
	public String[] getLoginaccount(String userToken);

	public void deleteOnlineUser(String loginAccount, String clientid);

	/**
	 * 在线用户的分页list
	 */
	public EasyUiDataGridResult getNlcuseronlineList(int page, int rows);
	
	/**
	 * 在线用户的所有数据
	 */
	public List<Nlcuseronline> getAll();
}
