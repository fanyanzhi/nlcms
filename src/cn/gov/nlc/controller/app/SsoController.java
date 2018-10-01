package cn.gov.nlc.controller.app;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.aleph.Aleph;
import cn.gov.nlc.aleph.Json;
import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.service.SsoService;
import cn.gov.nlc.util.Common;
import net.sf.json.JSONObject;

/**
 * 涉及到sso的操作
 * 
 * @author DAYI
 *
 */
@Controller
public class SsoController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.SsoController.class);

	@Autowired
	private SsoService ssoService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param clientid
	 * @param username
	 * @param password
	 * @param baseos
	 * @return
	 */
	@RequestMapping(value = "/app/login")
	@ResponseBody
	public JSONObject login(HttpServletRequest request, String clientid, @RequestParam String username, @RequestParam String password,
			String baseos) {
		logger.info("登录第一步");
		try {
			String reg = "^[8]{7}\\d{9}$"; // 定义正则表达式 身份证
			String reg2 = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(username);
			Pattern pattern2 = Pattern.compile(reg2);
			Matcher matcher2 = pattern2.matcher(username);
			boolean b = matcher.matches();
			boolean b2 = matcher2.matches();
			// 当条件满足时，同时登录aleph系统，获得bor_id，用于opac系统的查询和预约
			String borId = "";
			String ip = Common.getClientIP(request);
			logger.info("登录第1.1步" + ip);
			String location = Common.getIpLocation(ip);
			logger.info("登录第1.2步" + location);
			if (Common.IsNullOrEmpty(location) || "null".equalsIgnoreCase(location)) {
				location = "北京";
			}
			if (b || b2) {
				borId = Aleph.kalogin(username, password);
			}

			JSONObject json = ssoService.login(username, password, baseos, clientid, ip, location, borId);
			logger.info("登录返回的信息a" + json);
			return json;
		} catch (Exception e) {
			logger.info(e.getMessage());
			JSONObject resJson = new JSONObject();
			resJson.put("result", 500);
			logger.info("登录返回的信息b" + resJson);
			return resJson;
		}

	}
	
	/**
	 * 获取sso端的accesstoken   有效期半小时
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/app/getssoaccesstoken")
	@ResponseBody
	public JSONObject getSsoAccessToken(HttpServletRequest request, String username, String password) {
		JSONObject res = new JSONObject();
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			res.put("result", false);
			res.put("msg", "parameters error");
		}
		
		return ssoService.getSsoAccessToken(username, password);
	}

	/**
	 * 注册接口
	 * 使用旧的redis，现在已经出错了
	 * 
	 * @param nlcuser
	 * @return
	 * @throws IOException
	 */
