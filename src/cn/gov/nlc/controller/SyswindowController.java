package cn.gov.nlc.controller;

import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.SyswindowExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/window")
public class SyswindowController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.SyswindowController.class);
	
	@Autowired
	private NlcadminlogService nlcadminlogService;
	@Autowired
	private SyswindowService syswindowService;
	
	/**
	 * 弹窗页面
	 * @return
	 */
	@RequestMapping("/show")
	public String show(HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");
		
		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看弹窗页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "window/view";
	}
	
	/**
	 * 弹窗展示页面，包括高级查询
	 * @param page
	 * @param rows
	 * @param syswindowExt
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, SyswindowExt syswindowExt) {
		EasyUiDataGridResult result = syswindowService.getwindowList(page, rows, syswindowExt);
		return result;
	}
	
}
