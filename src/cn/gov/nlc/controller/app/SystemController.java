package cn.gov.nlc.controller.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Nlcads;
import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.Nlcsuggestion;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.service.NlcadsService;
import cn.gov.nlc.service.NlcnewsService;
import cn.gov.nlc.service.NlcnoticeService;
import cn.gov.nlc.service.NlcsubjectService;
import cn.gov.nlc.service.NlcsuggestionService;
import cn.gov.nlc.service.NlctrailerService;
import cn.gov.nlc.service.PictureService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.WjreaderService;
import cn.gov.nlc.vo.ResultVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class SystemController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.SystemController.class);

	@Autowired
	SysmessageService sysmessageService;

	// 意见反馈
	@Autowired
	private NlcsuggestionService nlcsuggestionService;

	@Autowired
	private NlcadsService nlcadsService;

	@Autowired
	private WjreaderService wjreaderService;

	@Autowired
	private NlcnewsService nlcnewsService;

	@Autowired
	private NlcnoticeService nlcnoticeService;

	@Autowired
	private NlctrailerService nlctrailerService;

	@Autowired
	private NlcsubjectService nlcsubjectService;

	@Autowired
	private PictureService pictureService;

	/**
	 * 首页-->广告位
	 * 
	 * @return
	 */
	@RequestMapping("/ads")
	@ResponseBody
	public List<Nlcads> getAppAds() {
		return nlcadsService.getAppNlcAds();
	}

	/**
	 * 首页-->文津数据
	 * 
	 * @return
	 */
	@RequestMapping("/sharepic")
	@ResponseBody
	public JSONObject getSharePic() {
		JSONObject json = new JSONObject();
		json.put("result", true);
		json.put("src", pictureService.getPictureByName("共享图片"));
		return json;
	}

	/**
	 * 首页-->文津数据
	 * 
	 * @return
	 */
	@RequestMapping("/wenjin/pic")
	@ResponseBody
	public JSONObject getWenJin() {
		JSONObject json = new JSONObject();
		/* json.put("result", true); */
		json.put("result", pictureService.getPictureByName("文津经典诵读"));
		return json;
	}

	/**
	 * 首页文津经典诵读pad版的图
	 * 
	 * @return
	 */
	@RequestMapping("/wenjin/pic_pad")
	@ResponseBody
	public JSONObject getWenJin_pad() {
		JSONObject json = new JSONObject();
		/* json.put("result", true); */
		json.put("result", pictureService.getPictureByName("文津经典诵读pad"));
		return json;
	}

	/**
	 * 首页-->文津数据
	 * 
	 * @return
	 */
	@RequestMapping("/wenjin/today")
	@ResponseBody
	public Wjreader getWenJinToday() {
		return wjreaderService.getWjreaderToday();
	}

	/**
	 * 首页-->文津数据
	 * 
	 * @return
	 */
	@RequestMapping("/wenjin/yestoday")
	@ResponseBody
	public Wjreader getWjreaderYestoday() {
		return wjreaderService.getWjreaderYestoday();
	}

	@RequestMapping("/middleinfo")
	@ResponseBody
	public JSONArray getMiddleInfomation() {
		List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> mapNews = nlcnewsService.getIndexNews();
		if(null != mapNews) {
			mapNews.put("type", "news");
			infoList.add(mapNews);
		}
		Map<String, Object> mapNotice = nlcnoticeService.getIndexNotice();
		if(null != mapNotice) {
			mapNotice.put("type", "notice");
			infoList.add(mapNotice);
		}

		Map<String, Object> mapTrailer = nlctrailerService.getIndexTrailer();
		if(null != mapTrailer) {
			mapTrailer.put("type", "trailer");
			infoList.add(mapTrailer);
		}
		
		JSONArray j = JSONArray.fromObject(infoList);
		return j;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/recsubject")
	@ResponseBody
	public List<Nlcsubject> getIndexSubject() {
		List<Nlcsubject> result = nlcsubjectService.getIndexSubject();
		return result;
	}

	@RequestMapping("/sysmessage/count/{clientid}")
	@ResponseBody
	public JSONObject getGTSysmessagecount(@PathVariable String clientid, HttpServletRequest request) {
		String loginAccount = "";
		Object attribute = request.getAttribute("loginAccount");
		if(null != attribute) {
			loginAccount = attribute.toString();
		}
		
		int result = sysmessageService.getSysmessageCountByType(0, clientid);
		
		if (StringUtils.isNotBlank(loginAccount)) {	//登录用户
			int count2 = sysmessageService.getSysmessageCountByTypeAndAccount(8, loginAccount, clientid);	//登录之后获取意见回复条数
			result += count2;
		}else {	//未登录用户获取意见回复
			int count2 = sysmessageService.getSysmessageCountByTypeAndAccount(8, clientid, clientid);	//登录之后获取意见回复条数
			result += count2;
		}
		
		return JSONObject.fromObject("{\"result\":" + result + "}");
	}

	@RequestMapping("/sysmessage/list/{clientid}")
	@ResponseBody
	public List<Sysmessage> getGTSysmessage(@PathVariable String clientid, HttpServletRequest request) {
		String loginAccount = "";
		Object attribute = request.getAttribute("loginAccount");
		if(null != attribute) {
			loginAccount = attribute.toString();
		}
		List<Sysmessage> result = new ArrayList<Sysmessage>();
		
		List<Sysmessage> list1 = sysmessageService.getSysmessage(0, clientid);
		if(null != list1 && list1.size() > 0) {
			result.addAll(list1);
		}
		
		if(StringUtils.isNotBlank(loginAccount)) {	//登录用户
			List<Sysmessage> list2 = sysmessageService.getSysmessageByAccount(8, loginAccount, clientid);
			if(null != list2 && list2.size() > 0) {
				result.addAll(list2);
			}
		}else {	//未登录用户
			List<Sysmessage> list2 = sysmessageService.getSysmessageByAccount(8, clientid, clientid);
			if(null != list2 && list2.size() > 0) {
				result.addAll(list2);
			}
		}
		
		return result;
	}

	@RequestMapping("/user/message/count/get/{type}")
	@ResponseBody
	public JSONObject getSysmessagecount(@PathVariable String type, HttpServletRequest request, String clientid) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		int iType = 0;
		try {
			iType = Integer.parseInt(type);
		} catch (Exception e) {

		}
		int result = sysmessageService.getSysmessageCountByTypeAndAccount(iType, loginAccount, clientid);
		return JSONObject.fromObject("{\"result\":" + result + "}");
	}

	@RequestMapping("/user/message/get/{type}")
	@ResponseBody
	public List<Sysmessage> getSysmessage(@PathVariable String type, HttpServletRequest request, String clientid) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		int iType = 0;
		try {
			iType = Integer.parseInt(type);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		List<Sysmessage> result = sysmessageService.getSysmessageByAccount(iType, loginAccount, clientid);
		return result;
	}

	/**
	 * 前端的意见反馈，存入数据库
	 */
	@RequestMapping("/suggestion/insert")
	@ResponseBody
	public JSONObject insertSuggestion(Nlcsuggestion nlcsuggestion) {
		try {
			logger.info("【意见反馈】" + nlcsuggestion);
			String username = nlcsuggestion.getUsername();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String currTime = sdf1.format(new Date());

			nlcsuggestion.setUid(username + currTime);
			nlcsuggestion.setStatus("未回复");
			nlcsuggestion.setAdminname("");
			nlcsuggestionService.insertSuggestion(nlcsuggestion);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/**
	 * 通过username查找意见的list
	 */
	@RequestMapping("/suggestion/getlist")
	@ResponseBody
	public ResultVo getSuggestion(String username) {
		List<Nlcsuggestion> list = nlcsuggestionService.getListByUsername(username);
		ResultVo rs = new ResultVo();
		rs.setResult(true);
		rs.setList(list);
		return rs;
	}

}
