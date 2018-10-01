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
import cn.gov.nlc.pojo.Librarymap;
import cn.gov.nlc.service.LibrarymapService;
import cn.gov.nlc.service.NlctemplateService;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/librarymap")
public class LibrarymapController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.LibrarymapController.class);

	@Autowired
	private NlctemplateService nlctemplateService;
	@Autowired
	private LibrarymapService librarymapService;
	
	/**
	 * 跳转到馆区地图管理页面
	 * @return
	 */
	@RequestMapping("/show")
	public String show() {
		return "librarymap/view";
	}
	
	/**
	 * 罐区地图的list
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getAdsList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows) {
		EasyUiDataGridResult result = librarymapService.getMapList(page, rows);
		return result;
	}
	
	/**
	 * 馆区地图修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Librarymap librarymap = librarymapService.selectByPrimaryKey(id);
		model.addAttribute("librarymap", librarymap);
		return "librarymap/update";
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
	 * 修改馆区地图
	 */
	@RequestMapping("/updateMap")
	@ResponseBody
	public String updateWj(HttpServletRequest request) {
		try{
			Map<String, String[]> parameterMap = request.getParameterMap();
			librarymapService.updateMap(parameterMap);
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
		return "librarymap/add";
	}
	
	/**
	 * 添加馆区地图
	 * @return
	 */
	@RequestMapping("/addmap")
	@ResponseBody
	public String addPic(Librarymap map) {
		try{
			map.setTime(new Date());
			librarymapService.insertMap(map);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 根据id删除馆区地图，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = librarymapService.deleteSingleById(id);
		
		if(result == 0) {	//删除失败
			return "{result:false}";
		}else {				//删除成功
			return "{result:true}";
		}
	}
	
}
