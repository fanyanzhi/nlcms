package cn.gov.nlc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gov.nlc.aleph.Json;
import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.pojo.Nlcuseronline;
import cn.gov.nlc.service.NlcuserService;
import cn.gov.nlc.service.NlcuseronlineService;
import cn.gov.nlc.service.SsoService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.MD5Util;
import cn.gov.nlc.util.PropertiesUtils;
import net.sf.json.JSONObject;

@Service
public class SsoServiceImpl implements SsoService {

	private static final String newssourl = PropertiesUtils.getPropertyValue("newssourl");
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.SsoServiceImpl.class);

	@Autowired
	private NlcuserService nlcuserService;

	@Autowired
	private NlcuseronlineService nlcuseronlineService;

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @param baseos
	 * @param clientid
	 * @param ip
	 * @param location
	 * @param borId
	 * @return
	 */
	@Override
	public JSONObject login(String username, String password, String baseos, String clientid, String ip,
			String location, String borId) {
		JSONObject resJson = new JSONObject();
		// 通过登录账号、密码获取access_token
		String uidone = username;
		String userPasswordone = MD5Util.md5(password);
		String appIDone = "90004";
		String urlone = newssourl + "/sso/foreign/authenManager/getAccessToken?uid=" + uidone + "&userPassword="
				+ userPasswordone + "&appId=" + appIDone;
		logger.info("登录第二步的url" + urlone);
		String resone = Common.sendGet(urlone, null);
		logger.info("【获取accesstoken的】 " + resone);
		if (StringUtils.isBlank(resone)) {
			resJson.put("result", 402);
			resJson.put("msg", "获取atoken的sso服务器连接失败");
			return resJson;
		}

		com.alibaba.fastjson.JSONObject jsonObjectone = com.alibaba.fastjson.JSONObject.parseObject(resone);
		String code = jsonObjectone.getString("code");
		if ("I0000000".equals(code)) { // 认证成功
			com.alibaba.fastjson.JSONObject result = jsonObjectone.getJSONObject("result");
			String access_token = result.getString("accessToken"); // 得到令牌
			String urltwo = newssourl + "/sso/foreign/authenManager/getUserInfoByToken?access_token=" + access_token;
			String restwo = Common.sendGet(urltwo, null);
			logger.info("【获取用户信息的】 " + restwo);
			if (StringUtils.isBlank(restwo)) {
				resJson.put("result", 402);
				resJson.put("msg", "获取用户信息sso服务器连接失败");
				return resJson;
			}

			com.alibaba.fastjson.JSONObject jsonObjecttwo = com.alibaba.fastjson.JSONObject.parseObject(restwo);
			String codetwo = jsonObjecttwo.getString("code");
			if ("I0000000".equals(codetwo)) { // 成功取得用户信息
				com.alibaba.fastjson.JSONObject userobject = jsonObjecttwo.getJSONObject("result");
				String uid = userobject.getString("uid"); // 登录账号
				// 01标识虚拟用户、02标识实名用户、03标识读者卡用户
				String rdrolecode = userobject.getString("category");
				if("03".equals(rdrolecode)) {
					String rcardid = userobject.getString("rcardid");
					uid = rcardid;
				}
				
				String strToken = nlcuserService.CreateUserToken(uid); // token

				// 添加在线记录
				Nlcuseronline nlcuseronline = new Nlcuseronline();
				nlcuseronline.setLoginaccount(uid);
				nlcuseronline.setBorid(borId);
				nlcuseronline.setToken(strToken);
				nlcuseronline.setTime(new Date());
				nlcuseronline.setClientid(clientid);
				nlcuseronline.setAddress(ip);
				nlcuseronline.setLocation(location);
				if (!nlcuseronlineService.isExistUserOnline(uid, clientid)) {
					nlcuseronlineService.addNlcuseronline(nlcuseronline);
				} else {
					nlcuseronlineService.updateNlcuseronline(nlcuseronline);
				}

				Nlcuser nlcuser = new Nlcuser();
				nlcuser.setLoginaccount(uid);

				String name = userobject.getString("cn");
				String sextype = userobject.getString("sex");
				String cardtype = userobject.getString("idtype");
				String cardno = userobject.getString("idno");
				String country = userobject.getString("country");
				// String province = userobject.getString("province");
				// String city = userobject.getString("city");
				String education = userobject.getString("education");
				String technical = userobject.getString("titleAttr");
				String birthday = userobject.getString("birthday");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				if (StringUtils.isNotBlank(birthday)) {
					try {
						Date birth = format.parse(birthday);
						nlcuser.setBirthday(birth);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				String email = userobject.getString("mail");
				String telephone = userobject.getString("fixphone");
				String mobile = userobject.getString("mobile");
				String fax = userobject.getString("faxno");
				String address = userobject.getString("street");
				String postcode = userobject.getString("postalCode");
				

				// roamflag漫游的、rdtypecode、status这三个没有数据
				nlcuser.setName(name);
				nlcuser.setSextype(sextype);
				nlcuser.setCardtype(cardtype);
				nlcuser.setCardno(cardno);
				nlcuser.setCountry(country);
				nlcuser.setEducation(education);
				nlcuser.setTechnical(technical);
				nlcuser.setEmail(email);
				nlcuser.setTelephone(telephone);
				nlcuser.setMobile(mobile);
				nlcuser.setFax(fax);
				nlcuser.setAddress(address);
				nlcuser.setPostcode(postcode);

				// 虚拟、0000
				// 实名、JS0001
				// 物理卡JS0002

				if ("01".equals(rdrolecode)) {
					nlcuser.setRdrolecode("0000");
				} else if ("02".equals(rdrolecode)) {
					nlcuser.setRdrolecode("JS0001");
				} else if ("03".equals(rdrolecode)) {
					nlcuser.setRdrolecode("JS0002");
				}

				if (!nlcuserService.isExistNlcuser(uid)) {
					try {
						String[] strArr = Common.getTwoAddressByIP(ip);
						if(StringUtils.isNotBlank(strArr[0])) {
							nlcuser.setProvince(strArr[0]);
						}else {
							nlcuser.setProvince(null);
						}
						
						if(StringUtils.isNotBlank(strArr[1])) {
							nlcuser.setCity(strArr[1]);
						}else {
							nlcuser.setCity(null);
						}
						
						nlcuser.setBaseos(baseos);
						nlcuser.setInserttime(new Date());
						nlcuser.setAliasstatus("1");
						nlcuserService.insertNlcuser(nlcuser);
					} catch (Exception e) {
						logger.error("nlcuser插入失败"+e.getMessage(), e);
					}
				} else {
					nlcuser.setUpdatetime(new Date());
					nlcuserService.updateNlcuser(nlcuser);
				}

				Nlcuser dbNlcuser = nlcuserService.getByAccount(uid);
				resJson.put("token", strToken);
				resJson.put("email", email);
				resJson.put("cardno", cardno);
				resJson.put("mobile", mobile);
				resJson.put("cardtype", cardtype);
				resJson.put("result", 200);
				resJson.put("account", uid);
				resJson.put("commName", name);
				resJson.put("role", nlcuser.getRdrolecode());
				resJson.put("aliasstatus", dbNlcuser.getAliasstatus());
				return resJson;
			} else {
				String msg = jsonObjecttwo.getString("msg"); // 取得用户信息失败
				resJson.put("result", 402);
				resJson.put("msg", msg);
				return resJson;
			}

		} else {
			String msg = jsonObjectone.getString("msg"); // 认证失败
			resJson.put("result", 402);
			resJson.put("msg", msg);
			return resJson;
		}
	}

	/**
	 * 注册
	 */
	@Override
	public Json register(Nlcuser nlcuser) {
		Json j = new Json();
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();

		// 身份证号校验
		String identityreg = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		Pattern idpattern = Pattern.compile(identityreg);

		String idnor = nlcuser.getCardno();
		if (StringUtils.isNotBlank(idnor)) {
			Matcher matcher = idpattern.matcher(idnor);
			boolean b = matcher.matches();
			if (!b) {
				j.setSuccess(false);
				j.setMsg("身份证格式错误");
				return j;
			}

			jsonparam.put("idno", idnor);
			jsonparam.put("idtype", "01");
		}

		String uid = nlcuser.getLoginaccount();
		String userPassword = nlcuser.getPassword();
		String cn = nlcuser.getName();
		String sex = nlcuser.getSextype();
		Date birthdaydate = nlcuser.getBirthday();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String birthday = "";
		if (null != birthdaydate) {
			try {
				birthday = format.format(birthdaydate);
			} catch (Exception e) {
			}
		}
		String country = nlcuser.getCountry();
		String province = nlcuser.getProvince();
		String city = nlcuser.getCity();
		String postalAddress = nlcuser.getAddress();
		String education = nlcuser.getEducation();
		String titleAttr = nlcuser.getTechnical();
		String fixphone = nlcuser.getTelephone();
		String street = nlcuser.getAddress();
		String postalCode = nlcuser.getPostcode();
		String faxno = nlcuser.getFax();
		String mobile = nlcuser.getMobile();
		String mail = nlcuser.getEmail();
		String nation = "";

		jsonparam.put("uid", uid);
		jsonparam.put("userPassword", userPassword);
		jsonparam.put("cn", cn);
		jsonparam.put("sex", sex);
		jsonparam.put("birthday", birthday);
		jsonparam.put("country", country);
		jsonparam.put("province", province);
		jsonparam.put("city", city);
		jsonparam.put("postalAddress", postalAddress);
		jsonparam.put("education", education);
		jsonparam.put("titleAttr", titleAttr);
		jsonparam.put("fixphone", fixphone);
		jsonparam.put("street", street);
		jsonparam.put("postalCode", postalCode);
		jsonparam.put("faxno", faxno);
		jsonparam.put("mobile", mobile);
		jsonparam.put("mail", mail);
		jsonparam.put("nation", nation);

		String url = newssourl + "/sso/foreign/userManager/addUser";
		String resstr = Common.sendPostForm(url, jsonparam);
		logger.info("【注册返回的信息】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			j.setSuccess(false);
			j.setMsg("sso服务器连接失败");
			return j;
		}

		com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resstr);
		String code = jsonObjectrt.getString("code");
		String result = jsonObjectrt.getString("result");
		String msg = jsonObjectrt.getString("msg");
		if ("I0000000".equals(code)) { // 标识成功
			if ("1".equals(result)) {
				j.setSuccess(true);
				j.setMsg("注册成功");
			} else {
				j.setSuccess(false);
				j.setMsg("注册失败");
			}
		} else {
			if ("-1".equals(result)) {
				j.setSuccess(false);
				j.setMsg("用户已存在");
			} else if ("-2".equals(result)) {
				j.setSuccess(false);
				j.setMsg("用户属性不能为空");
			} else if ("-3".equals(result)) {
				j.setSuccess(false);
				j.setMsg("手机号码已存在");
			} else if ("-4".equals(result)) {
				j.setSuccess(false);
				j.setMsg("邮箱已存在");
			} else {
				j.setSuccess(false);
				j.setMsg(msg);
			}
		}

		return j;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param nlcuser
	 * @return
	 */
	@Override
	public Json modifyUser(Nlcuser nlcuser) {
		Json j = new Json();
		String uid = nlcuser.getLoginaccount();
		String cn = nlcuser.getName();
		String password = nlcuser.getPassword();
		String userPassword = MD5Util.md5(password);

		String country = nlcuser.getCountry();
		String province = nlcuser.getProvince();
		String city = nlcuser.getCity();
		String education = nlcuser.getEducation();
		String titleAttr = nlcuser.getTechnical();
		Date birthdaydate = nlcuser.getBirthday();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String birthday = "";
		if (null != birthdaydate) {
			try {
				birthday = format.format(birthdaydate);
			} catch (Exception e) {
			}
		}
		String mail = nlcuser.getEmail();
		String fixphone = nlcuser.getTelephone();
		String mobile = nlcuser.getMobile();
		String faxno = nlcuser.getFax();
		String street = nlcuser.getAddress();
		String postalCode = nlcuser.getPostcode();
		String sex = nlcuser.getSextype();

		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
		jsonparam.put("uid", uid);
		jsonparam.put("userPassword", userPassword);
		jsonparam.put("cn", cn);
		jsonparam.put("birthday", birthday);
		jsonparam.put("country", country);
		jsonparam.put("province", province);
		jsonparam.put("city", city);
		jsonparam.put("education", education);
		jsonparam.put("titleAttr", titleAttr);
		jsonparam.put("fixphone", fixphone);
		jsonparam.put("street", street);
		jsonparam.put("postalCode", postalCode);
		jsonparam.put("faxno", faxno);
		jsonparam.put("mobile", mobile);
		jsonparam.put("mail", mail);
		jsonparam.put("sex", sex);

		String url = newssourl + "/sso/foreign/userManager/modifyUser";
		String resstr = Common.sendPostForm(url, jsonparam);
		logger.info("【修改用户的信息】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			j.setSuccess(false);
			j.setMsg("sso服务器连接失败");
			return j;
		}

		com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resstr);
		String code = jsonObjectrt.getString("code");
		String result = jsonObjectrt.getString("result");
		String msg = jsonObjectrt.getString("msg");
		if ("I0000000".equals(code)) { // 标识成功
			if ("1".equals(result)) {
				j.setSuccess(true);
				j.setMsg("修改成功");
			} else {
				j.setSuccess(false);
				j.setMsg("修改失败");
			}
		} else {
			if ("-1".equals(result)) {
				j.setSuccess(false);
				j.setMsg("用户不存在");
			} else if ("-2".equals(result)) {
				j.setSuccess(false);
				j.setMsg("用户属性不能为空");
			} else if ("-3".equals(result)) {
				j.setSuccess(false);
				j.setMsg("手机号码已存在");
			} else if ("-4".equals(result)) {
				j.setSuccess(false);
				j.setMsg("邮箱已存在");
			} else {
				j.setSuccess(false);
				j.setMsg(msg);
			}
		}

		return j;
	}

	/**
	 * 修改读者账户密码
	 * 
	 * @param loginAccount
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@Override
	public JSONObject upPassword(String loginAccount, String oldPassword, String newPassword) {
		JSONObject resJson = new JSONObject();
		String oldPasswordMd5 = MD5Util.md5(oldPassword);
		String appID = "90004";

		String urlone = newssourl + "/sso/foreign/userAccountManager/updatePassword?uid=" + loginAccount
				+ "&oldPassword=" + oldPasswordMd5 + "&newPassword=" + newPassword + "&appId=" + appID;
		String resstr = Common.sendGet(urlone, null);
		logger.info("【修改读者账户密码的返回数据：】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			resJson.put("result", false);
			resJson.put("message", "sso服务器连接失败");
			return resJson;
		}

		com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resstr);
		String code = jsonObjectrt.getString("code");
		String message = jsonObjectrt.getString("msg");
		if ("I0000000".equals(code)) { // 标识成功
			resJson.put("result", true);
			resJson.put("message", "修改成功");
		} else {
			resJson.put("result", false);
			resJson.put("message", message);
		}

		return resJson;
	}

	/**
	 * share下的登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public String shareLogin(String username, String password) {
		// 通过登录账号、密码获取access_token
		String uidone = username;
		String userPasswordone = MD5Util.md5(password);
		String appIDone = "90004";
		String urlone = newssourl + "/sso/foreign/authenManager/getAccessToken?uid=" + uidone + "&userPassword="
				+ userPasswordone + "&appId=" + appIDone;
		String resone = Common.sendGet(urlone, null);
		logger.info("【获取accesstoken的】 " + resone);
		if (StringUtils.isBlank(resone)) {
			return null;
		}
		com.alibaba.fastjson.JSONObject jsonObjectone = com.alibaba.fastjson.JSONObject.parseObject(resone);
		String code = jsonObjectone.getString("code");
		if ("I0000000".equals(code)) { // 认证成功
			com.alibaba.fastjson.JSONObject result = jsonObjectone.getJSONObject("result");
			String access_token = result.getString("accessToken"); // 得到令牌
			return access_token;
		}
		return null;
	}

	/**
	 * 实名认证接口
	 */
	@Override
	public JSONObject authenticateRealname(String loginAccount, String cn, String idno) {
		JSONObject resJson = new JSONObject();
		String urlone = newssourl + "/sso/foreign/userManager/checkIdcard?uid=" + loginAccount + "&cn=" + cn
				+ "&idtype=01&appId=90004&idno=" + idno;
		logger.info("【实名认证的URL】" + urlone);
		String resstr = Common.sendGet(urlone, null);
		logger.info("【实名认证的返回数据：】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			resJson.put("result", false);
			resJson.put("message", "sso服务器连接失败");
			return resJson;
		}

		com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resstr);
		String code = jsonObjectrt.getString("code");
		String message = jsonObjectrt.getString("msg");
		String result = jsonObjectrt.getString("result");
		if ("I0000000".equals(code)) { // 标识成功
			resJson.put("result", true);
			resJson.put("message", "修改成功");
		} else {
			if ("-1".equals(result)) {
				resJson.put("result", false);
				resJson.put("message", "认证失败");
			} else if ("-2".equals(result)) {
				resJson.put("result", false);
				resJson.put("message", "证件号码已存在");
			} else {
				resJson.put("result", false);
				resJson.put("message", message);
			}
		}

		return resJson;
	}

	/**
	 * 发送验证码
	 */
	@Override
	public JSONObject sendVerifyCode(String phone, HttpSession session) {
		JSONObject resJson = new JSONObject();
		
		String urlver = newssourl + "/sso/foreign/userManager/getUserInfoByUid?uid=" + phone;
		String resver = Common.sendGet(urlver, null);
		if(StringUtils.isNotBlank(resver)) {
			com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resver);
			String code = jsonObjectrt.getString("code");
			
			if ("I0000000".equals(code)) { // 用户存在
				resJson.put("result", false);
				resJson.put("message", "该手机号已经注册过！");
				return resJson;
			}
		}
		
		Object attr = session.getAttribute("codetime"); //上次发送的时间
		long oldtime = 0;
		if(null != attr) {
			oldtime = (long)attr;
		}
		long nowtime = System.currentTimeMillis();	//这次的时间
		if((nowtime - oldtime) < 60000) {
			resJson.put("result", false);
			resJson.put("message", "请60秒后再发送！");
			logger.info("【发送验证码60秒后再调用】"+phone);
			return resJson;
		}
		
		logger.info("【马上就调用发送验证码】"+phone);
		/*String urlone = newssourl + "/sso/foreign/userManager/sendVcode?appId=90004&state=S2FR7B31D7&mobile=" + phone;
		String resstr = Common.sendGet(urlone, null);
		logger.info("【发送验证码的返回数据：】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			resJson.put("result", false);
			resJson.put("message", "sso服务器连接失败");
			return resJson;
		}

		com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resstr);
		String code = jsonObjectrt.getString("code");
		String message = jsonObjectrt.getString("msg");

		if ("I0000000".equals(code)) { // 标识成功
			session.setAttribute("codetime", System.currentTimeMillis());
			resJson.put("result", true);
			resJson.put("message", "发送成功");
		} else {
			resJson.put("result", false);
			resJson.put("message", message);
		}*/
		
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
		String urlver2 = newssourl + "/sso/foreign/userManager/send-sms";
		jsonparam.put("mobile", phone);
		jsonparam.put("appId", "90004");
		jsonparam.put("state", "S2FR7B31D7");
		String resver2 = Common.sendPostFormSso(urlver2, jsonparam);
		if(StringUtils.isNotBlank(resver2)) {
			com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resver2);
			String code = jsonObjectrt.getString("code");
			String message = jsonObjectrt.getString("msg");
			
			if ("I0000000".equals(code)) { //发送成功
				session.setAttribute("codetime", System.currentTimeMillis());
				resJson.put("result", true);
				resJson.put("message", "发送成功");
			}else {
				resJson.put("result", false);
				resJson.put("message", message);
			}
			
		}else {
			resJson.put("result", false);
			resJson.put("message", "sso服务器连接失败");
		}
		return resJson;
	}
	
	/**
	 * 发送验证码，后面sso方面修改了接口
	 * @param phone
	 * @return
	 */
	public JSONObject sendVerifyCodeNew(String phone) {
		JSONObject resJson = new JSONObject();
		
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
		String urlver = newssourl + "/sso/foreign/userManager/send-sms";
		jsonparam.put("mobile", phone);
		jsonparam.put("appId", "90004");
		jsonparam.put("state", "S2FR7B31D7");
		String resver = Common.sendPostFormSso(urlver, jsonparam);
		if(StringUtils.isNotBlank(resver)) {
			com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resver);
			String code = jsonObjectrt.getString("code");
			String message = jsonObjectrt.getString("msg");
			
			if ("I0000000".equals(code)) { //发送成功
				resJson.put("result", true);
				resJson.put("message", "发送成功");
			}else {
				resJson.put("result", false);
				resJson.put("message", message);
			}
			
		}else {
			resJson.put("result", false);
			resJson.put("message", "sso服务器连接失败");
		}
		
		return resJson;
	}
	

	/**
	 * 快速注册
	 */
	@Override
	public Json fastRegister(String phone, String code, String password) {
		Json j = new Json();
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
		jsonparam.put("mobile", phone);
		jsonparam.put("userPassword", password);
		jsonparam.put("vcode", code);

		String url = newssourl + "/sso/foreign/userManager/celAddUser";
		String resstr = Common.sendPostForm(url, jsonparam);
		logger.info("【注册返回的信息】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			j.setSuccess(false);
			j.setMsg("sso服务器连接失败");
			return j;
		}

		com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resstr);
		String rescode = jsonObjectrt.getString("code");
		String result = jsonObjectrt.getString("result");
		String msg = jsonObjectrt.getString("msg");
		if ("I0000000".equals(rescode)) { // 标识成功
			if ("1".equals(result)) {
				j.setSuccess(true);
				j.setMsg("注册成功");
			} else {
				j.setSuccess(false);
				j.setMsg("注册失败");
			}
		} else {
			if ("-1".equals(result)) {
				j.setSuccess(false);
				j.setMsg("用户已存在");
			} else if ("-2".equals(result)) {
				j.setSuccess(false);
				j.setMsg("用户属性不能为空");
			} else if ("-3".equals(result)) {
				j.setSuccess(false);
				j.setMsg("手机号码已存在");
			} else if ("-4".equals(result)) {
				j.setSuccess(false);
				j.setMsg("邮箱已存在");
			} else {
				j.setSuccess(false);
				j.setMsg(msg);
			}
		}

		return j;
	}

	/**
	 * 实名注册
	 */
	@Override
	public Json realnameRegister(String phone, String code, String password, String cn, String idno) {
		Json j = new Json();
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();

		// 身份证号校验
		String identityreg = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		Pattern idpattern = Pattern.compile(identityreg);
		Matcher matcher = idpattern.matcher(idno);
		boolean b = matcher.matches();
		if (!b) {
			j.setSuccess(false);
			j.setMsg("身份证格式错误");
			return j;
		}

		jsonparam.put("cn", cn);
		jsonparam.put("idno", idno);
		jsonparam.put("idtype", "01");
		jsonparam.put("mobile", phone);
		jsonparam.put("userPassword", password);
		jsonparam.put("vcode", code);
		
		String url = newssourl + "/sso/foreign/userManager/mobileAppAddUser";
		String resstr = Common.sendPostForm(url, jsonparam);
		logger.info("【注册返回的信息】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			j.setSuccess(false);
			j.setMsg("sso服务器连接失败");
			return j;
		}

		com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resstr);
		String rescode = jsonObjectrt.getString("code");
		String result = jsonObjectrt.getString("result");
		String msg = jsonObjectrt.getString("msg");
		if ("I0000000".equals(rescode)) { // 标识成功
			if ("1".equals(result)) {
				j.setSuccess(true);
				j.setMsg("注册成功");
			} else {
				j.setSuccess(false);
				j.setMsg("注册失败");
			}
		} else {
			if ("-1".equals(result)) {
				j.setSuccess(false);
				j.setMsg("用户已存在");
			} else if ("-2".equals(result)) {
				j.setSuccess(false);
				j.setMsg("参数不能为空");
			} else if ("-3".equals(result)) {
				j.setSuccess(false);
				j.setMsg("手机号码已存在");
			} else if ("-6".equals(result)) {
				j.setSuccess(false);
				j.setMsg("证件号码已存在");
			} else {
				j.setSuccess(false);
				j.setMsg(msg);
			}
		}

		return j;
	}

	/**
	 * 第三方登录的虚拟用户升级成实名用户
	 * @param loginAccount
	 * @param cn
	 * @param idno
	 * @return
	 */
	@Override
	public JSONObject thirdUpgradeReal(String loginAccount, String cn, String idno) {
		JSONObject resJson = new JSONObject();
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
		// 身份证号校验
		String identityreg = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		Pattern idpattern = Pattern.compile(identityreg);
		Matcher matcher = idpattern.matcher(idno);
		boolean b = matcher.matches();
		if (!b) {
			resJson.put("result", false);
			resJson.put("message", "身份证格式错误");
			return resJson;
		}

		jsonparam.put("cn", cn);
		jsonparam.put("idno", idno);
		jsonparam.put("idtype", "01");
		jsonparam.put("appId", 90004);
		
		logger.info("【第三方实名认证的参数】" + jsonparam);
		String url = newssourl + "/sso/foreign/userManager/validateIdCard";
		String resstr = Common.sendPostForm(url, jsonparam);
		logger.info("【第三方登录的虚拟用户升级成实名用户认证的时候返回的信息】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			resJson.put("result", false);
			resJson.put("message", "sso服务器连接失败");
			return resJson;
		}
		
		com.alibaba.fastjson.JSONObject jsonObjectrt = com.alibaba.fastjson.JSONObject.parseObject(resstr);
		String rescode = jsonObjectrt.getString("code");
//		String result = jsonObjectrt.getString("result");
		String msg = jsonObjectrt.getString("msg");
		if (!"I0000000".equals(rescode)) { // 标识失败
			resJson.put("result", false);
			resJson.put("message", msg);
			return resJson;
		} 
		
		Nlcuser dbNlcuser = nlcuserService.getByAccount(loginAccount);
		if(null == dbNlcuser) {
			resJson.put("result", false);
			resJson.put("message", "不存在此用户");
			return resJson;
		}
		
		dbNlcuser.setCardtype("01");
		dbNlcuser.setCardno(idno);
		dbNlcuser.setName(cn);
		dbNlcuser.setRealname(cn);
		dbNlcuser.setRdrolecode("JS0001");
		nlcuserService.updateByPrimaryKey(dbNlcuser);
		
		resJson.put("result", true);
		resJson.put("message", "实名认证成功");
		return resJson;
	}

	/**
	 * 第三方登录的日志接口
	 * @return
	 */
	@Override
	public JSONObject thirdLoginLog(String openid, String method) {
		JSONObject resJson = new JSONObject();
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
		jsonparam.put("uid", openid);
		jsonparam.put("appId", 90004);
		jsonparam.put("loginResult", "成功");
		String loginType = "";
		switch (method) {
			case "qq": loginType = "8";break;
			case "wx": loginType = "9";break;
			case "wb": loginType = "10";break;
		}
		
		jsonparam.put("loginType", loginType);
		jsonparam.put("loginExp", "");
		String url = newssourl + "/sso/foreign/recordManager/recordAudit";
		String resstr = Common.sendPostForm(url, jsonparam);
		logger.info("【第三方登录日志返回信息】 " + resstr);
		if (StringUtils.isBlank(resstr)) {
			resJson.put("result", false);
			resJson.put("message", "sso服务器连接失败");
			return resJson;
		}
		return null;
	}

	/**
	 * 获取sso的accesstoken
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public JSONObject getSsoAccessToken(String username, String password) {
		JSONObject resJson = new JSONObject();
		String uidone = username;
		String userPasswordone = MD5Util.md5(password);
		String appIDone = "90004";
		String urlone = newssourl + "/sso/foreign/authenManager/getAccessToken?uid=" + uidone + "&userPassword="
				+ userPasswordone + "&appId=" + appIDone;
		String resone = Common.sendGet(urlone, null);
		if (StringUtils.isBlank(resone)) {
			resJson.put("result", false);
			resJson.put("msg", "sso服务器连接失败");
			return resJson;
		}
		
		com.alibaba.fastjson.JSONObject jsonObjectone = com.alibaba.fastjson.JSONObject.parseObject(resone);
		String code = jsonObjectone.getString("code");
		if ("I0000000".equals(code)) { // 认证成功
			com.alibaba.fastjson.JSONObject result = jsonObjectone.getJSONObject("result");
			String access_token = result.getString("accessToken"); // 得到令牌
			resJson.put("result", true);
			resJson.put("access_token", access_token);
		}else {
			String msg = jsonObjectone.getString("msg"); // 认证失败
			resJson.put("result", false);
			resJson.put("msg", msg);
		}
		return resJson;
	}

}
