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

/**
 * Servlet Filter implementation class ClientFilter
 */
@WebFilter(filterName = "/ClientFilter", dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE, DispatcherType.ERROR }, urlPatterns = "/app/*")
public class ClientFilter implements Filter {
	private NlcuseronlineService nlcuseronlineService;
	/**
	 * Default constructor.
	 */
	public ClientFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// HttpServletResponse httpResponse = (HttpServletResponse) response;

		String appId = httpRequest.getHeader("app_id");
		String sign = httpRequest.getHeader("sign");
		String timeStamp = httpRequest.getHeader("timestamp");
//		System.out.println(
//				request.getRemoteAddr() + "-->appid:" + appId + "-->sign:" + sign + "-->timeStamp:" + timeStamp);
		SHA1 sha = new SHA1();
		if (Common.IsNullOrEmpty(appId) || Common.IsNullOrEmpty(sign) || Common.IsNullOrEmpty(timeStamp)
				|| !sign.equals(sha.Digest((timeStamp + "tvcd7BiqSgJfnz1z"), "UTF-8").toLowerCase())) {
			
			  response.getOutputStream().write(
			  "{\"result\":false,\"message\":\"".concat("invalid url")
			  .concat("\",\"errorcode\":").concat("-1001").concat("}").getBytes
			  ("utf-8")); response.getOutputStream().close(); return;
			 
		}
		long curTimeStamp = System.currentTimeMillis();
		if ((Long.parseLong(timeStamp) < ((curTimeStamp / 1000) - 30 * 60))
				|| (Long.parseLong(timeStamp) > ((curTimeStamp / 1000) + 30 * 60))) {
			response.getOutputStream().write("{\"result\":false,\"message\":\"".concat("url timeout")
					.concat("\",\"errorcode\":").concat("-1002").concat("}").getBytes("utf-8"));
			response.getOutputStream().close(); return;
		}
		if (!validAppId(appId)) {
			response.getOutputStream().write("{\"result\":false,\"message\":\"".concat("invalid AppId")
					.concat("\",\"errorcode\":").concat("-1000").concat("}").getBytes("utf-8"));
			response.getOutputStream().close();
			return;
		}
		request.setAttribute("appid", appId);
		String token = httpRequest.getHeader("token");
		if (!Common.IsNullOrEmpty(token)) {
			String[] retUser = nlcuseronlineService.getLoginaccount(token);
			if (!Common.IsNullOrEmpty(retUser[0])) {
				request.setAttribute("loginAccount", retUser[0]);
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext context = fConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		nlcuseronlineService = (NlcuseronlineService) ctx.getBean("nlcuseronlineServiceImpl");
	}

	private boolean validAppId(String appId) {
		return true;
	}

}