/*	@RequestMapping("/app/register2")
	@ResponseBody
	public Json userRegister2(@RequestParam String code, @RequestBody Nlcuser nlcuser) {
		Json j = new Json();
		try {

			if (!RedisMngr.checkValidateCode(nlcuser.getMobile(), code)) {
				j.setSuccess(false);
				j.setMsg("手机验证码错误");
				return j;
			}

			Json json = ssoService.register(nlcuser);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			j.setSuccess(false);
			j.setMsg("server wrong");
			return j;
		}
	}*/

	/**
	 * 用户信息修改接口
	 * 
	 * @return
	 */
	@RequestMapping("/app/user/updateaccount")
	@ResponseBody
	public Json modifyUser(HttpServletRequest request, @RequestBody Nlcuser nlcuser) {
		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			nlcuser.setLoginaccount(loginAccount);
			Json json = ssoService.modifyUser(nlcuser);
			return json;
		} catch (Exception e) {
			Json j = new Json();
			j.setSuccess(false);
			j.setMsg("server wrong");
			return j;
		}
	}

	@RequestMapping("/app/user/updpwd")
	@ResponseBody
	public JSONObject updatePassword(HttpServletRequest request, @RequestParam String oldPassword, @RequestParam String newPassword) {
		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			JSONObject jsonObj = ssoService.upPassword(loginAccount, oldPassword, newPassword);
			return jsonObj;
		} catch (Exception e) {
			JSONObject resJson = new JSONObject();
			resJson.put("result", false);
			resJson.put("message", "server wrong");
			return resJson;
		}
	}

	@RequestMapping("/ssologin")
	public void ssoLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password) throws IOException {
		String result = ssoService.shareLogin(username, password);
		if(StringUtils.isNotBlank(result)) {
			response.sendRedirect("http://m.ndlib.cn/index.html?ticket="+result);
		}
	}
	
	/**
	 * 实名认证
	 * @return
	 */
	@RequestMapping("/app/user/authenticateRealname")
	@ResponseBody
	public JSONObject authenticateRealname(HttpServletRequest request, @RequestParam String cn, @RequestParam String idno) {
		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			JSONObject jsonObj = ssoService.authenticateRealname(loginAccount, cn, idno);
			return jsonObj;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			JSONObject resJson = new JSONObject();
			resJson.put("result", false);
			resJson.put("message", "server wrong");
			return resJson;
		}
	}
	
	/**
	 * 快速注册获取短信验证码
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping("/app/code/{phoneNum}")
	@ResponseBody
	public JSONObject getValidateCode(@PathVariable String phoneNum, HttpSession session) {
		logger.info("【进入发送验证码】"+phoneNum);
		JSONObject res = new JSONObject();
		try {
			res = ssoService.sendVerifyCode(phoneNum, session);
			logger.info("【发送验证码结果】" + res);
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			res.put("result", false);
			res.put("message", "服务器错误");
		}
		return res;
	}
	
	/**
	 * 快速注册接口
	 * 
	 * @param nlcuser
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/fastRegister")
	@ResponseBody
	public Json userRegister(@RequestParam String phone, @RequestParam String code, @RequestParam String password) {
		Json j = new Json();
		try {
			j = ssoService.fastRegister(phone, code, password);
			return j;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			j.setSuccess(false);
			j.setMsg("server wrong");
			return j;
		}
	}
	
	/**
	 * 实名注册接口
	 * 
	 * @param nlcuser
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/realnameRegister")
	@ResponseBody
	public Json realnameRegister(@RequestParam String phone, @RequestParam String code, @RequestParam String password, @RequestParam String cn, @RequestParam String idno) {
		Json j = new Json();
		try {
			j = ssoService.realnameRegister(phone, code, password, cn, idno);
			return j;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			j.setSuccess(false);
			j.setMsg("server wrong");
			return j;
		}
	}
	
	/**
	 * 第三方登录的虚拟用户升级成实名用户的实名认证接口
	 * 
	 * @param nlcuser
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/user/thirdUpgradeReal")
	@ResponseBody
	public JSONObject thirdUpgradeReal(HttpServletRequest request, @RequestParam String cn, @RequestParam String idno) {
		try {
//			String loginAccount = request.getAttribute("loginAccount").toString();
//			JSONObject jsonObj = ssoService.thirdUpgradeReal(loginAccount, cn, idno);
			JSONObject resJson = new JSONObject();
			resJson.put("result", false);
			resJson.put("message", "该功能暂时无法使用，要成为实名用户请先退出登录，再请进行实名注册。");
			return resJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			JSONObject resJson = new JSONObject();
			resJson.put("result", false);
			resJson.put("message", "server wrong");
			return resJson;
		}
	}
	
	@RequestMapping("/trysso")
	public void trySsoLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password, @RequestParam String url) throws IOException {
		if("http://open.nlc.cn".equals(url)) {	//国图公开课
			if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
				String result = ssoService.shareLogin(username, password);
				if(StringUtils.isNotBlank(result)) {
					response.sendRedirect("http://open.nlc.cn?ticket="+result);
					return;
				}
			}
		}
		response.sendRedirect(url);
	}
}
