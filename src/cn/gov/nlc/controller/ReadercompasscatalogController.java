package cn.gov.nlc.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Readercompasscatalog;
import cn.gov.nlc.pojo.ReadercompasscatalogWithBLOBs;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.ReadercompasscatalogService;
import cn.gov.nlc.util.UUIDGenerator;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/readercompasscata")
public class ReadercompasscatalogController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.ReadercompasscatalogController.class);

	@Autowired
	private NlcadminlogService nlcadminlogService;
	@Autowired
	private ReadercompasscatalogService readercompasscatalogService;

	/**
	 * 跳转到特色专题管理
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
		nlcadminlog.setOperate("查看读者指南管理");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "compasscata/view";
	}

	@RequestMapping(value = "/showTree", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public List<Readercompasscatalog> showTree() {
		List<Readercompasscatalog> list = readercompasscatalogService.getAll();
		return list;
	}

	@RequestMapping("/contentPage")
	public String contentPage(Model model, String title) {
		model.addAttribute("title", title);
		return "compasscata/content";
	}

	@RequestMapping("/catalogAddPage")
	public String catalogAddPage(Model model, String pid) {
		model.addAttribute("pid", pid);
		return "compasscata/catalogadd";
	}

	@RequestMapping("/savecatalog")
	@ResponseBody
	public String saveCatalog(ReadercompasscatalogWithBLOBs po) {
		try {
			po.setCataloguuid(UUIDGenerator.getUUID());
			po.setContent("");
			po.setContenthtml("");
			po.setChecked("false");
			po.setState("open");
			po.setCreatetime(new Date());
			String status = po.getStatus();
			if ("已上架".equals(status)) {
				po.setBkpubtime(new Date());
			}
			po.setIsdir("0");
			readercompasscatalogService.insertCatalogAndUpdateParent(po);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 通过cataloguuid得到目录对象 (这里的cataloguuid不会是root)，为了得到content
	 * @return
	 */
	@RequestMapping(value="/getCatalongContent", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public ReadercompasscatalogWithBLOBs getCatalongContentByCatalogid(String cataloguuid){
		ReadercompasscatalogWithBLOBs po = readercompasscatalogService.getCatalongContentByCataloguuid(cataloguuid);
		return po;
	}
	
	/**
	 * 根据cataloguuid保存内容content，(root不保存)
	 * @param cataloguuid
	 * @param content
	 * @return
	 */
	@RequestMapping("/saveContent")
	@ResponseBody
	public String saveContent(String cataloguuid, String content, String contenthtml) {
		try{
			readercompasscatalogService.updateContentByCataloguuid(cataloguuid, content, contenthtml);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 编辑目录名后保存目录名text，根据cataloguuid
	 * @return
	 */
	@RequestMapping("/catalogEdit")
	@ResponseBody
	public String catalogEdit(String cataloguuid, String title) {
		try{
			readercompasscatalogService.catalogEdit(cataloguuid, title);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 根据cataloguuid删除目录及其子目录
	 * @return
	 */
	@RequestMapping("/delCatalog")
	@ResponseBody
	public String delCatalog(String cataloguuid) {
		try{
			readercompasscatalogService.deleteCatalogByCatalogid(cataloguuid);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 根据cataloguuid查询并跳转到目录编辑页面
	 * @return
	 */
	@RequestMapping("/catalogEditPage")
	public String catalogEditPage(String cataloguuid, Model model) {
		Readercompasscatalog po = readercompasscatalogService.findByCataloguuid(cataloguuid);
		model.addAttribute("readercompasscatalog", po);
		return "compasscata/catalogEdit";
	}
	
	/**
	 * 修改目录
	 */
	@RequestMapping(value="/editCatalog")
	@ResponseBody
	public JSONObject editCatalog(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try{
			readercompasscatalogService.updateCatalog(request.getParameterMap());
			result.put("result", true);
		}catch(Exception e){
			result.put("result", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
}
