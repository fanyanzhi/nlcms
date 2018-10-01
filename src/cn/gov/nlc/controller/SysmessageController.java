package cn.gov.nlc.controller;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.pojo.SysmessageExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/message")
public class SysmessageController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.SysmessageController.class);
	
	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	
	/**
	 * 站内信页面
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
		nlcadminlog.setOperate("查看站内信页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "message/view";
	}
	
	/**
	 * 站内信展示页面，包括高级查询
	 * @param page
	 * @param rows
	 * @param sysmessage
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, SysmessageExt sysmessageExt) {
		EasyUiDataGridResult result = sysmessageService.getmessageList(page, rows, sysmessageExt);
		return result;
	}
	
	/**
	 * 根据id删除新闻信息，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = sysmessageService.deleteSingleById(id);
		
		if(result == 0) {	//删除失败
			return "{result:false}";
		}else {				//删除成功
			return "{result:true}";
		}
	}
	
	/**
	 * 删除多个
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public String deleteAll(String ids) {
		if(StringUtils.isBlank(ids)) {
			return "{result:false}";
		}
		
		String[] strArr = ids.split(",");
		for (String str : strArr) {
			sysmessageService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 跳转到添加站内信
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "message/add";
	}
	
	/**
	 * 添加站内信
	 */
	@RequestMapping("/addMessage")
	@ResponseBody
	public String saveTrailer(Sysmessage sysmessage, HttpSession session) {
		try{
			sysmessage.setType(Byte.valueOf("0"));
			
			Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			String username = dbNlcadmin.getUsername();
			sysmessage.setPubname(username);
			sysmessage.setUsername("全体读者");
			sysmessage.setTime(new Date());
			sysmessage.setSort("");
			sysmessage.setFid("");
			
			sysmessageService.insertMessage(sysmessage);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 站内信修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Sysmessage sysmessage = sysmessageService.selectByPrimaryKey(id);
		model.addAttribute("sysmessage", sysmessage);
		return "message/update";
	}
	
	/**
	 * 修改站内信
	 */
	@RequestMapping("/updateMessage")
	@ResponseBody
	public String updateMessage(HttpServletRequest request, HttpSession session) {
		try{
			Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			String pubname = dbNlcadmin.getUsername();
			Map<String, String[]> parameterMap = request.getParameterMap();
			sysmessageService.updateMessage(parameterMap, pubname);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
}
