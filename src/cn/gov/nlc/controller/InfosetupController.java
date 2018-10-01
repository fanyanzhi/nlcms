package cn.gov.nlc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Infosetup;
import cn.gov.nlc.service.InfosetupService;

@Controller
@RequestMapping("/session/infosetup")
public class InfosetupController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.InfosetupController.class);
	
	@Autowired
	private InfosetupService infosetupService;
	
	/**
	 * 跳转到消息通知设置页面
	 * @return
	 */
	@RequestMapping("/show")
	public String show(Model model) {
		List<Infosetup> list = infosetupService.getAllList();
		if(null != list && list.size() > 0) {
			for (Infosetup infosetup : list) {
				if((infosetup.getTypeid()+"").equals("1")) { //交易记录
					model.addAttribute("trade", infosetup.getPushmethod());
					model.addAttribute("tradeid", infosetup.getId());
				}
				if((infosetup.getTypeid()+"").equals("2")) { //图书催还
					model.addAttribute("bookback", infosetup.getPushmethod());
					model.addAttribute("bookbackid", infosetup.getId());
				}
				if((infosetup.getTypeid()+"").equals("3")) { //违规
					model.addAttribute("breaklaw", infosetup.getPushmethod());
					model.addAttribute("breaklawid", infosetup.getId());
				}
			}
		}
		return "infosetup/view";
	}
	
	/**
	 * 修改设置
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateNotice(Infosetup infosetup) {
		try {
			infosetup.setTime(new Date());
			infosetupService.updateBypk(infosetup);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
}
