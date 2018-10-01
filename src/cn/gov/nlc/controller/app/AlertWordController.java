package cn.gov.nlc.controller.app;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class AlertWordController {

	@RequestMapping(value = "/alertword")
	@ResponseBody
	public JSONObject alertWord() {
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("one", "默认登录状态");
		resMap.put("two", "应该是实名用户");
		resMap.put("three", "应该是读者卡用户");
		resMap.put("four", "应该是实名用户或读者卡用户");
		resMap.put("five", "应该是虚拟用户");
		resMap.put("six", "应该是实名用户或虚拟用户");
		resMap.put("seven", "应该是读者卡用户或虚拟用户");
		
		return null;
	}
}
