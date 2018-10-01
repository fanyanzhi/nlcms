package cn.gov.nlc.controller.share;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Librarymap;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.Nlcsubjectcatalog;
import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.service.DownloadrecordService;
import cn.gov.nlc.service.LibrarymapService;
import cn.gov.nlc.service.NlcnewsService;
import cn.gov.nlc.service.NlcnoticeService;
import cn.gov.nlc.service.NlcsubjectService;
import cn.gov.nlc.service.NlcsubjectcatalogService;
import cn.gov.nlc.service.NlctrailerService;
import cn.gov.nlc.service.WjreaderService;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.ZtreeNode;

@Controller
@RequestMapping("/share")
public class ShareController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.share.ShareController.class);
	
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
	private DownloadrecordService downloadrecordService;
	@Autowired
	private LibrarymapService librarymapService;
	
	/**
	 * 展示文津分享
	 */
	@RequestMapping("/wjreader")
	public String showWjreader(Model model, Date wjdate) {
		Wjreader wjreader = wjreaderService.getWjreaderBywjdate(wjdate);
		model.addAttribute("wjreader", wjreader);
		return "share/wjreader";
	}
	
	
	/**
	 * 展示新闻分享
	 */
	@RequestMapping("/news")
	public String show(String newsid, Model model) {
		Nlcnews nlcnews = nlcnewsService.getNlcnewsByNewsId(newsid);
		model.addAttribute("nlcnews", nlcnews);
		return "share/news";
	}
	
	@RequestMapping("/newslist")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="5")Integer rows, Integer id) {
		EasyUiDataGridResult result = nlcnewsService.getShareNewsList(id, rows);
		return result;
	}
	
	/**
	 * 展示公告分享
	 */
	@RequestMapping("/notice")
	public String showNotice(String noticeid, Model model) {
		Nlcnotice nlcnotice = nlcnoticeService.selectByPrimaryKey(noticeid);
		model.addAttribute("nlcnotice", nlcnotice);
		return "share/notice";
	}
	
	@RequestMapping("/noticelist")
	@ResponseBody
	public EasyUiDataGridResult getNoticeList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="5")Integer rows, String noticeid) {
		EasyUiDataGridResult result = nlcnoticeService.getShareNoticeList(noticeid, rows);
		return result;
	}
	
	/**
	 * 讲座预告分享
	 */
	@RequestMapping("/trailer")
	public String showTrailer(String trailerid, Model model) {
		Nlctrailer nlctrailer = nlctrailerService.selectByTrailerId(trailerid);
		model.addAttribute("nlctrailer", nlctrailer);
		return "share/trailer";
	}
	
	/**
	 * 专题的分享
	 */
	@RequestMapping("/subject")
	public String showSubject(String subjectid, Model model) {
		Nlcsubject nlcsubject = nlcsubjectService.selectBySubjectid(subjectid);
		model.addAttribute("nlcsubject", nlcsubject);
		return "share/subject";
	}
	
	/**
	 * 专题分享页面的目录
	 */
	@RequestMapping("/subjectCatalog")
	public String subjectCatalog(String subjectid, Model model) {
		model.addAttribute("subjectid", subjectid);
		return "share/subjectCatalog";
	}
	
	/**
	 * Ztree的专题目录树
	 */
	@RequestMapping(value="/showZtree", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public List<ZtreeNode> showZtree(String subjectid) {
		List<Nlcsubjectcatalog> list = nlcsubjectcatalogService.findBySubjectidWithoutDown(subjectid);
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
	 * 专题每一条目录的展开页
	 * @return
	 */
	@RequestMapping("/showCatalogDetail")
	public String showCatalogDetail(String catalogid, Model model) {
		Nlcsubjectcatalog nlcsubjectcatalog = nlcsubjectcatalogService.getCatalongContentByCatalogid(catalogid);
		String pid = nlcsubjectcatalog.getPid();
		Integer cseq = nlcsubjectcatalog.getCseq();
		String subjectid = nlcsubjectcatalog.getSubjectid();
		String[] pages = nlcsubjectcatalogService.getPreAndNextCatalogid(pid, cseq, subjectid);
		if(null == pages) {
			model.addAttribute("prepage", null);
			model.addAttribute("nextpage", null);
		}else {
			model.addAttribute("prepage", pages[0]);
			model.addAttribute("nextpage", pages[1]);
		}
		
		
		model.addAttribute("title", nlcsubjectcatalog.getTitle());
		model.addAttribute("content", nlcsubjectcatalog.getContent());
		model.addAttribute("subjectid", nlcsubjectcatalog.getSubjectid());
		return "share/showCatalogDetail";
	}
	
	/**
	 * 文津今日的目录的展开页
	 * @return
	 */
	@RequestMapping("/showCatalogDetailWjtoday")
	public String showCatalogDetailWjtoday(String catalogid, String subjectid, Model model) {
		model.addAttribute("subjectid", subjectid);
		model.addAttribute("catalogid", catalogid);
		return "share/showCatalogDetailWjtoday";
	}
	
	/**
	 * 通过catalogid得到目录对象 (这里的catalogid不会是root)，为了得到content
	 * @return
	 */
	@RequestMapping(value="/getCatalongContent", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public Nlcsubjectcatalog getCatalongContentByCatalogid(String catalogid){
		Nlcsubjectcatalog nlcsubjectcatalog = nlcsubjectcatalogService.getCatalongContentByCatalogid(catalogid);
		return nlcsubjectcatalog;
	}
	
	/**
	 * 文津今日的目录的展开页
	 * @return
	 */
	@RequestMapping("/showCatalogDetailWjhistory")
	public String showCatalogDetailWjhistory(String catalogid, String subjectid, Model model) {
		model.addAttribute("subjectid", subjectid);
		model.addAttribute("catalogid", catalogid);
		return "share/showCatalogDetailWjhistory";
	}
	
	/**
	 * 下载页面
	 */
	@RequestMapping("/download")
	public String download(Model model, String method) {
		try{
			downloadrecordService.insertDownloadrecord(method);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return "share/appdownload";
	}
	
	/**
	 * 馆区地图页面
	 * @return
	 */
	@RequestMapping("/map")
	public String show(Model model) {
		List<Librarymap> reslist = librarymapService.getAll();
		model.addAttribute("reslist", reslist);
		return "share/map";
	}
	
}