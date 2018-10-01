package cn.gov.nlc.controller.app;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Clouddoc;
import cn.gov.nlc.pojo.Userfavorite;
import cn.gov.nlc.service.ClouddocService;
import cn.gov.nlc.service.UserfavoriteService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class CnkiDocumentController {

	@Autowired
	UserfavoriteService userfavoriteService;

	@Autowired
	ClouddocService clouddocService;

	/**
	 * 添加我的收藏
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/favorite/add")
	@ResponseBody
	public JSONObject addUserfavorite(HttpServletRequest request, @RequestBody Userfavorite userfavorite) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		userfavorite.setUsername(loginAccount);
		userfavorite.setAppid(request.getAttribute("appid").toString());
		userfavorite.setTime(new Date());
		int id = userfavoriteService.getFavoriteID(loginAccount, userfavorite.getOdatatype(), userfavorite.getFileid());
		try {
			if (id > -1) {
				return JSONObject.fromObject("{\"result\":true,\"id\":\"" + id + "\"}");
			} else {
				userfavoriteService.addFavoriteInfo(userfavorite);
				id = userfavoriteService.getFavoriteID(loginAccount, userfavorite.getOdatatype(),
						userfavorite.getFileid());
			}
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true,\"id\":\"" + id + "\"}");
	}

	/**
	 * 取消我的收藏
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/favorite/cancel/{id}")
	@ResponseBody
	public JSONObject cancelUserfavorite(HttpServletRequest request, @PathVariable String id) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			userfavoriteService.delFavorite(loginAccount, id);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/**
	 * 判断我的收藏
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/favorite/check")
	@ResponseBody
	public JSONObject chkUserfavorite(HttpServletRequest request, @RequestParam(required = true) String odatatype,
			@RequestParam(required = true) String fileid) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			if (userfavoriteService.checkUserFavorite(loginAccount, odatatype, fileid)) {
				return JSONObject.fromObject("{\"result\":true,\"status\":\"1\"}");
			} else {
				return JSONObject.fromObject("{\"result\":true,\"status\":\"0\"}");
			}
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false,\"message\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 我收藏的文献数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/favorite/count")
	@ResponseBody
	public JSONObject getUserfavoriteCount(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		int iCount = 0;
		try {
			iCount = userfavoriteService.getFavoriteCount(loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false,\"message\":\"" + e.getMessage() + "\"}");
		}
		return JSONObject.fromObject("{\"result\":true,\"count\":\"" + iCount + "\"}");
	}

	/**
	 * 获取我的收藏
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/favorite/get")
	@ResponseBody
	public JSONObject getUserfavorite(HttpServletRequest request, @RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		JSONObject json = new JSONObject();
		int istart = 1;
		int ilength = 10000;
		if (Common.IsNullOrEmpty(start) || (Common.IsNullOrEmpty(length))) {
			istart = 1;
			ilength = 10000;
		} else {
			try {
				istart = Integer.parseInt(start);
				ilength = Integer.parseInt(length);
			} catch (Exception e) {
				istart = 1;
				ilength = 10000;
			}
		}
		try {
			EasyUiDataGridResult result = userfavoriteService.getFavoriteList(loginAccount, istart, ilength);
			json.put("result", true);
			json.put("count", result.getTotal());
			json.put("rows", result.getRows());

		} catch (Exception e) {
			json.put("result", false);
			json.put("message", e.getMessage());
		}
		return json;
	}

	/********************** 云文档同步 ******************************************************/

	/**
	 * 添加我的云文档
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/doc/add")
	@ResponseBody
	public JSONObject addClouddoc(HttpServletRequest request, @RequestBody Clouddoc clouddoc) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		clouddoc.setAppid(request.getAttribute("appid").toString());
		clouddoc.setUsername(loginAccount);
		clouddoc.setTime(new Date());
		clouddoc.setSync(1);
		try {
			clouddocService.insertClouddoc(clouddoc);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping(value = "/user/doc/get")
	@ResponseBody
	public JSONObject getClouddoc(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String loginAccount = request.getAttribute("loginAccount").toString();
		EasyUiDataGridResult result = null;
		try {
			result = clouddocService.getClouddoc(loginAccount);
			json.put("result", true);
			json.put("count", result.getTotal());
			json.put("rows", result.getRows());
		} catch (Exception e) {
			json.put("result", false);
			json.put("message", e.getMessage());
		}
		return json;
	}

	@RequestMapping(value = "/user/doc/update")
	@ResponseBody
	public JSONObject updateClouddoc(HttpServletRequest request, @RequestBody Clouddoc clouddoc) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			clouddocService.updateClouddoc(loginAccount, clouddoc);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

}
