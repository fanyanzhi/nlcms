package cn.gov.nlc.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.gov.nlc.service.NlcuseronlineService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.SHA1;

@WebFilter(filterName = "/UserFilter", dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE, DispatcherType.ERROR }, urlPatterns = "/app/user/*")
public class UserFilter implements Filter {

	private NlcuseronlineService nlcuseronlineService;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) arg0;
		// HttpServletResponse httpResponse = (HttpServletResponse) response;

		String appId = httpRequest.getHeader("app_id");
		String sign = httpRequest.getHeader("sign");
		String timeStamp = httpRequest.getHeader("timestamp");
		SHA1 sha = new SHA1();
		if (Common.IsNullOrEmpty(appId) || Common.IsNullOrEmpty(sign) || Common.IsNullOrEmpty(timeStamp)
				|| !sign.equals(sha.Digest((timeStamp + "tvcd7BiqSgJfnz1z"), "UTF-8").toLowerCase())) {
			arg1.getOutputStream().write("{\"result\":false,\"message\":\"".concat("invalid url")
					.concat("\",\"errorcode\":").concat("-1001").concat("}").getBytes("utf-8"));
			arg1.getOutputStream().close();
			return;
		}
		long curTimeStamp = System.currentTimeMillis();
		if ((Long.parseLong(timeStamp) < ((curTimeStamp / 1000) - 10 * 60))
				|| (Long.parseLong(timeStamp) > ((curTimeStamp / 1000) + 10 * 60))) {
			arg1.getOutputStream().write("{\"result\":false,\"message\":\"".concat("url timeout")
					.concat("\",\"errorcode\":").concat("-1002").concat("}").getBytes("utf-8"));
			arg1.getOutputStream().close(); return;
		}
		if (!validAppId(appId)) {
			arg1.getOutputStream().write("{\"result\":false,\"message\":\"".concat("invalid AppId")
					.concat("\",\"errorcode\":").concat("-1000").concat("}").getBytes("utf-8"));
			arg1.getOutputStream().close();
			return;
		}
		String token = httpRequest.getHeader("token");
		if (Common.IsNullOrEmpty(token)) {
			arg1.getOutputStream().write("{\"result\":false,\"message\":\"".concat("please login")
					.concat("\",\"errorcode\":").concat("-1003").concat("}").getBytes("utf-8"));
			arg1.getOutputStream().close();
			return;
		}
		String[] retUser = new String[]{"",""};
		try{
			retUser = nlcuseronlineService.getLoginaccount(token);
		}catch(Exception e){
			Common.appendMethod("/root/chklogin", "getLoginaccount false,token:"+token+"-->message:"+e.getMessage());
			System.out.println(e.getMessage());
		}
		if (Common.IsNullOrEmpty(retUser[0])) {
			arg1.getOutputStream().write("{\"result\":false,\"message\":\"".concat("error token")
					.concat("\",\"errorcode\":").concat("-1004").concat("}").getBytes("utf-8"));
			arg1.getOutputStream().close();
			return;
		}
		arg0.setAttribute("loginAccount", retUser[0]);
		arg0.setAttribute("borId", retUser[1]);
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		ServletContext context = arg0.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		nlcuseronlineService = (NlcuseronlineService) ctx.getBean("nlcuseronlineServiceImpl");

	}

	private boolean validAppId(String appId) {
		return true;
	}

	private boolean isValid(String ticket) {
		return true;
	}

}
