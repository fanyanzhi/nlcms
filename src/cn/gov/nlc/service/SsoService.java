package cn.gov.nlc.service;

import javax.servlet.http.HttpSession;

import cn.gov.nlc.aleph.Json;
import cn.gov.nlc.pojo.Nlcuser;
import net.sf.json.JSONObject;

public interface SsoService {

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param baseos
	 * @param clientid
	 * @param ip
	 * @param location
	 * @param borId
	 * @return
	 */
	public JSONObject login(String username, String password, String baseos, String clientid, String ip, String location, String borId);
	
	/**
	 * 注册
	 * @param Nlcuser
	 * @return
	 */
	public Json register(Nlcuser nlcuser);
	
	/**
	 * 修改用户信息
	 * @param nlcuser
	 * @return
	 */
	public Json modifyUser(Nlcuser nlcuser);
	
	/**
	 * 修改读者账户密码
	 * @param loginAccount
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public JSONObject upPassword(String loginAccount, String oldPassword, String newPassword);
	
	/**
	 * share下的登录
	 * @param username
	 * @param password
	 * @return
	 */
	public String shareLogin(String username, String password);

	/**
	 * 实名认证接口
	 * @param loginAccount
	 * @param cn
	 * @param idno
	 * @return
	 */
	public JSONObject authenticateRealname(String loginAccount, String cn, String idno);
	
	/**
	 * 发送验证码
	 * @return
	 */
	public JSONObject sendVerifyCode(String phone, HttpSession session);
	
	/**
	 * 发送验证码
	 * @return
	 */
	public JSONObject sendVerifyCodeNew(String phone);
	
	/**
	 * 快速注册
	 * @param Nlcuser
	 * @return
	 */
	public Json fastRegister(String phone, String code, String password);

	/**
	 * 实名注册
	 * @param phone
	 * @param code
	 * @param password
	 * @param cn
	 * @param idno
	 * @return
	 */
	public Json realnameRegister(String phone, String code, String password, String cn, String idno);
	
	/**
	 * 第三方登录的虚拟用户升级成实名用户
	 * @param loginAccount
	 * @param cn
	 * @param idno
	 * @return
	 */
	public JSONObject thirdUpgradeReal(String loginAccount, String cn, String idno);
	
	/**
	 * 第三方登录的日志接口
	 * @return
	 */
	public JSONObject thirdLoginLog(String openid, String method);
	
	/**
	 * 获取sso的accesstoken
	 * @param username
	 * @param password
	 * @return
	 */
	public JSONObject getSsoAccessToken(String username, String password);
}
