package cn.gov.nlc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcsubjectcatalog;
import cn.gov.nlc.service.NlcsubjectcatalogService;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.ZtreeNode;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/subjectcatalog")
public class NlcsubjectcatalogController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcsubjectcatalogController.class);
	
	@Autowired
	private NlcsubjectcatalogService nlcsubjectcatalogService;
	
	/**
	 * 目录树
	 * @param subjectid
	 * @return
	 */
	@RequestMapping(value="/showTree", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public List<Nlcsubjectcatalog> showTree(String subjectid) {
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogService.findBySubjectid(subjectid);
		return list;
	}
	
	/**
	 * Ztree的目录树
	 */
	@RequestMapping(value="/showZtree", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public List<ZtreeNode> showZtree(String subjectid) {
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogService.findBySubjectid(subjectid);
		List<ZtreeNode> zlist = new ArrayList<ZtreeNode>();
		
		if(null != list && list.size() > 0) {
			for (Nlcsubjectcatalog nlcsubjectcatalog : list) {
				ZtreeNode znode = new ZtreeNode();
				String catalogid = nlcsubjectcatalog.getCatalogid();
				if("root".equals(catalogid)) {
					continue;
				}
				znode.setId(catalogid);
				
				String pid = nlcsubjectcatalog.getPid();
				if("root".equals(pid)) {
					znode.setpId("");
				}else {
					znode.setpId(pid);
				}
				znode.setName(nlcsubjectcatalog.getTitle());
				String state = nlcsubjectcatalog.getState();
				if("open".equals(state) || "true".equals(state)) {
					znode.setOpen("true");
				}else if("closed".equals(state) || "false".equals(state)) {
					znode.setOpen("false");
				}
				zlist.add(znode);
			}
		}
		return zlist;
	}
	
	/**
	 * 跳转到专题目录内容content页面
	 * @return
	 */
	@RequestMapping("/subjectContentPage")
	public String subjectContent(Model model, String title) {
		model.addAttribute("title", title);
		return "subjectcatalog/subjectContent";
	}
	
	/**
	 * 通过catalogid得到目录对象 (这里的catalogid不会是root)，为了得到content
	 * @return
	 */
	@RequestMapping(value="/getCatalongContent", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public Nlcsubjectcatalog getCatalongContentByCatalogid(String catalogId){
		Nlcsubjectcatalog nlcsubjectcatalog = nlcsubjectcatalogService.getCatalongContentByCatalogid(catalogId);
		return nlcsubjectcatalog;
	}
	
	/**
	 * 编辑目录名后保存目录名text，根据catalogid
	 * @return
	 */
	@RequestMapping("/catalogEdit")
	@ResponseBody
	public String catalogEdit(String catalogid, String title) {
		try{
			nlcsubjectcatalogService.catalogEdit(catalogid, title);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 进入目录新增页面
	 * @param model
	 * @param subjectid
	 * @param pid
	 * @return
	 */
	@RequestMapping("/catalogAddPage")
	public String catalogAddPage(Model model, String subjectid, String pid, String title) {
		model.addAttribute("subjectid", subjectid);
		model.addAttribute("pid", pid);
		model.addAttribute("title", title);
		return "subjectcatalog/subjectCatalogAdd";
	}
	
	/**
	 * 保存新添加的目录
	 * @return
	 */
	@RequestMapping("/subjectCatalogAdd")
	@ResponseBody
	public String subjectCatalogAdd(Nlcsubjectcatalog nlcsubjectcatalog) {
		try{
			nlcsubjectcatalog.setChecked("false");
			nlcsubjectcatalog.setContent("");
			nlcsubjectcatalog.setState("open");
			nlcsubjectcatalog.setCatalogid(UUIDGenerator.getUUID());
			nlcsubjectcatalog.setIsdir("0");
			nlcsubjectcatalog.setStatus("已上架");
			nlcsubjectcatalogService.insertCatalogAndUpdateParent(nlcsubjectcatalog);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 根据catalogid保存内容content，(root不保存)
	 * @param catalogid
	 * @param content
	 * @return
	 */
	@RequestMapping("/saveContent")
	@ResponseBody
	public String saveContent(String catalogid, String content) {
		try{
			nlcsubjectcatalogService.updateContentByCatalogid(catalogid, content);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 根据catalogid删除目录及其子目录
	 * @return
	 */
	@RequestMapping("/delCatalog")
	@ResponseBody
	public String delCatalog(String catalogid) {
		try{
			nlcsubjectcatalogService.deleteCatalogByCatalogid(catalogid);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 根据catalogid查询并跳转到目录编辑页面
	 * @return
	 */
	@RequestMapping("/catalogEditPage")
	public String catalogEditPage(String catalogid, Model model, String title) {
		Nlcsubjectcatalog nlcsubjectcatalog = nlcsubjectcatalogService.getCatalogByCatalogid(catalogid);
		model.addAttribute("nlcsubjectcatalog", nlcsubjectcatalog);
		model.addAttribute("title", title);
		return "subjectcatalog/subjectCatalogEdit";
	}
	
	/**
	 * 修改目录
	 */
	@RequestMapping(value="/editCatalog")
	@ResponseBody
	public JSONObject editCatalog(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try{
			nlcsubjectcatalogService.updateCatalog(request.getParameterMap());
			result.put("result", true);
		}catch(Exception e){
			result.put("result", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 进入特色专题管理预览的每一条的详情页面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String catalogid, Model model) {
		model.addAttribute("catalogid", catalogid);
		return "subject/showinfo";
	}
	
	/**
	 * 文津经典诵读预览今日内容
	 */
	@RequestMapping("/wjtoday")
	public String wjtoday(String catalogid, Model model) {
		model.addAttribute("catalogid", catalogid);
		return "subjectcatalog/wjtoday";
	}
	
	/**
	 * 文津经典诵读预览历史内容
	 */
	@RequestMapping("/wjhistory")
	public String wjhistory(String catalogid, Model model) {
		model.addAttribute("catalogid", catalogid);
		return "subjectcatalog/wjhistory";
	}
	
}
