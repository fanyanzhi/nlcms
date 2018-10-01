package cn.gov.nlc.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.pojo.WjreaderExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.WjreaderService;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/wjreader")
public class WjreaderController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.WjreaderController.class);
	
	@Autowired
	private WjreaderService wjreaderService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	
	/**
	 * 跳转到文津内容管理页面
	 * @return
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
		nlcadminlog.setOperate("查看文津诵读管理页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "wjreader/view";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getWjreaderList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, WjreaderExt wjreaderExt) {
		EasyUiDataGridResult result = wjreaderService.getWjreaderList(page, rows, wjreaderExt);
		return result;
	}
	
	/**
	 * 文津发布
	 * status 1发布，0取消
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public String shelf(Integer id, String status) {
		try{
			wjreaderService.publish(id, status);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 根据id删除，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = wjreaderService.deleteSingleById(id);
		
		if(result == 0) {	//删除失败
			return "{result:false}";
		}else {				//删除成功
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
			wjreaderService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 拉取文津
	 */
	@RequestMapping("/pull")
	@ResponseBody
	public JSONObject pull(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try{
			result = wjreaderService.pull();
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("result", false);
			result.put("message", "服务器错误");
		}
		return result;
	}
	
	@RequestMapping("/add")
	public String add() {
		return "wjreader/add";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String insert(Wjreader wjreader) {
		try {
			wjreader.setPid("ff808081519e421901519edfb677013e");
			String status = wjreader.getStatus();
			if("已发布".equals(status)) {
				wjreader.setBkpubtime(new Date());
			}
			
			Date wjdate = wjreader.getWjdate();
			DateTime dt = new DateTime(wjdate);
			wjreader.setWjyear(dt.getYear()+"");
			wjreader.setWjmonth(dt.getMonthOfYear()+"");
			wjreaderService.insertWjreader(wjreader);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Wjreader wjreader = wjreaderService.selectByPrimaryKey(id);
		model.addAttribute("wjreader", wjreader);
		return "wjreader/update";
	}
	
	@RequestMapping("/updateobj")
	@ResponseBody
	public String updateObj(HttpServletRequest request) {
		try{
			Map<String, String[]> parameterMap = request.getParameterMap();
			wjreaderService.updateObj(parameterMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 新增、编辑页面的预览
	 */
	@RequestMapping("/addPreview")
	public String addPreview() {
		return "wjreader/addPreview";
	}
	
	/**
	 * 在管理中的预览
	 */
	@RequestMapping("/itemPreview")
	public String itemPreview(Integer id, Model model) {
		Wjreader wjreader = wjreaderService.selectByPrimaryKey(id);
		model.addAttribute("wjreader", wjreader);
		return "wjreader/itemPreview";
	}
}
