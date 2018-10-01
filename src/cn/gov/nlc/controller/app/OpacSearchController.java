package cn.gov.nlc.controller.app;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.aleph.EveryOpacSearch;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class OpacSearchController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.OpacSearchController.class);

	/**
	 * 查询所有分馆的馆藏数
	 * @param base
	 * @param docnum
	 * @return
	 */
	@RequestMapping("/opac/everycollectnum")
	@ResponseBody
	public JSON collectNum(HttpServletRequest request, @RequestParam String query, @RequestParam String find_base) {
		logger.info("【查询所有分馆的馆藏数请求】" + query + "  " + find_base);
		JSONObject json = JSONObject.fromObject(query);
		JSONArray result = EveryOpacSearch.everyFind(json, find_base, "Y");
		logger.info("【查询所有分馆的馆藏数响应】" + result);
		return result;
	}
}
