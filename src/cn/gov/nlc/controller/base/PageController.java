package cn.gov.nlc.controller.base;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.base.PageController.class);

	/**
	 * 进入登录页面
	 * @return
	 */
	@RequestMapping("/")
	public String login(HttpSession session){
		if (session.getAttribute("LoginObj") != null) {	//已经登录的，不再进入登录页面
			return "index";
		}
		return "login";
	}
	
	/**
	 * 进入首页
	 * @return
	 */
	@RequestMapping("/session/index")
	public String showIndex(){
		return "index";
	}
	
	/**
	 * 直接进入jsp页面
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page, HttpSession session){
		if("login".equalsIgnoreCase(page) && session.getAttribute("LoginObj") != null) {
			return "index";	//已经登录的，不再进入登录页面
		}
		if("index".equalsIgnoreCase(page) && session.getAttribute("LoginObj") == null) {
			return "login";
		}
		return page;
	}
}
