package cn.gov.nlc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Olcchotword;
import cn.gov.nlc.service.HotwordService;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.OlcchotwordService;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/olcchotword")
public class OlcchotwordController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.OlcchotwordController.class);
	
	@Autowired
	private OlcchotwordService olcchotwordService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	
	/**
	 * 跳转到热词
	 */
	@RequestMapping("/show")
	public String show(HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");
		
		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看olcc热词");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "olcchotword/view";
	}
	
	/**
	 * 热词list
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getOrderList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows) {
		EasyUiDataGridResult result = olcchotwordService.getHotwordList(page, rows);
		return result;
	}
	
	/**
	 * 根据id删除公告，单个删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = olcchotwordService.deleteSingleById(id);

		if (result == 0) { // 删除失败
			return "{result:false}";
		} else { // 删除成功
			return "{result:true}";
		}
	}
	
	/**
	 * 删除多个
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public String deleteAll(String ids) {
		if(StringUtils.isBlank(ids)) {
			return "{result:false}";
		}
		
		String[] strArr = ids.split(",");
		for (String str : strArr) {
			olcchotwordService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 修改热词值及其排序
	 * @param nlcadmin
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveModi")
	@ResponseBody
	public String login(HttpServletRequest request) {
		List<Olcchotword> reslist = new ArrayList<Olcchotword>();
		String json = request.getParameter("json");
		JSONArray jsonArray = JSONArray.fromObject(json);
		for(int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = JSONObject.fromObject(jsonArray.get(i));
			Olcchotword ele = new Olcchotword();
			
			Object objId = object.get("id");
			if(objId instanceof String) {
				ele.setId(Integer.valueOf((String)objId));
			}else if(objId instanceof Integer) {
				ele.setId((Integer)object.get("id"));
			}
			
			Object objSort = object.get("sort");
			if(objSort instanceof String) {
				ele.setSort(Integer.valueOf((String)objSort));
			}else if(objId instanceof Integer) {
				ele.setSort((Integer)object.get("sort"));
			}
			
			String hotword = (String)object.get("hotword");
			ele.setHotword(hotword);
			reslist.add(ele);
		}
		
		try {
			olcchotwordService.saveModi(reslist);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
}
