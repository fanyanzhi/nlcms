package cn.gov.nlc.controller.app;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.gov.nlc.service.NlctemplateService;
import cn.gov.nlc.util.PropertiesUtils;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class UploadController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.UploadController.class);
	
	@Autowired
	private NlctemplateService nlctemplateService;

	/**
	 * 单图片上传
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public JSONObject upload(MultipartFile file) {

		// 走ftp到图片服务器
		try {
			String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
			String newUpFilename = nlctemplateService.uploadPicture(file);
			if (StringUtils.isNotBlank(newUpFilename)) {
				String withUrlNewUpFilename = imgshow + "/" + newUpFilename;
				return JSONObject.fromObject("{\"result\":true, \"picname\":\"" + withUrlNewUpFilename + "\"}");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JSONObject.fromObject("{\"result\":false}");
	}

}
