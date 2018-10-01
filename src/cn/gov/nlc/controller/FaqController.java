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
import cn.gov.nlc.pojo.Faq;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.Thirdapp;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.service.FaqService;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/faq")
public class FaqController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.FaqController.class);

	@Autowired
	private NlcadminlogService nlcadminlogService;
	@Autowired
	private FaqService faqService;
	
	/**
	 * 跳转到管理页面
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
		nlcadminlog.setOperate("查看常见问题页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "faq/view";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Faq faq) {
		EasyUiDataGridResult list = faqService.getList(page, rows, faq);
		return list;
	}
	
	/**
	 * 根据id删除，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = faqService.deleteSingleById(id);
		
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
			faqService.deleteSingleById(Integer.parseInt(str));
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
			faqService.publish(id, status);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 专题排序页面
	 */
	@RequestMapping("/sort")
	public String sort(Integer id, Model model) {
		Faq faq = faqService.selectByPrimaryKey(id);
		model.addAttribute("faq", faq);
		return "faq/sort";
	}
	
	@RequestMapping("/sortfaq")
	@ResponseBody
	public String sortFaq(Integer id, Integer cseq) {
		try{
			faqService.sortFaq(id, cseq);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "faq/add";
	}
	
	@RequestMapping("/savefaq")
	@ResponseBody
	public String insert(Faq faq) {
		try {
			String uuid = UUIDGenerator.getUUID();
			faq.setUuid(uuid);
			faq.setCreatime(new Date());
			
			String status = faq.getStatus();
			if("已发布".equals(status)) {
				faq.setBkpubtime(new Date());
			}
			
			faqService.insert(faq);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	@RequestMapping("/addPreview")
	public String addPreview() {
		return "faq/addPreview";
	}
	
	/**
	 * 修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Faq faq = faqService.selectByPrimaryKey(id);
		model.addAttribute("faq", faq);
		return "faq/update";
	}
	
	@RequestMapping("/updatefaq")
	@ResponseBody
	public String updateObj(HttpServletRequest request) {
		try{
			Map<String, String[]> parameterMap = request.getParameterMap();
			faqService.updateObj(parameterMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	@RequestMapping("/itemPreview")
	public String itemPreview(Integer id, Model model) {
		Faq faq = faqService.selectByPrimaryKey(id);
		model.addAttribute("faq", faq);
		return "faq/itemPreview";
	}
}
