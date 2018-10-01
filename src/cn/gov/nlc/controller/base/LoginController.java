package cn.gov.nlc.controller.base;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.service.NlcadminService;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.util.MD5Util;

@Controller
public class LoginController {
	
	@Autowired
	private NlcadminService nlcadminService;
	@Autowired
	private NlcadminlogService nlcadminlogService;

	@RequestMapping("/logincontroller")
	@ResponseBody
	public String login(@RequestBody Nlcadmin nlcadmin, HttpSession session, HttpServletRequest request) {
		
		if(null == nlcadmin) {
			return "{result:false, msg:'请输入用户名和密码'}";
		}
		String username = nlcadmin.getUsername();
		String password = nlcadmin.getPassword();
		
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return "{result:false, msg:'请输入用户名和密码'}";
		}
		
		Nlcadmin dbNlcadmin = nlcadminService.GetAdminByName(username);
		if(null == dbNlcadmin) {
			return "{result:false, msg:'没有此用户'}";
		}
		
		//判断密码是否一致，需要先将页面传过来的密码加密，然后与数据库中加密的密码对比
		password = MD5Util.md5(password);
		
		if(password.equals(dbNlcadmin.getPassword())) {
			session.setAttribute("LoginObj", dbNlcadmin);
			session.setAttribute("loginTime", new Date());
			session.setAttribute("loginIp", getRemoteHost(request));
			
			Nlcadminlog nlcadminlog = new Nlcadminlog();
			nlcadminlog.setUsername(dbNlcadmin.getUsername());
			nlcadminlog.setRole(dbNlcadmin.getRole());
			nlcadminlog.setIp(getRemoteHost(request));
			nlcadminlog.setTime(new Date());
			nlcadminlog.setOperate("管理员登录");
			nlcadminlogService.insertNlcadminlog(nlcadminlog);
			
			return "{result:true}";
		}
		
		return "{result:false, msg:'密码错误'}";
	}
	
	private String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
}
