package cn.gov.nlc.controller.app;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Audioinfo;
import cn.gov.nlc.service.AudioinfoService;

@Controller
@RequestMapping("/app")
public class AudioinfoController {

	@Autowired
	private AudioinfoService audioinfoService;
	
	/**
	 * 音频播放量的接口
	 * @param request
	 * @param uid
	 */
	@RequestMapping("/info/audio")
	@ResponseBody
	public void setAudioinfo(HttpServletRequest request, String uid) {
		String loginAccount = request.getAttribute("loginAccount") == null ? "" : request.getAttribute("loginAccount").toString();
		Audioinfo audioinfo = new Audioinfo();
		if(StringUtils.isNotBlank(loginAccount)) {
			audioinfo.setUsername(loginAccount);
		}
		audioinfo.setUid(uid);
		audioinfo.setTime(new Date());
		audioinfoService.insertAudioinfo(audioinfo);
	}
}
