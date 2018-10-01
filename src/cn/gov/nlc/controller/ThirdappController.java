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
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.Thirdapp;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.ThirdappService;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/thirdapp")
public class ThirdappController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.ThirdappController.class);

	@Autowired
	private ThirdappService thirdappService;
	@Autowired
	private NlcadminlogService nlcadminlogService;

	/**
	 * 弹窗页面
	 * 
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
		nlcadminlog.setOperate("查看其他应用程序页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "thirdapp/view";
	}

	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Thirdapp thirdapp) {
		EasyUiDataGridResult list = thirdappService.getList(page, rows, thirdapp);
		return list;
	}

	@RequestMapping("/add")
	public String add() {
		return "thirdapp/add";
	}

	@RequestMapping("/addapp")
	@ResponseBody
	public String insert(Thirdapp thirdapp) {
		try {
			String uuid = UUIDGenerator.getUUID();
			thirdapp.setUuid(uuid);
			thirdapp.setCreatime(new Date());
			thirdappService.insert(thirdapp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * status 1发布，0取消
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public String shelf(Integer id, String status) {
		try{
			thirdappService.publish(id, status);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 根据id删除，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = thirdappService.deleteSingleById(id);
		
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
			thirdappService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 编辑页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Thirdapp thirdapp = thirdappService.selectByPrimaryKey(id);
		model.addAttribute("thirdapp", thirdapp);
		if(null != thirdapp) {
			String os = thirdapp.getOs();
			if("android".equals(os)) {
				return "thirdapp/update1";
			}
		}
		return "thirdapp/update2";
	}
	
	@RequestMapping("/updateapp")
	@ResponseBody
	public String updateApp(HttpServletRequest request) {
		try{
			Map<String, String[]> parameterMap = request.getParameterMap();
			thirdappService.updateApp(parameterMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{result:false}";
		}
		return "{result:true}";
	}
}
