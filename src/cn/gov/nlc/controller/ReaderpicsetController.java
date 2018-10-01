package cn.gov.nlc.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Nlctemplate;
import cn.gov.nlc.pojo.Readerpicset;
import cn.gov.nlc.service.ReaderpicsetService;

@Controller
@RequestMapping("/session/readerpicset")
public class ReaderpicsetController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.ReaderpicsetController.class);

	@Autowired
	private ReaderpicsetService readerpicsetService;
	
	/**
	 * 跳转到读者画像展示设置
	 * @return
	 */
	@RequestMapping("/show")
	public String show(Model model) {
		List<Readerpicset> reslist = readerpicsetService.getAll();
		model.addAttribute("reslist", reslist);
		return "readerpicset/view";
	}
	
	/**
	 * 修改读者画像展示设置的状态
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(String typeidarr){
		try{
			readerpicsetService.modifyStatus(typeidarr);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		
		return "{result:true}";
	}
}
