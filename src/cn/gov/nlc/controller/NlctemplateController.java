package cn.gov.nlc.controller;

import java.io.IOException;
import java.util.Date;

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
import org.springframework.web.multipart.MultipartFile;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlctemplate;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlctemplateService;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/template")
public class NlctemplateController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlctemplateController.class);

	@Autowired
	private NlctemplateService nlctemplateService;
	@Autowired
	private NlcadminlogService nlcadminlogService;

	/**
	 * 跳转到文津诵读模板设置
	 */
	@RequestMapping("/show")
	public String show(Model model, HttpSession session) {
		String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
		model.addAttribute("imgshow", imgshow);

		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看文津诵读模板设置");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "template/view";
	}

	/**
	 * 模板展示页面
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getTemplateList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		EasyUiDataGridResult result = nlctemplateService.getTemplateList(page, rows);
		return result;
	}

	/**
	 * 根据id删除模板，单个删除
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = nlctemplateService.deleteSingleById(id);

		if (result == 0) { // 删除失败
			return "{result:false}";
		} else { // 删除成功
			return "{result:true}";
		}
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
		model.addAttribute("imgshow", imgshow);
		return "template/add";
	}

	/**
	 * 图片上传
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request, MultipartFile file) {
		// System.out.println("ContentLen-->"+request.getContentLength());
		// System.out.println("filesize"+file.getSize());
		// 走ftp到图片服务器
		try {
			String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
			String newUpFilename = nlctemplateService.uploadPicture(file);
			if (StringUtils.isNotBlank(newUpFilename)) {
				String withUrlNewUpFilename = imgshow + "/" + newUpFilename;
				return "{result:true, picname:\"" + withUrlNewUpFilename + "\",size:\"" + file.getSize() + "\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "{result:false}";
	}

	/**
	 * 添加模板
	 */
	@RequestMapping("/addTemplate")
	@ResponseBody
	public String addTemplate(Nlctemplate nlctemplate) {
		try {
			nlctemplate.setTime(new Date());
			nlctemplateService.insertTemplate(nlctemplate);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}

		return "{result:true}";
	}

	/**
	 * 模板修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Nlctemplate nlctemplate = nlctemplateService.selectByPrimaryKey(id);
		model.addAttribute("nlctemplate", nlctemplate);
		// String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
		// model.addAttribute("imgshow", imgshow);
		return "template/update";
	}

	/**
	 * 修改模板
	 */
	@RequestMapping("/updateTemplate")
	@ResponseBody
	public String updateSubject(Nlctemplate nlctemplate) {
		try {
			nlctemplateService.updateTemplate(nlctemplate);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}

	/**
	 * 公告发布 status 1发布，0取消
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public String shelf(Integer id, String status) {
		try {
			nlctemplateService.publish(id, status);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
}
