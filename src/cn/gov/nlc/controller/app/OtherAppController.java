package cn.gov.nlc.controller.app;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Thirdapp;
import cn.gov.nlc.service.ThirdappService;
import cn.gov.nlc.vo.ResultVo;

@Controller
@RequestMapping("/app")
public class OtherAppController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.OtherAppController.class);
	
	@Autowired
	private ThirdappService thirdappService;
	
	@RequestMapping("/thirdapp/get")
	@ResponseBody
	public ResultVo getThirdapp(String os) {
		ResultVo rs = new ResultVo();
		List<Thirdapp> list = thirdappService.getByOs(os);
		rs.setResult(true);
		rs.setList(list);
		return rs;
	}
}
