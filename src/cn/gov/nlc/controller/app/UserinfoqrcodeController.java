package cn.gov.nlc.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Userinfoqrcode;
import cn.gov.nlc.service.UserinfoqrcodeService;
import cn.gov.nlc.util.AesUtil;
import cn.gov.nlc.util.UUIDGenerator;
import net.sf.json.JSONObject;

/**
 * 二维码提供用户信息
 * @author DAYI
 *
 */
@Controller
public class UserinfoqrcodeController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.UserinfoqrcodeController.class);
	
	@Autowired
	private UserinfoqrcodeService userinfoqrcodeService;
	
	/**
	 * 生成用户二维码
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/user/geneuserinfoqrcode")
	@ResponseBody
	public JSONObject geneUserinfoQrcode(HttpServletRequest request) {
		JSONObject res = new JSONObject();
		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			Userinfoqrcode pojo = new Userinfoqrcode();
			pojo.setUsername(loginAccount);
			String qrcode = UUIDGenerator.getUUID() + "@" + System.currentTimeMillis();
			pojo.setQrcode(qrcode);
			userinfoqrcodeService.insertUserinfoqrcode(pojo);
			String encodeQrcode = AesUtil.encodeAES(qrcode);
			res.put("result", true);
			res.put("qrcode", encodeQrcode);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			res.put("result", false);
			res.put("message", "服务器错误");
		}
		return res;
	}
	
	/**
	 * 第三方通过二维码获取用户信息
	 * @param qrcode
	 * @return
	 */
	@RequestMapping("/getuserinfo")
	@ResponseBody
	public JSONObject matchUserinfo(String qrcode) {
		JSONObject res = new JSONObject();
		try {
			if(StringUtils.isBlank(qrcode)) {
				res.put("result", false);
				res.put("message", "参数为空");
				res.put("errcode", 1);
				return res;
			}
			res = userinfoqrcodeService.matchUserinfo(qrcode);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			res.put("result", false);
			res.put("message", "服务器错误");
			res.put("errcode", 0);
		}
		return res;
	}
}
