package cn.gov.nlc.controller.share;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jit.client.sp.bean.LoginResult;
import com.jit.client.sp.model.LoginProcessUtil;

import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.PropertiesUtils;

@Controller

public class SsologinController {
	private static String ssourl = "http://sso1.nlc.cn";
	private static String nlcmsurl = "http://localhost:8080";
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.share.SsologinController.class);

	@RequestMapping("/ssologin3")
	public void ssoLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		String ip = Common.getClientIP(request);
		try {
			LoginProcessUtil.sendRequestString(username, new String(password), ip, ssourl + "/sso/user-login",
					nlcmsurl + "/nlcms/ssologinhandler", request, response);
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	@RequestMapping("/ssologinhandler")
	public void ssoLoginHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String res = request.getParameter("response");
		LoginResult loginResult = LoginProcessUtil.parseResponseString(res, request);
		if (loginResult.getResult() == 200) {
			response.sendRedirect("http://m.ndlib.cn/");
		}
		

	}

}
