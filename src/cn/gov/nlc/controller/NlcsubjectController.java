package cn.gov.nlc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.Subindexnum;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlcsubjectService;
import cn.gov.nlc.service.SubindexnumService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.service.impl.ScheduleServiceImpl;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/subject")
public class NlcsubjectController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcsubjectController.class);
	
	@Autowired
	private NlcsubjectService nlcsubjectService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private SyswindowService syswindowService;
	@Autowired
	private ScheduleServiceImpl scheduleServiceImpl;
	@Autowired
	private SubindexnumService subindexnumService;
	
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
		nlcadminlog.setOperate("查看特色专题管理");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "subject/view";
	}
	
	/**
	 * 专题展示页面
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getSubjectList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows){
		EasyUiDataGridResult result = nlcsubjectService.getSubjectList(page, rows);
		return result;
	}
	
	/**
	 * 根据id删除专题，单个删除
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = nlcsubjectService.deleteSingleById(id);
		
		if(result == 0) {	//删除失败
			return "{result:false}";
		}else {				//删除成功
			return "{result:true}";
		}
	}
	
	/**
	 * 跳转到特色专题添加页面
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "subject/add";
	}
	
	/**
	 * 添加专题信息和专题目录的root项
	 */
	@RequestMapping("/saveSubject")
	@ResponseBody
	public String saveSubject(Nlcsubject nlcsubject ,HttpSession session) {
		try{
			Nlcadmin nlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			nlcsubject.setCreater(nlcadmin.getUsername()); 		//创建人
			nlcsubject.setCreatetime(new Date()); 				//创建时间
			String subjectid = UUIDGenerator.getUUID();
			nlcsubject.setSubjectid(subjectid);
			nlcsubject.setSort(10000); //默认初始为10000
			
			if("已发布".equals(nlcsubject.getStatus())) {	//已发布状态，才插入站内信、弹窗表
				sysmessageService.insertMessageThfour(nlcsubject.getPushmethod(), Byte.valueOf("0"), "", nlcsubject.getSubjectid(), new Date(), nlcadmin.getUsername(), nlcsubject.getTitle(), "subject");
				syswindowService.insertWindowThfour(nlcsubject.getPushmethod(), Byte.valueOf("7"), nlcsubject.getTitle(), nlcsubject.getSubjectid(), new Date(), nlcadmin.getUsername());
				nlcsubject.setPubtime(new Date());
			}
			nlcsubject.setPraisecount(0);
			nlcsubject.setCollectcount(0);
			nlcsubjectService.inertNlcsubjectAndCatalogRoot(nlcsubject);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 专题修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Nlcsubject nlcsubject = nlcsubjectService.selectByPrimaryKey(id);
		model.addAttribute("nlcsubject", nlcsubject);
		return "subject/update";
	}
	
	/**
	 * 修改专题
	 */
	@RequestMapping("/updateSubject")
	@ResponseBody
	public String updateSubject(HttpServletRequest request) {
		try{
			Map<String, String[]> parameterMap = request.getParameterMap();
			nlcsubjectService.updateSubject(parameterMap);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 跳转到特色专题目录页面
	 * @return
	 */
	@RequestMapping("/catalogPage")
	public String catalogPage(String subjectid, Model model, String title) {
		model.addAttribute("subjectid", subjectid);
		model.addAttribute("title", title);
		return "subjectcatalog/catalogPage";
	}
	
	/**
	 * 单个专题的预览
	 */
	@RequestMapping("/itemPreview")
	public String itemPreview(String subjectid, Model model) {
		model.addAttribute("subjectid", subjectid);
		return "subject/itemPreview";
	}
	
	/**
	 * 根据subjectid查找信息
	 */
	@RequestMapping("/unit")
	@ResponseBody
	public Nlcsubject unit(String subjectid) {
		Nlcsubject nlcsubject = nlcsubjectService.selectBySubjectid(subjectid);
		return nlcsubject;
	}
	
	/**
	 * 新增页面的预览
	 */
	@RequestMapping("/addPreview")
	public String addPreview(String title, String introduce, Model model) {
		//model.addAttribute("title", title);
		//model.addAttribute("introduce", introduce);
		return "subject/addPreview";
	}
	
	/**
	 * 特色专题上下架
	 */
	@RequestMapping("/shelf/{id}")
	@ResponseBody
	public String shelf(@PathVariable Integer id) {
		try{
			nlcsubjectService.shelfAds(id);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 获取全部的数据
	 */
	@RequestMapping("/getAllList")
	@ResponseBody
	public List<Nlcsubject> getAllList() {
		List<Nlcsubject> list = nlcsubjectService.getAllList();
		return list;
	}
	
	/**
	 * 专题排序页面
	 */
	@RequestMapping("/sort")
	public String sort(Integer id, Model model) {
		Nlcsubject nlcsubject = nlcsubjectService.selectByPrimaryKey(id);
		model.addAttribute("nlcsubject", nlcsubject);
		return "subject/sort";
	}
	
	/**
	 * 修改专题
	 */
	@RequestMapping("/sortSubject")
	@ResponseBody
	public String sortSubject(Integer id, Integer sort) {
		try{
			nlcsubjectService.sortSubject(id, sort);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 拉取专题
	 */
	@RequestMapping("/pull")
	@ResponseBody
	public JSONObject pull(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try{
			result = nlcsubjectService.pull();
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("result", false);
			result.put("message", "服务器错误");
		}
		return result;
	}
	
	/**
	 * 首页专题数
	 */
	@RequestMapping("/enumshow")
	public String enumshow(Model model) {
		Subindexnum subindexnum = subindexnumService.getpo();
		model.addAttribute("subindexnum", subindexnum);
		return "subject/enumshow";
	}
	
	/**
	 * 首页专题数修改
	 */
	@RequestMapping("/enumupdate")
	@ResponseBody
	public String enumupdate(Integer id, Integer num) {
		try{
			Subindexnum po = new Subindexnum();
			po.setId(id);
			po.setNum(num);
			subindexnumService.updatePo(po);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
}
