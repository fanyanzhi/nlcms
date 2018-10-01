package cn.gov.nlc.controller;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.service.NlcadminService;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.util.MD5Util;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/admin")
public class NlcadminController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcadminController.class);

	@Autowired
	private NlcadminService nlcadminService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	
	/**
	 * 用户注销
	 */
	@RequestMapping(value="/logout", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String logout(HttpSession session) {
		try{
			Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			Date loginTime = (Date) session.getAttribute("loginTime");
			String loginIp = (String) session.getAttribute("loginIp");
			
			Nlcadminlog nlcadminlog = new Nlcadminlog();
			nlcadminlog.setUsername(dbNlcadmin.getUsername());
			nlcadminlog.setRole(dbNlcadmin.getRole());
			nlcadminlog.setIp(loginIp);
			nlcadminlog.setTime(loginTime);
			nlcadminlog.setOperate("管理员注销");
			nlcadminlogService.insertNlcadminlog(nlcadminlog);
			
			session.invalidate();
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 进入修改密码页面
	 */
	@RequestMapping("/editPwdPage")
	public String editPwdPage() {
		return "admin/userEditPwd";
	}
	
	/**
	 * 进入修改密码页面
	 */
	@RequestMapping("/editPwdPagebak")
	public String editPwdPagebak() {
		return "admin/userEditPwdbak";
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/editAdminPwd", method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String editAdminPwd(String oldPwd, String pwd, String rePwd, HttpSession session){
		try{
			if(StringUtils.isBlank(pwd)) {
				return "{\"result\":false, \"msg\":\"新密码不能为空\"}";
			}
			
			if(!pwd.equals(rePwd)) {
				return "{\"result\":false, \"msg\":\"密码不一致\"}";
			}
			
			Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			String dbPassword = dbNlcadmin.getPassword();
			oldPwd = MD5Util.md5(oldPwd);
			if(dbPassword.equals(oldPwd)) {
				Nlcadmin newnlcadmin = new Nlcadmin();
				newnlcadmin.setId(dbNlcadmin.getId());
				newnlcadmin.setPassword(MD5Util.md5(pwd));
				nlcadminService.changePassword(newnlcadmin);
			}else {
				return "{\"result\":false, \"msg\":\"原密码错误\"}";
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false, msg:'未知错误'}";
		}
		return "{result:true, msg:'修改成功'}";
	}
	
	/**
	 * 进入管理员列表页面
	 * @return
	 */
	@RequestMapping("/show")
	public String show() {
		return "admin/list";
	}
	
	/**
	 * 管理员展示页面，包括高级查询
	 * @param page
	 * @param rows
	 * @param nlcadmin
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, Nlcadmin nlcadmin) {
		EasyUiDataGridResult result = nlcadminService.getAdminList(page, rows, nlcadmin);
		return result;
	}
	
	/**
	 * 根据id删除管理员信息，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = nlcadminService.deleteSingleById(id);
		
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
			nlcadminService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 添加管理员页面
	 */
	@RequestMapping("/add")
	public String addAdmin() {
		return "admin/addAdmin";
	}
	
	/**
	 * insert管理员
	 */
	@RequestMapping("/insertAdmin")
	@ResponseBody
	public String insertAdmin(Nlcadmin nlcadmin) {
		try{
			nlcadminService.insertAdmin(nlcadmin);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 管理员修改页面
	 */
	@RequestMapping("/updateView")
	public String updateView(Integer id, Model model) {
		Nlcadmin nlcadmin = nlcadminService.selectByPrimaryKey(id);
		model.addAttribute("nlcadmin", nlcadmin);
		return "admin/update";
	}
	
	/**
	 * 保存对用户的修改
	 */
	@RequestMapping("/updateAdmin")
	@ResponseBody
	public String updateAdmin(Nlcadmin nlcadmin) {
		try{
			String password = nlcadmin.getPassword();
			if(StringUtils.isBlank(password)) {
				nlcadmin.setPassword(null);
			}else {
				nlcadmin.setPassword(MD5Util.md5(password));
			}
			
			String moduleid = nlcadmin.getModuleid();
			if(null == moduleid) {
				nlcadmin.setModuleid("");
			}
			
			nlcadminService.updateAdmin(nlcadmin);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 添加用户时，检查用户名是否存在 
	 */
	@RequestMapping("/checkUsername")
	@ResponseBody
	public String checkUsername(String username) {
		Nlcadmin nlcadmin = nlcadminService.GetAdminByName(username);
		if(null == nlcadmin) {
			return "{result:true}";
		}else {
			return "{result:false}";
		}
	}
	
}
