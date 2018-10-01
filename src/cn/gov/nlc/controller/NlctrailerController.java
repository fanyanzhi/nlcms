package cn.gov.nlc.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.NlctrailerExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlctrailerService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/trailer")
public class NlctrailerController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlctrailerController.class);
	
	@Autowired
	private NlctrailerService nlctrailerService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private SyswindowService syswindowService;
	
	/**
	 * 跳转到讲座管理页面
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
		nlcadminlog.setOperate("查看讲座管理页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "trailer/view";
	}
	
	/**
	 * 讲座展示页面，包括高级查询
	 * @param page
	 * @param rows
	 * @param nlctrailerExt
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getTrailerList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, NlctrailerExt nlctrailerExt, String sort, String order) {
		EasyUiDataGridResult result = nlctrailerService.getTrailerList(page, rows, nlctrailerExt, sort, order);
		return result;
	}
	
	/**
	 * 根据id删除讲座，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = nlctrailerService.deleteSingleById(id);
		
		if(result == 0) {	//删除失败
			return "{result:false}";
		}else {				//删除成功
			return "{result:true}";
		}
	}
	
	/**
	 * 删除多个
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public String deleteAll(String ids) {
		if(StringUtils.isBlank(ids)) {
			return "{result:false}";
		}
		
		String[] strArr = ids.split(",");
		for (String str : strArr) {
			nlctrailerService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		List<Nlctrailer> list = nlctrailerService.getAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("讲座管理");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("题目");
		headRow.createCell(1).setCellValue("栏目");
		headRow.createCell(2).setCellValue("主讲");
		headRow.createCell(3).setCellValue("开始时间");
		headRow.createCell(4).setCellValue("结束时间");
		headRow.createCell(5).setCellValue("来源");
		headRow.createCell(6).setCellValue("地点");
		headRow.createCell(7).setCellValue("馆区");
		headRow.createCell(8).setCellValue("创建时间");
		headRow.createCell(9).setCellValue("状态");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(null != list && list.size() > 0) {
			for(Nlctrailer nlctrailer : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				
				dataRow.createCell(0).setCellValue(nlctrailer.getTitle());
				dataRow.createCell(1).setCellValue(nlctrailer.getColumnname());
				dataRow.createCell(2).setCellValue(nlctrailer.getSpeakername());
				
				Date starttime = nlctrailer.getStarttime();	//开始时间
				if(null != starttime) {
					String strTime = sdf1.format(starttime);
					dataRow.createCell(3).setCellValue(strTime);
				}else {
					dataRow.createCell(3).setCellValue("");
				}
				
				Date endtime = nlctrailer.getEndtime();		//结束时间
				if(null != endtime) {
					String strTime = sdf1.format(endtime);
					dataRow.createCell(4).setCellValue(strTime);
				}else {
					dataRow.createCell(4).setCellValue("");
				}
				
				dataRow.createCell(5).setCellValue(nlctrailer.getSource());
				dataRow.createCell(6).setCellValue(nlctrailer.getPlace());
				dataRow.createCell(7).setCellValue(nlctrailer.getGuanqu());
				
				Date time = nlctrailer.getTime();
				if(null != time) {
					String cretime = sdf1.format(time);
					dataRow.createCell(8).setCellValue(cretime);
				}else {
					dataRow.createCell(8).setCellValue("");
				}
				
				dataRow.createCell(9).setCellValue(nlctrailer.getStatus());
			}
		}
		
		String filename = "讲座管理.xls";
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
	 * 跳转到添加讲座页面
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "trailer/add";
	}
	
	/**
	 * 添加讲座
	 */
	@RequestMapping("/saveTrailer")
	@ResponseBody
	public String saveTrailer(Nlctrailer nlctrailer, HttpSession session) {
		try{
			Nlcadmin nlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			String trailerid = UUIDGenerator.getUUID();
			nlctrailer.setTrailerid(trailerid);
			Date starttime = nlctrailer.getStarttime();
			Date endtime = nlctrailer.getEndtime();
			if(null != starttime && null != endtime) {
				String mergeTime = Common.mergeTime(starttime, endtime);
				nlctrailer.setSpeaktime(mergeTime);
			}
			nlctrailer.setTime(new Date());
			nlctrailer.setSource("原创");
			
			nlctrailer.setPraisecount(0);
			nlctrailer.setCollectcount(0);
			
			if("已发布".equals(nlctrailer.getStatus())) {	//已发布状态，才插入站内信、弹窗表
				sysmessageService.insertMessageThfour(nlctrailer.getPushmethod(), Byte.valueOf("0"), "", nlctrailer.getTrailerid(), new Date(), nlcadmin.getUsername(), nlctrailer.getTitle(), "trailer");
				syswindowService.insertWindowThfour(nlctrailer.getPushmethod(), Byte.valueOf("6"), "讲座预告："+nlctrailer.getTitle(), nlctrailer.getTrailerid(), new Date(), nlcadmin.getUsername());
			}
			nlctrailerService.insertNlctrailer(nlctrailer);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 讲座修改页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Nlctrailer nlctrailer = nlctrailerService.selectByPrimaryKey(id);
		model.addAttribute("nlctrailer", nlctrailer);
		return "trailer/update";
	}
	
	/**
	 * 修改公告
	 */
	@RequestMapping("/updateTrailer")
	@ResponseBody
	public String updateTrailer(HttpServletRequest request, HttpSession session) {
		try{
			Nlcadmin nlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			Map<String, String[]> parameterMap = request.getParameterMap();
			nlctrailerService.updateTrailer(parameterMap, nlcadmin.getUsername());
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 在讲座预告管理中的预览
	 */
	@RequestMapping("/itemPreview")
	public String itemPreview(String trailerid, Model model) {
		model.addAttribute("trailerid", trailerid);
		return "trailer/itemPreview";
	}
	
	/**
	 * 通过trailerid得到Nlctrailer
	 * @return
	 */
	@RequestMapping("/getTrailByTrailerid")
	@ResponseBody
	public Nlctrailer getTrailByTrailerid(String trailerid) {
		Nlctrailer nlctrailer = nlctrailerService.selectByTrailerId(trailerid);
		return nlctrailer;
	}
	
	/**
	 * 新增、编辑页面的预览
	 */
	@RequestMapping("/addPreview")
	public String addPreview() {
		return "trailer/addPreview";
	}
	
	/**
	 * 讲座发布
	 * status 1发布，0取消
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public String shelf(Integer id, String status, HttpSession session) {
		try{
			Nlcadmin nlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			nlctrailerService.publish(id, status, nlcadmin.getUsername());
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 拉取讲座预告
	 */
	@RequestMapping("/pull")
	@ResponseBody
	public JSONObject pull(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try{
			result = nlctrailerService.pull();
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("result", false);
			result.put("message", "服务器错误");
		}
		return result;
	}
}
