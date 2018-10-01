package cn.gov.nlc.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.NlcnewsExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlcnewsService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/news")
public class NlcnewsController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcnewsController.class);

	@Autowired
	private NlcnewsService nlcnewsService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private SyswindowService syswindowService;
	
	/**
	 * 跳转到新闻管理页面
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
		nlcadminlog.setOperate("查看新闻管理页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "news/view";
	}
	
	/**
	 * 新闻管理展示页面，包括高级查询
	 * @param page
	 * @param rows
	 * @param nlcadmin
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, NlcnewsExt nlcnewsExt, String sort, String order) {
		EasyUiDataGridResult result = nlcnewsService.getnewsList(page, rows, nlcnewsExt, sort, order);
		return result;
	}
	
	/**
	 * 根据id删除新闻信息，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = nlcnewsService.deleteSingleById(id);
		
		if(result == 0) {	//删除失败
			return "{result:false}";
		}else {				//删除成功
			return "{result:true}";
		}
	}
	
	/**
	 * 置顶新闻
	 * @param id
	 * @return
	 */
	@RequestMapping("/settop/{id}")
	@ResponseBody
	public String settop(@PathVariable Integer id) {
		try{
			nlcnewsService.settop(id);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 取消置顶新闻
	 * @param id
	 * @return
	 */
	@RequestMapping("/cantop/{id}")
	@ResponseBody
	public String cantop(@PathVariable Integer id) {
		try{
			nlcnewsService.cantop(id);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
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
			nlcnewsService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		List<Nlcnews> list = nlcnewsService.getAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("新闻管理");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("上传时间");
		headRow.createCell(1).setCellValue("新闻标题");
		headRow.createCell(2).setCellValue("上传人");
		headRow.createCell(3).setCellValue("来源");
		headRow.createCell(4).setCellValue("内容发布时间");
		headRow.createCell(5).setCellValue("状态");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(null != list && list.size() > 0) {
			Iterator<Nlcnews> iterator = list.iterator();
			while(iterator.hasNext()) {
				Nlcnews nlcnews = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				
				Date subTime = nlcnews.getSubTime();
				if(null != subTime) {
					String strTime = sdf1.format(subTime);
					dataRow.createCell(0).setCellValue(strTime);
				}else {
					dataRow.createCell(0).setCellValue("");
				}
				
				dataRow.createCell(1).setCellValue(nlcnews.getTitle());
				dataRow.createCell(2).setCellValue(nlcnews.getSubPerson());
				dataRow.createCell(3).setCellValue(nlcnews.getSource());
				
				Date pubTime = nlcnews.getPubTime();
				if(null != pubTime) {
					String strTime2 = sdf1.format(pubTime);
					dataRow.createCell(4).setCellValue(strTime2);
				}else {
					dataRow.createCell(4).setCellValue("");
				}
				
				dataRow.createCell(5).setCellValue(nlcnews.getStatus());
				
			}
		}
		
		String filename = "新闻管理.xls";
		String agent = request.getHeader("User-Agent");
		try {
			filename = FileUtils.encodeDownloadFilename(filename, agent);
			ServletOutputStream out = response.getOutputStream();
			String mimeType = request.getServletContext().getMimeType(filename);
			response.setContentType(mimeType);
			response.setHeader("content-disposition", "attachment;filename="+filename);
			workbook.write(out);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 跳转到添加新闻页面
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "news/add";
	}
	
	/**
	 * 添加新闻
	 * @param nlcnews
	 * @return
	 * pushmethod推送方式：弹窗0，站内信1
	 */
	@RequestMapping("/saveNews")
	@ResponseBody
	public String saveNews(Nlcnews nlcnews, HttpSession session) {
		try{
			Nlcadmin nlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			nlcnews.setSubPerson(nlcadmin.getUsername());	//上传人
			nlcnews.setSubTime(new Date());				 	//上传时间是当前时间
			nlcnews.setSource("原创");
			nlcnews.setPraisecount(0);
			nlcnews.setCollectcount(0);
			String newsid = UUIDGenerator.getUUID();
			nlcnews.setNewsid(newsid);
			
			if("已发布".equals(nlcnews.getStatus())) {	//已发布状态，才插入站内信、弹窗表
				sysmessageService.insertMessageThfour(nlcnews.getPushmethod(), Byte.valueOf("0"), "", nlcnews.getNewsid(), new Date(), nlcadmin.getUsername(), nlcnews.getTitle(), "news");
				syswindowService.insertWindowThfour(nlcnews.getPushmethod(), Byte.valueOf("4"), nlcnews.getTitle(), nlcnews.getNewsid(), new Date(), nlcadmin.getUsername());
				nlcnews.setBkpbtime(new Date());
			}
			nlcnews.setTime(new Date());
			nlcnews.setTops(0);
			nlcnewsService.insertNews(nlcnews);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 管理员修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Nlcnews nlcnews = nlcnewsService.selectByPrimaryKey(id);
		model.addAttribute("nlcnews", nlcnews);
		return "news/update";
	}
	
	/**
	 * 修改新闻
	 * @param nlcnews
	 * @return
	 */
	@RequestMapping("/updateNews")
	@ResponseBody
	public String upateNews(Nlcnews nlcnews, HttpServletRequest request) {
		try{
			Map<String, String[]> parameterMap = request.getParameterMap();
			nlcnewsService.updateNews(parameterMap);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 在新闻管理中的预览
	 */
	@RequestMapping("/itemPreview")
	public String itemPreview(String newsid, Model model) {
		model.addAttribute("newsid", newsid);
		return "news/itemPreview";
	}
	
	/**
	 * 通过newsid得到新闻
	 */
	@RequestMapping("/getNewsByNewsid")
	@ResponseBody
	public Nlcnews getNewsByNewsid(String newsid) {
		Nlcnews nlcnews = nlcnewsService.getNlcnewsByNewsId(newsid);
		return nlcnews;
	}
	/**
	 * 新增、编辑页面的预览
	 */
	@RequestMapping("/addPreview")
	public String addPreview() {
		return "news/addPreview";
	}
	
	/**
	 * 新闻发布
	 * status 1发布，0取消
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public String shelf(Integer id, String status) {
		try{
			nlcnewsService.publish(id, status);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 拉取新闻
	 */
	@RequestMapping("/pull")
	@ResponseBody
	public JSONObject pull(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try{
			result = nlcnewsService.pull();
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("result", false);
			result.put("message", "服务器错误");
		}
		return result;
	}
}
