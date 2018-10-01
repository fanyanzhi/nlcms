package cn.gov.nlc.controller.app;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Browselistenbook;
import cn.gov.nlc.pojo.Browsereadtx;
import cn.gov.nlc.pojo.Collectlistenbook;
import cn.gov.nlc.pojo.Collectreadtx;
import cn.gov.nlc.pojo.Downlistenbook;
import cn.gov.nlc.pojo.Downreadtx;
import cn.gov.nlc.pojo.Listenbook;
import cn.gov.nlc.pojo.Readtx;
import cn.gov.nlc.service.BrowselistenbookService;
import cn.gov.nlc.service.BrowsereadtxService;
import cn.gov.nlc.service.CollectlistenbookService;
import cn.gov.nlc.service.CollectreadtxService;
import cn.gov.nlc.service.DownlistenbookService;
import cn.gov.nlc.service.DownreadtxService;
import cn.gov.nlc.service.ListenbookService;
import cn.gov.nlc.service.ReadtxService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class CollectBookController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.CollectBookController.class);

	@Autowired
	ReadtxService readtxService;

	@Autowired
	CollectreadtxService collectreadtxService;

	@Autowired
	DownreadtxService downreadtxService;

	@Autowired
	ListenbookService listenbookService;

	@Autowired
	CollectlistenbookService collectlistenbookService;

	@Autowired
	DownlistenbookService downlistenbookService;

	@Autowired
	BrowselistenbookService browselistenbookService;

	@Autowired
	BrowsereadtxService browsereadtxService;

	/***
	 * 有问题，有时间需要修改
	 */

	/**
	 * 收藏读览天下
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/readtx/collect")
	@ResponseBody
	public JSONObject setReadTianXia(HttpServletRequest request, String status, @RequestBody Readtx readtx) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		JSONObject json = new JSONObject();

		try {
			if ("1".equals(status)) { // 收藏
				if (!readtxService.existReadtx(readtx.getMagazineid())) {
					readtxService.insertReadtx(readtx);
				}

				if (collectreadtxService.existCollectreadtx(loginAccount, readtx.getMagazineid())) {
					collectreadtxService.updateStatus(loginAccount, readtx.getMagazineid(), 1); // 存在就更新
				} else { // 不存在就插入
					Collectreadtx collectreadtx = new Collectreadtx();
					collectreadtx.setMagazineid(readtx.getMagazineid());
					collectreadtx.setUsername(loginAccount);
					collectreadtx.setTitle(readtx.getTitle());
					collectreadtx.setStatus(1);
					collectreadtx.setTime(new Date());
					collectreadtxService.insertCollectreadtx(collectreadtx);
				}
			} else { // 取消收藏
				if (collectreadtxService.existCollectreadtx(loginAccount, readtx.getMagazineid())) {
					collectreadtxService.updateStatus(loginAccount, readtx.getMagazineid(), 0); // 存在就更新
				}
			}
			json.put("result", true);

		} catch (Exception e) {
			json.put("result", false);
		}
		return json;
	}

	/**
	 * 获取收藏读览天下列表
	 * 
	 * @return
	 */
	@RequestMapping("/user/readtx/collectlist")
	@ResponseBody
	public JSONObject getReadTianXia(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			List<Readtx> list = collectreadtxService.getCollectreadtx(loginAccount);
			json.put("result", true);
			json.put("data", list);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			json.put("result", false);
		}
		return json;
	}

	/**
	 * 下载读览天下
	 * 
	 * @return
	 */
	@RequestMapping("/user/readtx/down")
	@ResponseBody
	public JSONObject setDownReadTianXia(HttpServletRequest request, String status, @RequestBody Readtx readtx) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		JSONObject json = new JSONObject();

		try {
			if ("1".equals(status)) { // 下载
				if (!readtxService.existReadtx(readtx.getMagazineid())) {
					readtxService.insertReadtx(readtx);
				}

				if (downreadtxService.existDownreadtx(loginAccount, readtx.getMagazineid())) {
					downreadtxService.updateStatus(loginAccount, readtx.getMagazineid(), 1); // 存在就更新
				} else { // 不存在就插入
					Downreadtx downreadtx = new Downreadtx();
					downreadtx.setMagazineid(readtx.getMagazineid());
					downreadtx.setTitle(readtx.getTitle());
					downreadtx.setUsername(loginAccount);
					downreadtx.setStatus(1);
					downreadtx.setTime(new Date());
					downreadtxService.insertDownreadtx(downreadtx);
				}
			} else { // 删除下载
				if (downreadtxService.existDownreadtx(loginAccount, readtx.getMagazineid())) {
					downreadtxService.updateStatus(loginAccount, readtx.getMagazineid(), 0); // 存在就更新
				}
			}
			json.put("result", true);

		} catch (Exception e) {
			json.put("result", false);
		}

		return json;
	}

	/**
	 * 获取下载读览天下列表
	 * 
	 * @return
	 */
	@RequestMapping("/user/readtx/downlist")
	@ResponseBody
	public JSONObject setReadTianXia(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			List<Readtx> list = downreadtxService.getDownreadtx(loginAccount);
			json.put("result", true);
			json.put("data", list);
		} catch (Exception e) {
			json.put("result", false);
		}
		return json;
	}

	/**
	 * 浏览读览天下
	 * 
	 * @return
	 */
	@RequestMapping("/user/readtx/browse")
	@ResponseBody
	public JSONObject setBrowseReadTianXia(HttpServletRequest request, @RequestBody Readtx readtx) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		Browsereadtx browsereadtx = new Browsereadtx();
		browsereadtx.setMagazineid(readtx.getMagazineid());
		browsereadtx.setTitle(readtx.getTitle());
		browsereadtx.setUsername(loginAccount);
		browsereadtx.setTime(new Date());
		JSONObject json = new JSONObject();
		try {
			if (!readtxService.existReadtx(readtx.getMagazineid()))
				readtxService.insertReadtx(readtx);
			browsereadtxService.insertBrowsereadtx(browsereadtx);
			json.put("result", true);
		} catch (Exception e) {
			json.put("result", false);
		}
		return json;
	}

	/********************************************************************/

	/**
	 * 收藏听书
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/listenbook/collect")
	@ResponseBody
	public JSONObject setListenbook(HttpServletRequest request, String status, @RequestBody Listenbook listenbook) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		JSONObject json = new JSONObject();

		try {
			if ("1".equals(status)) { // 收藏
				if (!listenbookService.existListenbook(listenbook.getBookid())) {
					listenbookService.insertListenbook(listenbook);
				}

				if (collectlistenbookService.existcollectlistenbook(loginAccount, listenbook.getBookid())) {
					collectlistenbookService.updateStatus(loginAccount, listenbook.getBookid(), 1); // 存在就更新
				} else { // 不存在就插入
					Collectlistenbook collectlistenbook = new Collectlistenbook();
					collectlistenbook.setBookid(listenbook.getBookid());
					collectlistenbook.setTitle(listenbook.getBookname());
					collectlistenbook.setUsername(loginAccount);
					collectlistenbook.setTime(new Date());
					collectlistenbook.setStatus(1);
					collectlistenbookService.insertCollectlistenbook(collectlistenbook);
				}
			} else { // 取消收藏
				if (collectlistenbookService.existcollectlistenbook(loginAccount, listenbook.getBookid())) {
					collectlistenbookService.updateStatus(loginAccount, listenbook.getBookid(), 0); // 存在就更新
				}
			}
			json.put("result", true);

		} catch (Exception e) {
			json.put("result", false);
		}
		return json;
	}

	/**
	 * 获取收藏听书列表
	 * 
	 * @return
	 */
	@RequestMapping("/user/listenbook/collectlist")
	@ResponseBody
	public JSONObject getListenbook(HttpServletRequest request) {
		JSONObject json = new JSONObject();

		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			List<Listenbook> list = collectlistenbookService.getCollectlistenbook(loginAccount);
			json.put("result", true);
			json.put("data", list);
		} catch (Exception e) {
			json.put("result", false);
		}
		return json;
	}

	/**
	 * 下载听书
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/listenbook/down")
	@ResponseBody
	public JSONObject setDownListenbook(HttpServletRequest request, String status, @RequestBody Listenbook listenbook) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		JSONObject json = new JSONObject();

		try {
			if ("1".equals(status)) { // 下载
				if (!listenbookService.existListenbook(listenbook.getBookid())) {
					listenbookService.insertListenbook(listenbook);
				}

				if (downlistenbookService.existdownlistenbook(loginAccount, listenbook.getBookid())) {
					downlistenbookService.updateStatus(loginAccount, listenbook.getBookid(), 1); // 存在就更新
				} else { // 不存在就插入
					Downlistenbook downlistenbook = new Downlistenbook();
					downlistenbook.setBookid(listenbook.getBookid());
					downlistenbook.setUsername(loginAccount);
					downlistenbook.setTime(new Date());
					downlistenbook.setStatus(1);
					downlistenbookService.insertDownlistenbook(downlistenbook);
				}
			} else { // 取消收藏
				if (downlistenbookService.existdownlistenbook(loginAccount, listenbook.getBookid())) {
					downlistenbookService.updateStatus(loginAccount, listenbook.getBookid(), 0); // 存在就更新
				}
			}
			json.put("result", true);

		} catch (Exception e) {
			json.put("result", false);
		}
		return json;
	}

	/**
	 * 获取下载听书列表
	 * 
	 * @return
	 */
	@RequestMapping("/user/listenbook/downlist")
	@ResponseBody
	public JSONObject getDownListenbook(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			List<Listenbook> list = downlistenbookService.getDownlistenbook(loginAccount);
			json.put("result", true);
			json.put("data", list);
		} catch (Exception e) {
			json.put("result", false);
		}
		return json;
	}

	/**
	 * 浏览读览天下
	 * 
	 * @return
	 */
	@RequestMapping("/user/listenbook/browse")
	@ResponseBody
	public JSONObject setBrowseListenbook(HttpServletRequest request, String status,
			@RequestBody Listenbook listenbook) {
		String loginAccount = request.getAttribute("loginAccount").toString();
		Browselistenbook browselistenbook = new Browselistenbook();
		browselistenbook.setBookid(listenbook.getBookid());
		browselistenbook.setUsername(loginAccount);
		browselistenbook.setTime(new Date());
		JSONObject json = new JSONObject();
		try {
			if (!listenbookService.existListenbook(listenbook.getBookid()))
				listenbookService.insertListenbook(listenbook);
			browselistenbookService.insertBrowselistenbook(browselistenbook);
			json.put("result", true);
		} catch (Exception e) {
			json.put("result", false);
		}
		return json;
	}
}
