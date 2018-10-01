package cn.gov.nlc.controller.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jit.client.sp.AssertClient;
import com.jit.client.sp.bean.AccessResult;
import com.jit.client.sp.bean.LoginResult;
import com.jit.client.sp.model.LoginProcessUtil;
import cn.gov.nlc.pojo.Appstatist;
import cn.gov.nlc.pojo.Binduser;
import cn.gov.nlc.pojo.Holdrecord;
import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.pojo.Nlcuserloginlog;
import cn.gov.nlc.pojo.Nlcuseronline;
import cn.gov.nlc.pojo.Olcchotword;
import cn.gov.nlc.pojo.Renewrecord;
import cn.gov.nlc.pojo.Useralert;
import cn.gov.nlc.service.AppstatistService;
import cn.gov.nlc.service.BinduserService;
import cn.gov.nlc.service.HoldrecordService;
import cn.gov.nlc.service.HotwordService;
import cn.gov.nlc.service.NlcuserService;
import cn.gov.nlc.service.NlcuserloginlogService;
import cn.gov.nlc.service.NlcuseronlineService;
import cn.gov.nlc.service.OlcchotwordService;
import cn.gov.nlc.service.OrderinfoService;
import cn.gov.nlc.service.RenewrecordService;
import cn.gov.nlc.service.SsoService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.UseralertService;
import cn.gov.nlc.service.UsernormalwordService;
import cn.gov.nlc.aleph.Aleph;
import cn.gov.nlc.aleph.Find;
import cn.gov.nlc.aleph.Json;
import cn.gov.nlc.aleph.OlccSearch;
import cn.gov.nlc.aleph.OpacSearch;
import cn.gov.nlc.util.AliPay;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.NlcMobile;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.util.RSAUtils;
import cn.gov.nlc.vo.ResultData;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class NlcuserController {

	@Autowired
	private NlcuserService nlcuserService;

	@Autowired
	private NlcuseronlineService nlcuseronlineService;

	@Autowired
	private NlcuserloginlogService nlcuserloginlogService;

	@Autowired
	private AppstatistService appstatistService;

	@Autowired
	private NlcMobile nlcMobile;

	@Autowired
	private AliPay aliPay;

	@Autowired
	HoldrecordService holdrecordService;

	@Autowired
	RenewrecordService renewrecordService;

	@Autowired
	HotwordService hotwordService;

	@Autowired
	UsernormalwordService usernormalwordService;

	@Autowired
	UseralertService useralertService;

	@Autowired
	BinduserService binduserService;

	@Autowired
	private OlcchotwordService olcchotwordService;
	@Autowired
	private SsoService ssoService;

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.NlcuserController.class);
	private static final String newssourl = PropertiesUtils.getPropertyValue("newssourl");

	@RequestMapping(value = "/user/chklogin")
	@ResponseBody
	public JSONObject loginByToken(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		Common.appendMethod("/root/chklogin", "login:"+loginAccount);
		JSONObject retJson = new JSONObject();
		Nlcuser nlcuser = nlcuserService.getByAccount(loginAccount);
		retJson.put("result", true);
		retJson.put("username", nlcuser.getLoginaccount());
		retJson.put("name", nlcuser.getName());
		return retJson;
	}

	@RequestMapping(value = "/applog")
	@ResponseBody
	public void appLog(HttpServletRequest request, @RequestBody Appstatist appstatist) {
		appstatist.setAddress(Common.getClientIP(request));
		appstatist.setTime(new Date());
		appstatist.setLocation(Common.getIpLocation(Common.getClientIP(request)));
		appstatist.setAppid(request.getAttribute("appid").toString());
		appstatistService.insertAppstatist(appstatist);
	}

	@RequestMapping(value = "/user/loginlog")
	@ResponseBody
	public void loginLog(HttpServletRequest request, @RequestBody Nlcuserloginlog userloginlog) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		userloginlog.setUsername(loginAccount);
		userloginlog.setAddress(Common.getClientIP(request));
		userloginlog.setAppid(request.getAttribute("appid").toString());
		userloginlog.setLocation(Common.getIpLocation(Common.getClientIP(request)));
		userloginlog.setTime(new Date());
		try {
			nlcuserloginlogService.insertNlcuserloginlog(userloginlog);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 获取最新的token信息/*切换的时候用的比较多
	 * 
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/user/sso-login")
	@ResponseBody
	public String ssoLogin(HttpServletRequest req, HttpServletResponse resp) {
		String ticket = req.getParameter("ticket");
		// 调用单点登录接口
		AccessResult accessResult = new AssertClient().requestAssertion(ticket);
		JSON json = null;
		String returnObj = "";
		if (accessResult.getResult() != 200) {
			json = JSONObject.fromObject(accessResult);
		} else {
			LoginResult loginResult = LoginProcessUtil.getLoginResult(accessResult, ticket);
			json = JSONObject.fromObject(loginResult);
		}
		returnObj = json.toString();
		return returnObj;
	}

	/**
	 * 在借信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/borinfo")
	@ResponseBody
	public JSONObject bor_info(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String loginAccount = request.getAttribute("loginAccount").toString();
		json.put("result", true);
		json.put("data", Aleph.borinfo(loginAccount));
		return json;
	}

	/**
	 * 续借
	 * 
	 * @param request
	 *            http://ram19:8995/X?op=renew&doc_number=000000444&
	 *            item_sequence=000010&bor_id=1975&library=usm50
	 * 
	 * @return
	 */
	@RequestMapping("/user/renew")
	@ResponseBody
	public Json renew(String barcode, HttpServletRequest request) {
		String borId = request.getAttribute("borId").toString();
		String loginAccount = request.getAttribute("loginAccount").toString();
		Json json = Aleph.renew(barcode, borId);
		if (json.isSuccess()) {
			Renewrecord renewrecord = new Renewrecord();
			renewrecord.setLoginaccount(loginAccount);
			renewrecord.setBarcode(barcode);
			renewrecord.setTime(new Date());
			renewrecordService.insertRenewrecord(renewrecord);
		}
		return json;

	}

	//预约
	@RequestMapping("/user/holdreq")
	@ResponseBody
	public Json requestHold(String barcode, HttpServletRequest request, String placecode) {
		String borId = request.getAttribute("borId").toString();
		String loginAccount = request.getAttribute("loginAccount").toString();
		logger.info(barcode + "--->" + borId);
		Json json = OpacSearch.hold_req(barcode, borId, placecode);
		if (json.isSuccess()) {
			Holdrecord holdrecord = new Holdrecord();
			holdrecord.setLoginaccount(loginAccount);
			holdrecord.setBarcode(barcode);
			holdrecord.setTime(new Date());
			holdrecordService.insertHoldrecord(holdrecord);
		}
		return json;
	}

	/**
	 * 预约信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/curhold")
	@ResponseBody
	public JSONObject currentHold(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		JSONObject json = new JSONObject();
		json.put("result", true);
		json.put("data", Aleph.currentHold(loginAccount));
		return json;
	}
	
	/**
	 * 判断base是nlc09外文库和sub_library_code=WWJC的时候，点击预约会调用此查询借书地点接口
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/enholdselectlocation")
	@ResponseBody
	public JSONObject enHoldSelectLocation(HttpServletRequest request, String item_status_code) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		return Aleph.enHoldSelectLocation(loginAccount, item_status_code);
	}

	/**
	 * 取消预约 这个不用base
	 * 
	 * @param request
	 *            http://10.1.235.19:8991/X?op=hold-req-cancel&item_barcode=
	 *            32044024520025&bor_id=8888888510728049&library=nlc50&user_name
	 *            =www-app&user_password=pwdapp
	 * @return
	 */
	@RequestMapping("/user/canholdreq")
	@ResponseBody
	public Json cancelHold(String docnum, String itemseq, String seq, HttpServletRequest request) {
		String borId = request.getAttribute("borId").toString();
		return Aleph.cancelrequest(borId, docnum, itemseq, seq);
	}

	/**
	 * delayCash
	 * 
	 * @param requestBody
	 * @param request
	 * @param response,怎么获取未交的滞纳金事务
	 */

	@RequestMapping("/user/delaycash/detail")
	@ResponseBody
	public JSONObject delayCashDetail(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		return JSONObject.fromObject("{\"result\":true,\"data\":" + Aleph.delayCashDetail(loginAccount) + "}");
	}

	@RequestMapping("/user/delaycash")
	@ResponseBody
	public JSONObject delayCash(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		return JSONObject.fromObject("{\"result\":true,\"count\":\"" + Aleph.delayCash(loginAccount) + "\"}");
	}

	/**
	 * 交押金回写
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/regAccount.do")
	public void registerAccount(@RequestBody String requestBody, HttpServletRequest request,
			HttpServletResponse response) {

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/user/loanhistory")
	@ResponseBody
	public JSONObject loanHistory(HttpServletRequest request) {
		String borId = request.getAttribute("borId").toString();
		return Aleph.loan_history(borId);
	}

	@RequestMapping(value = "/usercash")
	public void userCash(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username, // required如果为false参数可传可不传，true必须传
			@RequestParam(value = "password", required = true) String password, ModelMap viewMap) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String strRet = getFile("usercash");
		writer.print(strRet);
		writer.flush();
		writer.close();
	}

	private String getFile(String type) {
		String filepath = "";
		switch (type) {
		case "loanhistory":
			filepath = "D:\\Study\\cnki文档\\国图项目\\整理\\loanhistory.xml";
			break;
		case "currentloan":
			filepath = "D:\\Study\\cnki文档\\国图项目\\整理\\currentloan.xml";
			break;
		case "":
			filepath = "";
			break;
		}
		FileReader fr = null;
		StringBuilder sbRet = new StringBuilder();
		try {
			fr = new FileReader(filepath);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while (s != null) {
				sbRet.append(s);
				s = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return sbRet.toString();

	}

	@RequestMapping("/opac/books")
	@ResponseBody
	public JSON opacBooks(HttpServletRequest request, @RequestParam String query, @RequestParam String find_base,
			@RequestParam String adjacent, @RequestParam int pageSize, @RequestParam int page) {
		//logger.info("【opac/books接口】的请求：" + query + "  " + find_base + "  " + pageSize + "  " + page);
		String loginAccount = request.getAttribute("loginAccount") == null ? ""
				: request.getAttribute("loginAccount").toString();
		JSONObject json = JSONObject.fromObject(query);
		String key = "";
		@SuppressWarnings("unchecked")
		Iterator<String> it = json.keys();
		while (it.hasNext()) {
			key = it.next();
			if ("WSL".equals(key) || "WYR".equals(key) || "WFM".equalsIgnoreCase(key))
				continue;
			int id = hotwordService.getHotwordID(json.getString(key));
			if (id == -1) {
				Hotword hotword = new Hotword();
				hotword.setHotword(json.getString(key));
				hotword.setSeacount(1);
				hotword.setSort(10000);
				try {
					hotwordService.insertHotword(hotword);
				} catch (Exception e) {

				}
			} else {
				try {
					hotwordService.updSeaCount(id);
				} catch (Exception e) {

				}
			}
			if (!Common.IsNullOrEmpty(loginAccount)) {
				usernormalwordService.setUsernormalword(loginAccount, json.getString(key));
			}
		}
		List<Find> jFind = OpacSearch.searchMoreBase(json, find_base, adjacent, pageSize, page);
		if (jFind.size() == 1) {
			JSON opacPresent = OpacSearch.opacPresent(jFind.get(0), pageSize, page);
			//logger.info("【opac/books接口】的响应：" + opacPresent);
			return opacPresent;
		} else{
			JSONObject job = new JSONObject();
			job.put("count", 0);
			job.put("data", new ArrayList());
			return job;
			//return JSONArray.fromObject(jFind);
		}
	}
	
	/**
	 * 中英文库搜索的总数
	 * @param query
	 * @return
	 */
	@RequestMapping("/opac/alllibbookcount")
	@ResponseBody
	public JSON opacAllLibBookCount(@RequestParam String query) {
		logger.info("【opac全库条数的请求】:"+query);
		JSONObject json = JSONObject.fromObject(query);
		int result = OpacSearch.getAllLibCount(json);
		JSONObject job = new JSONObject();
		job.put("count", result);
		logger.info("【opac全库条数的响应】："+job);
		return job;
	}
	
	/**
	 * 查询馆藏数
	 * @param base
	 * @param docnum
	 * @return
	 */
	@RequestMapping("/opac/collectnum")
	@ResponseBody
	public JSON collectNum(HttpServletRequest request, @RequestParam String query, @RequestParam String find_base) {
		JSONObject json = JSONObject.fromObject(query);
		Find find = OpacSearch.find(json, find_base, "Y");
		JSONObject job = new JSONObject();
		if(null != find) {
			job.put("count", find.getNo_records());
		}else {
			job.put("count", 0);
		}
		return job;
	}

	@RequestMapping("/opac/nlcitem")
	@ResponseBody
	public JSON opacBooks(@RequestParam String base, @RequestParam String docnum) {
		return OpacSearch.doitem(base, docnum);
	}

	@RequestMapping("/olcc/books")
	@ResponseBody
	public JSON olccBooks(HttpServletRequest request, @RequestParam String query, @RequestParam String find_base,
			@RequestParam String adjacent, @RequestParam int pageSize, @RequestParam int page) {
		String loginAccount = request.getAttribute("loginAccount") == null ? ""
				: request.getAttribute("loginAccount").toString();
		JSONObject json = JSONObject.fromObject(query);
		String key = "";
		@SuppressWarnings("unchecked")
		Iterator<String> it = json.keys();
		while (it.hasNext()) {
			key = it.next();
			if ("WSL".equals(key) || "WYR".equals(key) || "WFM".equalsIgnoreCase(key))
				continue;
			int id = olcchotwordService.getHotwordID(json.getString(key));
			if (id == -1) {
				Olcchotword hotword = new Olcchotword();
				hotword.setHotword(json.getString(key));
				hotword.setSeacount(1);
				hotword.setSort(10000);
				try {
					olcchotwordService.insertHotword(hotword);
				} catch (Exception e) {

				}
			} else {
				try {
					olcchotwordService.updSeaCount(id);
				} catch (Exception e) {

				}
			}
			if (!Common.IsNullOrEmpty(loginAccount)) {
				usernormalwordService.setUsernormalword(loginAccount, json.getString(key));
			}
		}
		if (find_base.split(",").length > 1) {
			List<Map<String, String>> jFind = OlccSearch.searchMoreBase(json, find_base, adjacent, pageSize, page);
			logger.info("【olcc/books选择两个库的响应：】" + jFind);
			return JSONArray.fromObject(jFind);
		} else {
			return OlccSearch.showRedults(json, find_base, adjacent, pageSize, page);
		}
	}
	
	/**
	 * 中英文库搜索的总数
	 * @param query
	 * @return
	 */
	@RequestMapping("/olcc/alllibbookcount")
	@ResponseBody
	public JSON olccAllLibBookCount(@RequestParam String query) {
		JSONObject json = JSONObject.fromObject(query);
		int result = OlccSearch.getAllLibCount(json);
		JSONObject job = new JSONObject();
		job.put("count", result);
		return job;
	}

	@RequestMapping("/olcc/nlcitem")
	@ResponseBody
	public JSON olccBooks(@RequestParam String base, @RequestParam String docnum) {
		return OlccSearch.item_date(base, docnum);
	}

	/**
	 * national为中文外借，foreign为外文外借，0为需要交钱，1为有外借权限不需要交钱。  中文外借押金为100.00，外文外借押金为1000.00 
	 * 如果返回-1，即为调取阿里夫获取权限失败。缴纳押金按钮置灰。不需要交钱
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/pledgecash")
	@ResponseBody
	public JSON pledgeCashRight(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String loginAccount = request.getAttribute("loginAccount").toString();
		int[] arrRight = Aleph.pledgeCashRight(loginAccount);
		json.put("national", arrRight[0]);
		json.put("foreign", arrRight[1]);
		return json;
	}

	/**
	 * 生成订单信息，订单号是临时生成，保存在订单表
	 * {"result":true,"payment":0,"orderinfo":""}  需要缴纳返回的信息
	 * {"result":true,"payment":1}  已经缴纳返回的信息
	 * @param request
	 * @param type：national，foreign
	 * @return
	 */
	@RequestMapping("/user/orderinfo")
	@ResponseBody
	public JSONObject getOrderInfo(HttpServletRequest request, @RequestParam String type) {
		JSONObject result = new JSONObject();
		boolean bFlag = true;
		String loginAccount = request.getAttribute("loginAccount").toString();
		String borId = request.getAttribute("borId").toString();
		int[] arrRight = new int[] { 0, 0 };
		try {
			arrRight = Aleph.pledgeCashRight(loginAccount);
		} catch (Exception e) {
			result.put("result", false);
			result.put("message", e.getMessage());
			return result;
		}
		if ("national".equalsIgnoreCase(type)) {
			if (arrRight[0] == 1) {
				bFlag = false;
			}
		} else if ("foreign".equalsIgnoreCase(type)) {
			if (arrRight[1] == 1) {
				bFlag = false;
			}
		}
		if (!bFlag) {
			result.put("result", true);
			result.put("payment", 1);
		} else {
			String orderInfo = aliPay.createOrderInfo(loginAccount, type, borId);
			if (!Common.IsNullOrEmpty(orderInfo)) {
				result.put("result", true);
				result.put("payment", 0);
				result.put("orderinfo", orderInfo);
			} else {
				result.put("result", false);
				result.put("message", "create orderinfo fail");
			}
		}
		return result;
	}

	
/*	public static void main(String[] args) {
		String type = "foreign";
		JSONObject jsono = new JSONObject();
		jsono.put("orderno", "aaaaaaabbbb");	//商户订单号
		jsono.put("tradeno", "ssssssssss8888888");	//支付宝订单号
		jsono.put("date", (new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
		
		
		if ("national".equalsIgnoreCase(type)) {
			jsono.put("item", "中文押金");
			jsono.put("sum", PropertiesUtils.getPropertyValue("national"));
		} else if ("foreign".equalsIgnoreCase(type)) {
			jsono.put("item", "英文押金");
			jsono.put("sum", PropertiesUtils.getPropertyValue("foreign"));
		}
		System.out.println(jsono);
	}*/
	
	@RequestMapping("/mobile/{id}")
	public void goMobile(@PathVariable String id) {
		// nlcMobile.getNlcMobileNews(@PathVariable String id);
		// nlcMobile.getNlcMobileNotice();
		// nlcMobile.getMobileJzyg();
		// nlcMobile.getNlcSubject();
		// nlcMobile.getTodayWenJin();
		nlcMobile.getWenJinContent(id);
	}

	@RequestMapping("/user/loginout")
	@ResponseBody
	public JSON loginout(HttpServletRequest request, @RequestParam String clientid) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		logger.info("loginAccount:" + loginAccount + "-->clientid=" + clientid);
		try {
			nlcuseronlineService.deleteOnlineUser(loginAccount, clientid);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}


	/**
	 * 
	 * type第三方登录的类型  qq是qq，微信是wx，微博是wb
	 * unionid 是微信的一个值
	 * @param request
	 * @param username
	 * @param gender
	 * @param icon
	 * @param clientid
	 * @param baseos
	 * @param type
	 * @return
	 */
	@RequestMapping("/thirdlogin")
	@ResponseBody
	public JSONObject thirdLogin(HttpServletRequest request, String openid, String username,
			String gender,String icon,String clientid,String baseos,
			String thirdtype, String unionid) {
		if(StringUtils.isBlank(openid)) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		if("(null)".equals(openid.trim())) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		
		JSONObject jsonRet = new JSONObject();
		String ip = request.getRemoteAddr();
		String location = Common.getIpLocation(ip);
		Binduser binduser = binduserService.getNlcuserinfo(openid);
		if (binduser != null) {
			jsonRet.put("result", true);
			jsonRet.put("username", binduser.getLoginaccount());
			String dbpassword = binduser.getEnpassword();
			byte[] priarray = RSAUtils.hexStringToByteArray(dbpassword);
			
			try {
				byte[] promessage = RSAUtils.decryptByPrivateKey(priarray, binduser.getPrikey());
				jsonRet.put("password", new String(promessage, "utf-8"));
			}catch (Exception e) {
				return JSONObject.fromObject("{\"result\":false}");
			}
			return jsonRet;
		}
		
		Nlcuser nlcuser = new Nlcuser();
		nlcuser.setBaseos(baseos);
		nlcuser.setLoginaccount(openid);
		nlcuser.setSextype(gender);
		nlcuser.setName(username);
		nlcuser.setIcon(icon);
		nlcuser.setLocation(location);

		if (!nlcuserService.isExistNlcuser(openid)) {
			nlcuser.setRdrolecode("0000");
			nlcuser.setInserttime(new Date());
			nlcuser.setAliasstatus("1");
			nlcuser.setThirdtype(thirdtype);
			nlcuser.setUnionid(unionid);
			try {
				nlcuserService.insertNlcuser(nlcuser);
			} catch (Exception e) {
				logger.error("【第三方登录时插入nlcuser失败】： " + openid + "  " + e);
				return JSONObject.fromObject("{\"result\":false}");
			}
		}else {
			nlcuser.setUpdatetime(new Date());
			nlcuserService.updateNlcuser(nlcuser);
		}
		
		
		String strToken = nlcuserService.CreateUserToken(openid);
		Nlcuseronline nlcuseronline = new Nlcuseronline();
		nlcuseronline.setLoginaccount(openid);
		nlcuseronline.setToken(strToken);
		nlcuseronline.setTime(new Date());
		nlcuseronline.setClientid(clientid);
		nlcuseronline.setAddress(ip);
		nlcuseronline.setLocation(location);
		try {
			if (!nlcuseronlineService.isExistUserOnline(openid, clientid)) {
				nlcuseronlineService.addNlcuseronline(nlcuseronline);
			} else {
				nlcuseronlineService.updateNlcuseronline(nlcuseronline);
			}

		} catch (Exception ex) {
			logger.error("【第三方登录时插入或更新nlcuseronline失败】： " + openid);
			return JSONObject.fromObject("{\"result\":false}");
		}
		
		Nlcuser dbNlcuser = nlcuserService.getByAccount(openid);
		jsonRet.put("result", "200");
		jsonRet.put("account", openid);
		jsonRet.put("role", dbNlcuser.getRdrolecode());
		jsonRet.put("token", strToken);
		jsonRet.put("commName", username);
		jsonRet.put("aliasstatus", dbNlcuser.getAliasstatus());
		if("JS0001".equals(dbNlcuser.getRdrolecode())) {
			jsonRet.put("cardno", dbNlcuser.getCardno());
			jsonRet.put("realname", dbNlcuser.getName());
		}
		jsonRet.put("method", "thirdpart");	//标识来自第三方登录
		if(StringUtils.isNotBlank(unionid)) {
			ssoService.thirdLoginLog(unionid, thirdtype);
		}else {
			ssoService.thirdLoginLog(openid, thirdtype);
		}
		return jsonRet;
	}
	

	/**
	 * thirdtype第三方登录的类型  qq是qq，微信是wx，微博是wb
	 * @return
	 */
	@RequestMapping("/user/bind")
	@ResponseBody
	public JSONObject bindThirdUser(HttpServletRequest request, String username, String password) {
		String openid = request.getAttribute("loginAccount").toString();
		Nlcuser dbNlcuser = nlcuserService.getByAccount(openid);
		String unionid = dbNlcuser.getUnionid();
		String thirdtype = dbNlcuser.getThirdtype();
		Binduser binduser = new Binduser();
		binduser.setLoginaccount(username);
		binduser.setToken(openid);
		try {
			Map<String, String> keypair = RSAUtils.genKeyPair();
			String pubkey = RSAUtils.getPublicKey(keypair);
			String prikey = RSAUtils.getPrivateKey(keypair);
			binduser.setPrikey(prikey);
			byte[] enmessage = RSAUtils.encryptByPublicKey(password.getBytes(), pubkey);
			String dbpassword = RSAUtils.bytesToHexString(enmessage);
			binduser.setEnpassword(dbpassword);
			binduser.setTime(new Date());
			binduserService.insertBinduser(binduser);
			
			if("wx".equals(thirdtype)) {
				com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
				jsonparam.put("unionID", unionid);
				jsonparam.put("rcardid", username);
				String url = newssourl + "/sso/foreign/thirdPartyLoginManager/bindRcardIdWX";
				String resstr = Common.sendPostForm(url, jsonparam);
				logger.info("【微信绑定读者卡时，发送unionid实现sso端的绑定的返回：】 " + resstr);
			}
			
			return JSONObject.fromObject("{\"result\":true}");
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
	}

	@RequestMapping("/user/alert")
	@ResponseBody
	public JSONObject setUseralert(HttpServletRequest request, @RequestParam String type, @RequestParam String fileid,
			@RequestParam String title, @RequestParam String endtime) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		Useralert useralert = new Useralert();
		useralert.setUsername(loginAccount);
		useralert.setFileid(fileid);
		useralert.setType(type);
		useralert.setTitle(title);
		useralert.setEndtime(Common.ConvertToDate(endtime, "yyyy-MM-dd"));
		useralert.setTime(new Date());
		try {
			useralertService.insertUseralert(useralert);
			return JSONObject.fromObject("{\"result\":true}");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return JSONObject.fromObject("{\"result\":false}");
	}

	@RequestMapping("/user/chkalert")
	@ResponseBody
	public JSONObject setChkUseralert(HttpServletRequest request, @RequestParam String type,
			@RequestParam String fileid) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			boolean exist = useralertService.existThisAlert(loginAccount, type, fileid);
			if (exist)
				return JSONObject.fromObject("{\"result\":true}");
			else
				return JSONObject.fromObject("{\"result\":false}");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return JSONObject.fromObject("{\"result\":false}");
	}

	@RequestMapping("/user/calalert")
	@ResponseBody
	public JSONObject cancelUseralert(HttpServletRequest request, @RequestParam String type,
			@RequestParam String fileid) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			boolean cancel = useralertService.cancelUseralert(loginAccount, type, fileid);
			if (cancel)
				return JSONObject.fromObject("{\"result\":true}");
			else
				return JSONObject.fromObject("{\"result\":false}");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return JSONObject.fromObject("{\"result\":false}");
	}

	/**
	 * 获取热词
	 * 
	 * @return
	 */
	@RequestMapping("/hotword")
	@ResponseBody
	public ResultData getHotword() {
		List<Hotword> list = hotwordService.getHotword();
		ResultData rd = new ResultData();
		rd.setResult(true);
		rd.setData(list);
		return rd;
	}
	
	/**
	 * olcc获取热词
	 */
	@RequestMapping("/olcchotword")
	@ResponseBody
	public ResultData getOlcchotword() {
		List<Olcchotword> list = olcchotwordService.getHotword();
		ResultData rd = new ResultData();
		rd.setResult(true);
		rd.setData(list);
		return rd;
	}
	
	/**
	 * 更改推送状态
	 * @param request
	 * @param aliasstatus   0是关闭，1是开启
	 * @return
	 */
	@RequestMapping("/user/aliasadjust")
	@ResponseBody
	public JSONObject aliasAdjust(HttpServletRequest request, @RequestParam String aliasstatus) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			Nlcuser dbNlcuser = nlcuserService.getByAccount(loginAccount);
			dbNlcuser.setAliasstatus(aliasstatus);
			nlcuserService.updateNlcuser(dbNlcuser);
			return JSONObject.fromObject("{\"result\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return JSONObject.fromObject("{\"result\":false}");
	}
}
