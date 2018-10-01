package cn.gov.nlc.controller.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Accesslog;
import cn.gov.nlc.pojo.Appinstall;
import cn.gov.nlc.pojo.Apprenew;
import cn.gov.nlc.pojo.Appstart;
import cn.gov.nlc.pojo.Appunstall;
import cn.gov.nlc.pojo.Usestatist;
import cn.gov.nlc.service.AccesslogService;
import cn.gov.nlc.service.AppinstallService;
import cn.gov.nlc.service.ApprenewService;
import cn.gov.nlc.service.AppstartService;
import cn.gov.nlc.service.AppunstallService;
import cn.gov.nlc.service.UsestatistService;
import cn.gov.nlc.util.Common;

@Controller
@RequestMapping("/app")
public class AccessLogController {

	@Autowired
	private AccesslogService accesslogService;

	@Autowired
	private AppinstallService appinstallService;

	@Autowired
	private AppunstallService appunstallService;

	@Autowired
	private ApprenewService apprenewService;

	@Autowired
	private AppstartService appstartService;

	@Autowired
	private UsestatistService usestatistService;

	@RequestMapping(value = "/accesslog")
	@ResponseBody
	public void accessLog(HttpServletRequest request, @RequestBody Accesslog accesslog) {
		String ip = Common.getClientIP(request);
		accesslog.setAddress(ip);
		accesslog.setTime(new Date());
		accesslog.setLocation(Common.getIpLocation(ip));
		accesslogService.insertAccesslog(accesslog);

	}

	/**
	 * 安装
	 */
	@RequestMapping(value = "/install")
	@ResponseBody
	public void appInstallLog(HttpServletRequest request, @RequestBody Appinstall appinstall) {
		String ip = Common.getClientIP(request);
		appinstall.setAddress(ip);
		appinstall.setTime(new Date());
		appinstall.setLocation(Common.getIpLocation(ip));
		appinstallService.insertAppinstall(appinstall);
	}

	/**
	 * 启动
	 */
	@RequestMapping(value = "/start")
	@ResponseBody
	public void appStartLog(HttpServletRequest request, @RequestBody Appstart appstart) {
		String ip = Common.getClientIP(request);
		appstart.setAddress(ip);
		appstart.setTime(new Date());
		appstart.setLocation(Common.getIpLocation(ip));
		appstartService.insertAppstart(appstart);
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/renew")
	@ResponseBody
	public void appRenewLog(HttpServletRequest request, @RequestBody Apprenew apprenew) {
		String ip = Common.getClientIP(request);
		apprenew.setAddress(ip);
		apprenew.setTime(new Date());
		apprenew.setLocation(Common.getIpLocation(ip));
		apprenewService.insertApprenew(apprenew);
	}

	/**
	 * 卸载
	 */
	@RequestMapping(value = "/unstall")
	@ResponseBody
	public void appUnstallLog(HttpServletRequest request, @RequestBody Appunstall appunstall) {
		String ip = Common.getClientIP(request);
		appunstall.setAddress(ip);
		appunstall.setTime(new Date());
		appunstall.setLocation(Common.getIpLocation(ip));
		appunstallService.insertAppunstall(appunstall);
	}

	@RequestMapping(value = "/statist")
	@ResponseBody
	public void appUnstallLog(HttpServletRequest request, @RequestBody Usestatist usestatist) {
		String loginAccount = request.getAttribute("loginAccount") == null ? ""
				: request.getAttribute("loginAccount").toString();
		String ip = Common.getClientIP(request);
		usestatist.setAddress(ip);
		usestatist.setTime(new Date());
		usestatist.setLocation(Common.getIpLocation(ip));
		if(!Common.IsNullOrEmpty(loginAccount)){
			usestatist.setUsername(loginAccount);
		}
		usestatistService.insertUsestatis(usestatist);
	}

}
