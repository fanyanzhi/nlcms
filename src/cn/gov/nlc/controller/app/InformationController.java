package cn.gov.nlc.controller.app;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.NlcnewsExt;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.NlcnoticeExt;
import cn.gov.nlc.pojo.NlcnoticeVo;
import cn.gov.nlc.pojo.Nlcnoticeannexdown;
import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.NlcsubjectExt;
import cn.gov.nlc.pojo.Nlcsubjectcatalog;
import cn.gov.nlc.pojo.Nlctemplate;
import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.NlctrailerExt;
import cn.gov.nlc.pojo.Shareinfo;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.service.NlcnewsService;
import cn.gov.nlc.service.NlcnoticeService;
import cn.gov.nlc.service.NlcnoticeannexdownService;
import cn.gov.nlc.service.NlcsubjectService;
import cn.gov.nlc.service.NlcsubjectcatalogService;
import cn.gov.nlc.service.NlctemplateService;
import cn.gov.nlc.service.NlctrailerService;
import cn.gov.nlc.service.ShareinfoService;
import cn.gov.nlc.service.WjreaderService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.ResultNotice;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class InformationController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.InformationController.class);

	@Autowired
	private NlcnewsService nlcnewsService;

	@Autowired
	private NlcnoticeService nlcnoticeService;

	@Autowired
	private NlctrailerService nlctrailerService;

	@Autowired
	private NlcsubjectService nlcsubjectService;

	@Autowired
	private NlcsubjectcatalogService nlcsubjectcatalogService;

	@Autowired
	private WjreaderService wjreaderService;

	@Autowired
	private NlctemplateService nlctemplateService;

	@Autowired
	private NlcnoticeannexdownService nlcnoticeannexdownService;

	@Autowired
	private ShareinfoService shareinfoService;

	/*
	 * 新闻相关
	 */
	@RequestMapping("/news/get")
	@ResponseBody
	public EasyUiDataGridResult getNlcnews(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		EasyUiDataGridResult result = nlcnewsService.getnewsList(page, rows);
		// JSONOB
		return result;
	}

	@RequestMapping("/news/{id}")
	@ResponseBody
	public Nlcnews getNlcnewsDetail(@PathVariable String id) {
		Nlcnews result = nlcnewsService.getNlcnewsByNewsId(id);
		return result;
	}

	@RequestMapping("/news/search")
	@ResponseBody
	public List<Nlcnews> getNlcnewsBySearch(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, String keyword) {
		List<Nlcnews> result = nlcnewsService.seaNewsList(page, rows, keyword);
		return result;
	}

	@RequestMapping("/user/news/iscollect/{id}")
	@ResponseBody
	public JSONObject isExistNewsCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (nlcnewsService.isExistCollect(id, loginAccount)) {
			return JSONObject.fromObject("{\"result\":true}");
		} else {
			return JSONObject.fromObject("{\"result\":false}");
		}
	}

	/**
	 * @param id
	 * @param loginAccount
	 */
	@RequestMapping("/user/news/addcollect/{id}")
	@ResponseBody
	public JSONObject addNewsCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (!nlcnewsService.isExistCollect(id, loginAccount)) {
			try {
				nlcnewsService.addNewsCollect(id, loginAccount);
			} catch (Exception e) {
				return JSONObject.fromObject("{\"result\":false}");
			}
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/**
	 * @param id
	 * @param loginAccount
	 */
	@RequestMapping("/user/news/cancollect/{id}")
	@ResponseBody
	public JSONObject cancelNewsCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcnewsService.cancelNewsCollect(id, loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/user/news/mycollect")
	@ResponseBody
	public List<NlcnewsExt> myNewsCollect(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		List<NlcnewsExt> result = nlcnewsService.getUserCollect(loginAccount);
		return result;
	}

	@RequestMapping("/user/news/ispraise/{id}")
	@ResponseBody
	public JSONObject isExistNewsPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (nlcnewsService.isExistPraise(id, loginAccount)) {
			return JSONObject.fromObject("{\"result\":true}");
		}
		return JSONObject.fromObject("{\"result\":false}");
	}

	@RequestMapping("/user/news/praise/{id}")
	@ResponseBody
	public JSONObject addNewsPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (!nlcnewsService.isExistPraise(id, loginAccount)) {
			try {
				nlcnewsService.addNewsPraise(id, loginAccount);
				nlcnewsService.updatePraiseCount(id);
				return JSONObject.fromObject("{\"result\":true}");
			} catch (Exception e) {
				return JSONObject.fromObject("{\"result\":false}");
			}
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/user/news/canpraise/{id}")
	@ResponseBody
	public JSONObject cancelNewsPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcnewsService.cancleNewsPraise(id, loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/user/news/mypraise")
	@ResponseBody
	public List<NlcnewsExt> myNewsPraise(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		List<NlcnewsExt> result = nlcnewsService.getUserPraise(loginAccount);
		return result;
	}

	@RequestMapping("/user/news/praise/clear")
	@ResponseBody
	public JSONObject clearNewspraise(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcnewsService.clearPraise(loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("news/related/{id}")
	@ResponseBody
	public List<Nlcnews> getrelatedNews(@PathVariable String id) {
		return nlcnewsService.getRelatedNews(id);
	}

	/*
	 * 公告相关
	 * 
	 * @RequestMapping("/notice/get")
	 * 
	 * @ResponseBody public EasyUiDataGridResult getNoticeList(int page, int
	 * rows, NlcnoticeExt nlcnoticeExt) { EasyUiDataGridResult j =
	 * nlcnoticeService.getAppNoticeList(page, rows); return j; }
	 */
	/*
	 * 公告相关
	 */
	@RequestMapping("/notice/get")
	@ResponseBody
	public ResultNotice getNoticeList(int page, int rows) {
		ResultNotice vo = new ResultNotice();
		EasyUiDataGridResult j = nlcnoticeService.getAppNoticeList(page, rows);
		vo.setResult(true);
		vo.setCount(j.getTotal() + "");
		vo.setRows(j.getRows());
		return vo;
	}

	@RequestMapping("/notice/{id}")
	@ResponseBody
	public NlcnoticeVo getNoticeDetail(@PathVariable String id) {
		NlcnoticeVo result = nlcnoticeService.selectByNoticeId(id);
		return result;
	}

	@RequestMapping("/notice/search")
	@ResponseBody
	public List<Nlcnotice> getNlcnoticeBySearch(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, @RequestParam String keyword) {
		List<Nlcnotice> result = nlcnoticeService.seaNoticeList(page, rows, keyword);
		return result;
	}

	@RequestMapping("/user/notice/iscollect/{id}")
	@ResponseBody
	public JSONObject isExistNoticesCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (nlcnoticeService.isExistCollect(id, loginAccount)) {
			return JSONObject.fromObject("{\"result\":true}");
		} else {
			return JSONObject.fromObject("{\"result\":false}");
		}
	}

	/**
	 * 需要判断id是不是空
	 * 
	 * @param id
	 * @param loginAccount
	 */
	@RequestMapping("/user/notice/addcollect/{id}")
	@ResponseBody
	public JSONObject addNoticeCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (!nlcnoticeService.isExistCollect(id, loginAccount)) {
			try {
				nlcnoticeService.addNoticeCollect(id, loginAccount);
			} catch (Exception e) {
				return JSONObject.fromObject("{\"result\":false}");
			}
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/**
	 * 需要判断id是不是空
	 * 
	 * @param id
	 * @param loginAccount
	 */
	@RequestMapping("/user/notice/cancollect/{id}")
	@ResponseBody
	public JSONObject cancelNoticeCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcnoticeService.cancelNoticeCollect(id, loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/user/notice/mycollect")
	@ResponseBody
	public List<NlcnoticeExt> mynoticeCollect(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		return nlcnoticeService.getUserCollect(loginAccount);
	}

	@RequestMapping("/user/notice/ispraise/{id}")
	@ResponseBody
	public JSONObject isExistNoticePraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (nlcnoticeService.isExistPraise(id, loginAccount)) {
			return JSONObject.fromObject("{\"result\":true}");
		}
		return JSONObject.fromObject("{\"result\":false}");
	}

	@RequestMapping("/user/notice/praise/{id}")
	@ResponseBody
	public JSONObject addNoticePraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (!nlcnoticeService.isExistPraise(id, loginAccount)) {
			try {
				nlcnoticeService.addNoticePraise(id, loginAccount);
				nlcnoticeService.updatePraiseCount(id);
				return JSONObject.fromObject("{\"result\":true}");
			} catch (Exception e) {

			}
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");

	}

	@RequestMapping("/user/notice/canpraise/{id}")
	@ResponseBody
	public JSONObject cancelNoticePraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcnoticeService.cancleNewsPraise(id, loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/user/notice/mypraise")
	@ResponseBody
	public List<NlcnoticeExt> myNoticePraise(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		List<NlcnoticeExt> result = nlcnoticeService.getUserPraise(loginAccount);
		return result;
	}

	@RequestMapping("/user/notice/praise/clear")
	@ResponseBody
	public JSONObject clearNoticepraise(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcnoticeService.clearPraise(loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/*
	 * 讲座相关
	 */
	@RequestMapping("/trailer/get")
	@ResponseBody
	public EasyUiDataGridResult getTrailerList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, @RequestParam(required = false) String guanqu,
			@RequestParam(defaultValue = "0", required = false) Integer days) {
		EasyUiDataGridResult j = nlctrailerService.getAppTrailer(page, rows, guanqu, days);
		return j;
	}

	@RequestMapping("/trailer/{id}")
	@ResponseBody
	public Nlctrailer getTrailerDetail(@PathVariable String id) {
		Nlctrailer result = nlctrailerService.selectByTrailerId(id);
		return result;
	}

	@RequestMapping("/trailer/search")
	@ResponseBody
	public List<Nlctrailer> getNlcTrailerBySearch(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, @RequestParam String keyword) {
		List<Nlctrailer> result = nlctrailerService.seaTrailerList(page, rows, keyword);
		return result;
	}

	@RequestMapping("/user/trailer/iscollect/{id}")
	@ResponseBody
	public JSONObject isExistTrailerCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (nlctrailerService.isExistCollect(id, loginAccount)) {
			return JSONObject.fromObject("{\"result\":true}");
		} else {
			return JSONObject.fromObject("{\"result\":false}");
		}
	}

	@RequestMapping("/trailerannex/down")
	@ResponseBody
	public JSONObject downTrailerannex(@RequestParam String url, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount") == null ? ""
				: request.getAttribute("loginAccount").toString();
		Nlcnoticeannexdown nlcnoticeannexdown = new Nlcnoticeannexdown();
		if (!Common.IsNullOrEmpty(loginAccount)) {
			nlcnoticeannexdown.setUsername(loginAccount);
		}
		nlcnoticeannexdown.setUrl(url);
		nlcnoticeannexdown.setDowncount(1);
		nlcnoticeannexdown.setTime(new Date());
		try {
			nlcnoticeannexdownService.insertNlcnoticeannexdown(nlcnoticeannexdown);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/**
	 * 需要判断id是不是空
	 * 
	 * @param id
	 * @param loginAccount
	 */
	@RequestMapping("/user/trailer/addcollect/{id}")
	@ResponseBody
	public JSONObject addTrailerCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (!nlctrailerService.isExistCollect(id, loginAccount)) {
			try {
				nlctrailerService.addTrailerCollect(id, loginAccount);
			} catch (Exception e) {
				return JSONObject.fromObject("{\"result\":false}");
			}
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/**
	 * 需要判断id是不是空 取消收藏
	 * 
	 * @param id
	 * @param loginAccount
	 */
	@RequestMapping("/user/trailer/cancollect/{id}")
	@ResponseBody
	public JSONObject cancelTrailerCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlctrailerService.cancelCollect(id, loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/user/trailer/mycollect")
	@ResponseBody
	public List<NlctrailerExt> mytrailerCollect(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		return nlctrailerService.getUserCollect(loginAccount);
	}

	@RequestMapping("/user/trailer/ispraise/{id}")
	@ResponseBody
	public JSONObject isExistTrailerPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (nlctrailerService.isExistPraise(id, loginAccount)) {
			return JSONObject.fromObject("{\"result\":true}");
		}
		return JSONObject.fromObject("{\"result\":false}");
	}

	@RequestMapping("/user/trailer/praise/{id}")
	@ResponseBody
	public JSONObject addTrailerPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (!nlctrailerService.isExistPraise(id, loginAccount)) {
			try {
				nlctrailerService.addTrailerPraise(id, loginAccount);
				nlctrailerService.updatePraiseCount(id);
				return JSONObject.fromObject("{\"result\":true}");
			} catch (Exception e) {
				return JSONObject.fromObject("{\"result\":false}");
			}
		}
		return JSONObject.fromObject("{\"result\":true}");

	}

	@RequestMapping("/user/trailer/canpraise/{id}")
	@ResponseBody
	public JSONObject cancelTrailerPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlctrailerService.cancleTrailerPraise(id, loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/user/trailer/mypraise")
	@ResponseBody
	public List<NlctrailerExt> myTrailerPraise(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		List<NlctrailerExt> result = nlctrailerService.getUserPraise(loginAccount);
		return result;
	}

	@RequestMapping("/user/trailer/praise/clear")
	@ResponseBody
	public JSONObject clearTrailerpraise(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlctrailerService.clearPraise(loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/*
	 * 专题相关
	 */
	@RequestMapping("/subject/get")
	@ResponseBody
	public EasyUiDataGridResult getSubjectList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		EasyUiDataGridResult result = nlcsubjectService.getSubjectListWithBlobtt(page, rows);
		return result;
	}

	@RequestMapping("/subject/getancestor")
	@ResponseBody
	public List<Nlcsubjectcatalog> getAllAncestor(String catalogid) {
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogService.getAllAncestor(catalogid);
		return list;
	}

	@RequestMapping("/subject/getallleaves")
	@ResponseBody
	public List<Nlcsubjectcatalog> getAllLeaf(String subjectid) {
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogService.getAllLeaf(subjectid, "and");
		return list;
	}

	@RequestMapping("/subject/getallleavesnocontent")
	@ResponseBody
	public List<Nlcsubjectcatalog> getAllLeafWithOutContent(String subjectid) {
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogService.getAllLeaf(subjectid, "ios");
		return list;
	}

	@RequestMapping("/subject/{id}")
	@ResponseBody
	public Nlcsubject getSubjectIntro(@PathVariable String id) {
		Nlcsubject result = nlcsubjectService.getNlcSubjectBySubjectId(id);
		return result;
	}

	@RequestMapping("/subject/search")
	@ResponseBody
	public List<Nlcsubject> getNlcSubjectBySearch(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, @RequestParam String keyword) {
		List<Nlcsubject> result = nlcsubjectService.seaSubjectList(page, rows, keyword);
		return result;
	}

	@RequestMapping("/user/subject/iscollect/{id}")
	@ResponseBody
	public JSONObject isExistSubjectCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (nlcsubjectService.isExistCollect(id, loginAccount)) {
			return JSONObject.fromObject("{\"result\":true}");
		} else {
			return JSONObject.fromObject("{\"result\":false}");
		}
	}

	/**
	 * 需要判断id是不是空
	 * 
	 * @param id
	 * @param loginAccount
	 */
	@RequestMapping("/user/subject/addcollect/{id}")
	@ResponseBody
	public JSONObject addSubjectCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (!nlcsubjectService.isExistCollect(id, loginAccount)) {
			try {
				nlcsubjectService.addSubjectCollect(id, loginAccount);
			} catch (Exception e) {
				return JSONObject.fromObject("{\"result\":false}");
			}
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/**
	 * 需要判断id是不是空 取消收藏
	 * 
	 * @param id
	 * @param loginAccount
	 */
	@RequestMapping("/user/subject/cancollect/{id}")
	@ResponseBody
	public JSONObject cancelSubjectCollect(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcsubjectService.cancelCollect(id, loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/user/subject/mycollect")
	@ResponseBody
	public List<NlcsubjectExt> mysubjectCollect(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		return nlcsubjectService.getUserCollect(loginAccount);
	}

	@RequestMapping("/user/subject/ispraise/{id}")
	@ResponseBody
	public JSONObject isExistSubjectPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (nlcsubjectService.isExistPraise(id, loginAccount)) {
			return JSONObject.fromObject("{\"result\":true}");
		}
		return JSONObject.fromObject("{\"result\":false}");
	}

	@RequestMapping("/user/subject/praise/{id}")
	@ResponseBody
	public JSONObject addSubjectPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		if (!nlcsubjectService.isExistPraise(id, loginAccount)) {
			try {
				nlcsubjectService.addSubjectPraise(id, loginAccount);
				nlcsubjectService.updatePraiseCount(id);
				return JSONObject.fromObject("{\"result\":true}");
			} catch (Exception e) {
				return JSONObject.fromObject("{\"result\":false}");
			}
		}
		return JSONObject.fromObject("{\"result\":true}");

	}

	@RequestMapping("/user/subject/canpraise/{id}")
	@ResponseBody
	public JSONObject cancelSubjectPraise(@PathVariable String id, HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcsubjectService.cancleSubjectPraise(id, loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	/*
	 * @RequestMapping("/subject/catalog/{id}")
	 * 
	 * @ResponseBody public List<Nlcsubjectcatalog>
	 * getSubjctcatalog(@PathVariable String id) { return
	 * nlcsubjectcatalogService.findBySubjectidWithoutDown(id); }
	 */

	@RequestMapping("/subject/catalog")
	@ResponseBody
	public List<Nlcsubjectcatalog> getSubjctcatalog(String subjectid, String catalogid) {
		if (StringUtils.isBlank(catalogid)) {
			catalogid = "root";
		}
		return nlcsubjectcatalogService.findCatalog(subjectid, catalogid);
	}

	@RequestMapping("/subject/praisecount/{id}")
	@ResponseBody
	public JSONObject getSubjectpraisecount(@PathVariable String id) {
		JSONObject json = new JSONObject();
		int count = nlcsubjectService.getPraiseCount(id);
		json.put("result", true);
		json.put("count", count);
		return json;
	}

	@RequestMapping("/subject/content/{id}")
	@ResponseBody
	public Nlcsubjectcatalog getCatalogContent(@PathVariable String id) {
		return nlcsubjectcatalogService.getCatalongContentByCatalogid(id);
	}

	@RequestMapping("/wenjin/search")
	@ResponseBody
	public List<Wjreader> getNlcWenJinBySearch(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, @RequestParam String keyword) {
		List<Wjreader> result = nlcsubjectService.seaWenJinList(page, rows, keyword);
		return result;
	}

	@RequestMapping("/user/subject/mypraise")
	@ResponseBody
	public List<NlcsubjectExt> mySubjectPraise(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		List<NlcsubjectExt> result = nlcsubjectService.getUserPraise(loginAccount);
		return result;
	}

	@RequestMapping("/user/subject/praise/clear")
	@ResponseBody
	public JSONObject clearSubjectpraise(HttpServletRequest request) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		try {
			nlcsubjectService.clearPraise(loginAccount);
		} catch (Exception e) {
			return JSONObject.fromObject("{\"result\":false}");
		}
		return JSONObject.fromObject("{\"result\":true}");
	}

	@RequestMapping("/wjreader/get")
	@ResponseBody
	public JSONObject getWjreader(@RequestParam String date) {
		JSONObject result = new JSONObject();
		Wjreader wjreader = null;
		try {
			wjreader = wjreaderService.getWjreader(date);
			result.put("result", true);
		} catch (Exception e) {
			result.put("result", false);
		}
		if (wjreader == null) {
			result.put("count", 0);
		} else {
			result.put("count", 1);
			result.put("data", wjreader);
		}
		return result;
	}

	@RequestMapping("/wjreader/style")
	@ResponseBody
	public JSONObject getWjreaderStyle() {
		JSONObject result = new JSONObject();
		List<Nlctemplate> lst = null;
		try {
			lst = nlctemplateService.getNlctemplate();
		} catch (Exception e) {
			result.put("result", false);
		}
		if (lst == null) {
			result.put("result", true);
			result.put("count", 0);
		} else {
			result.put("result", true);
			result.put("count", lst.size());
			result.put("data", lst);
		}
		return result;
	}

	@RequestMapping("/info/share")
	@ResponseBody
	public void setShareInfo(HttpServletRequest request, @RequestParam String type, @RequestParam String infoid,
			@RequestParam String platform) {
		try {
			String loginAccount = request.getAttribute("loginAccount") == null ? ""
					: request.getAttribute("loginAccount").toString();
			Shareinfo shareinfo = new Shareinfo();
			shareinfo.setPlatform(platform);
			shareinfo.setInfoid(infoid);
			shareinfo.setTime(new Date());
			shareinfo.setType(type);
			if (!Common.IsNullOrEmpty(loginAccount)) {
				shareinfo.setUsername(loginAccount);
			}
			shareinfoService.insertShareinfo(shareinfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
