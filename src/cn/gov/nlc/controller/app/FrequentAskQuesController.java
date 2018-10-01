package cn.gov.nlc.controller.app;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Faq;
import cn.gov.nlc.service.FaqService;
import cn.gov.nlc.vo.ResultVo;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class FrequentAskQuesController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.FrequentAskQuesController.class);
	
	@Autowired
	private FaqService faqService;
	
	@RequestMapping("/faq/get")
	@ResponseBody
	public ResultVo getFaq() {
		ResultVo rs = new ResultVo();
		List<Faq> list = faqService.getAllList();
		rs.setResult(true);
		rs.setList(list);
		return rs;
	}
	
}
