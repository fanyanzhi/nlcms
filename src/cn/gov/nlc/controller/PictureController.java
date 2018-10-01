package cn.gov.nlc.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

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

import cn.gov.nlc.pojo.Nlcads;
import cn.gov.nlc.pojo.Picture;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlctemplateService;
import cn.gov.nlc.service.PictureService;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/picture")
public class PictureController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.PictureController.class);

	@Autowired
	private PictureService pictureService;
	@Autowired
	private NlctemplateService nlctemplateService;
	@Autowired
	private NlcadminlogService nlcadminlogService;

	/**
	 * 跳转到wj管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/show")
	public String show() {
		return "pic/view";
	}

	/**
	 * 文津经典诵读展示页面的list
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getAdsList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		EasyUiDataGridResult result = pictureService.getPicList(page, rows);
		return result;
	}

	/**
	 * 文津经典诵读修改图片页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Picture picture = pictureService.selectByPrimaryKey(id);
		model.addAttribute("picture", picture);
		return "pic/update";
	}

	/**
	 * 图片上传
	 * 
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
	 * 修改文津经典诵读
	 */
	@RequestMapping("/updatePic")
	@ResponseBody
	public String updateWj(HttpServletRequest request) {
		try {
			Map<String, String[]> parameterMap = request.getParameterMap();
			pictureService.updatePic(parameterMap);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
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
		return "pic/add";
	}

	/**
	 * 添加图片项
	 * 
	 * @return
	 */
	@RequestMapping("/addPic")
	@ResponseBody
	public String addPic(Picture picture) {
		try {
			String siid = UUIDGenerator.getUUID();
			picture.setWjid(siid);
			picture.setTime(new Date());
			pictureService.insertPic(picture);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}

	/**
	 * 根据id删除图片项，单个删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = pictureService.deleteSingleById(id);

		if (result == 0) { // 删除失败
			return "{result:false}";
		} else { // 删除成功
			return "{result:true}";
		}
	}

}
