package cn.gov.nlc.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CurrentTimeController {

	@RequestMapping("/gettime")
	@ResponseBody
	public String getNowTime() {
		return "{\"result\":true,\"message\":\"" + System.currentTimeMillis() + "\"}";
	}

}
