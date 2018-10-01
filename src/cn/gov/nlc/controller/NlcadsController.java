package cn.gov.nlc.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlcads;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlcadsService;
import cn.gov.nlc.service.NlctemplateService;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/ads")
public class NlcadsController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcadsController.class);
	
	@Autowired
	private NlcadsService nlcadsService;
	@Autowired
	private NlctemplateService nlctemplateService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	
	/**
	 * 跳转到广告管理页面
	 * @return
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
		nlcadminlog.setOperate("查看广告位页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "ads/view";
	}
	
	/**
	 * 广告展示页面的list
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getAdsList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows) {
		EasyUiDataGridResult result = nlcadsService.getAdsList(page, rows);
		return result;
	}
	
	/**
	 * 根据id删除广告信息，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = nlcadsService.deleteSingleById(id);
		
		if(result == 0) {	//删除失败
			return "{result:false}";
		}else {				//删除成功
			return "{result:true}";
		}
	}
	
	/**
	 * 广告上下架
	 */
	@RequestMapping("/shelf/{id}")
	@ResponseBody
	public String shelf(@PathVariable Integer id) {
		try{
			nlcadsService.shelfAds(id);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 新增
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
		model.addAttribute("imgshow", imgshow);
		return "ads/add";
	}
	
	/**
	 * 图片上传
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(MultipartFile file) {
		
		// 走ftp到图片服务器
		try {
			String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
			String newUpFilename = nlctemplateService.uploadPicture(file);
			if(StringUtils.isNotBlank(newUpFilename)) {
				String withUrlNewUpFilename = imgshow + "/" + newUpFilename;
				return "{result:true, picname:\""+withUrlNewUpFilename+"\",size:\""+file.getSize()+"\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "{result:false}";
	}
	
	/**
	 * 添加广告
	 * @return
	 */
	@RequestMapping("/addAds")
	@ResponseBody
	public String addAds(Nlcads nlcads) {
		try{
			String adsid = UUIDGenerator.getUUID();
			nlcads.setAdsid(adsid);
			nlcads.setTime(new Date());
			nlcadsService.insertAds(nlcads);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 广告修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Nlcads nlcads = nlcadsService.selectByPrimaryKey(id);
		model.addAttribute("nlcads", nlcads);
		return "ads/update";
	}
	
	/**
	 * 修改广告
	 */
	@RequestMapping("/updateAds")
	@ResponseBody
	public String updateAds(HttpServletRequest request) {
		try{
			Map<String, String[]> parameterMap = request.getParameterMap();
			nlcadsService.updateAds(parameterMap);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
}
