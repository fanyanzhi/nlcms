package cn.gov.nlc.controller.app;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.aleph.Aleph;
import cn.gov.nlc.util.PropertiesUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class AlephController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.AlephController.class);
	private static String staff_id = PropertiesUtil.getPropValue("properties/configuration.properties", "staff_id");
	private static String station_id = PropertiesUtil.getPropValue("properties/configuration.properties", "station_id");
	
	//获取外借信息
	@RequestMapping("/user/outborrowinfo")
	@ResponseBody
	public JSONObject getOutBorrow(HttpServletRequest request, String barcode) {
		String borId = request.getAttribute("borId").toString();
		return Aleph.outBorrowInfo(borId, barcode, staff_id, station_id);
	}
	
}
