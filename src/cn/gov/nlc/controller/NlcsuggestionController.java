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
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.Nlcsuggestion;
import cn.gov.nlc.pojo.NlcsuggestionExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlcsuggestionService;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/suggestion")
public class NlcsuggestionController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcsuggestionController.class);
	
	@Autowired
	private NlcsuggestionService nlcsuggestionService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	
	/**
	 * 跳转到意见反馈
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
		nlcadminlog.setOperate("查看意见反馈页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "suggestion/view";
	}
	
	/**
	 * 意见反馈展示页面
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, NlcsuggestionExt nlcsuggestionExt) {
		EasyUiDataGridResult result = nlcsuggestionService.getSuggestionList(page, rows, nlcsuggestionExt);
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
		int result = nlcsuggestionService.deleteSingleById(id);
		
		if(result == 0) {	//删除失败
			return "{result:false}";
		}else {				//删除成功
			return "{result:true}";
		}
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
			nlcsuggestionService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String ids) {
		List<Nlcsuggestion> list = nlcsuggestionService.getSelect(ids);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("意见反馈");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("回复时间");
		headRow.createCell(1).setCellValue("留言时间");
		headRow.createCell(2).setCellValue("用户");
		headRow.createCell(3).setCellValue("邮箱");
		headRow.createCell(4).setCellValue("qq");
		headRow.createCell(5).setCellValue("联系电话");
		headRow.createCell(6).setCellValue("问题分类");
		headRow.createCell(7).setCellValue("管理员姓名");
		headRow.createCell(8).setCellValue("状态");
		headRow.createCell(9).setCellValue("问题");
		headRow.createCell(10).setCellValue("答复");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(null != list && list.size() > 0) {
			for (Nlcsuggestion nlcsuggestion : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				
				Date anstime = nlcsuggestion.getAnstime();	//回复时间
				if(null != anstime) {
					String strTime = sdf1.format(anstime);
					dataRow.createCell(0).setCellValue(strTime);
				}else {
					dataRow.createCell(0).setCellValue("");
				}
				
				Date asktime = nlcsuggestion.getAsktime();	//留言时间
				if(null != asktime) {
					String strTime = sdf1.format(asktime);
					dataRow.createCell(1).setCellValue(strTime);
				}else {
					dataRow.createCell(1).setCellValue("");
				}
				
				dataRow.createCell(2).setCellValue(nlcsuggestion.getUsername());
				dataRow.createCell(3).setCellValue(nlcsuggestion.getEmail());
				dataRow.createCell(4).setCellValue(nlcsuggestion.getQq());
				dataRow.createCell(5).setCellValue(nlcsuggestion.getPhone());
				dataRow.createCell(6).setCellValue(nlcsuggestion.getType());
				dataRow.createCell(7).setCellValue(nlcsuggestion.getAdminname());
				dataRow.createCell(8).setCellValue(nlcsuggestion.getStatus());
				dataRow.createCell(9).setCellValue(nlcsuggestion.getQuestion());
				dataRow.createCell(10).setCellValue(nlcsuggestion.getAnswer());
			}
		}
		
		String filename = "意见反馈.xls";
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
	 * 意见编辑/回复页面
	 * flag 1是编辑，2是回复
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model, String flag) {
		Nlcsuggestion nlcsuggestion = nlcsuggestionService.selectByPrimaryKey(id);
		model.addAttribute("nlcsuggestion", nlcsuggestion);
		model.addAttribute("flag", flag);
//		String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
//		model.addAttribute("imgshow", imgshow);
		return "suggestion/update";
	}
	
	/**
	 * 意见编辑/回复
	 * flag 1是编辑，2是回复
	 */
	@RequestMapping("/updateSuggestion")
	@ResponseBody
	public String updateSuggestion(Integer id, String flag, String answer, HttpSession session) {
		try{
			Nlcadmin nlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			String adminname = nlcadmin.getUsername();
			nlcsuggestionService.updateSuggestion(id, flag, answer, adminname);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 查看意见
	 */
	@RequestMapping("/check")
	public String check(Integer id, Model model) {
		Nlcsuggestion nlcsuggestion = nlcsuggestionService.selectByPrimaryKey(id);
		model.addAttribute("nlcsuggestion", nlcsuggestion);
//		String imgshow = PropertiesUtils.getPropertyValue("IMG_SHOW");
//		model.addAttribute("imgshow", imgshow);
		return "suggestion/check";
	}
}
