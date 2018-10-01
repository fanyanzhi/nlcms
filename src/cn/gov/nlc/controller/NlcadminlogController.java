package cn.gov.nlc.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.NlcadminlogExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/adminlog")
public class NlcadminlogController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcadminlogController.class);
	
	@Autowired
	private NlcadminlogService nlcadminlogService;

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
		nlcadminlog.setOperate("查看管理员日志");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "admin/loglist";
	}
	
	/**
	 * 管理员展示页面，包括高级查询
	 * @param page
	 * @param rows
	 * @param nlcadmin
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, NlcadminlogExt nlcadminlogExt) {
		EasyUiDataGridResult result = nlcadminlogService.getAdminList(page, rows, nlcadminlogExt);
		return result;
	}
	
	/**
	 * 根据id删除管理员信息，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = nlcadminlogService.deleteSingleById(id);
		
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
			nlcadminlogService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		List<Nlcadminlog> list = nlcadminlogService.getAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("管理日志");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("用户名");
		headRow.createCell(1).setCellValue("角色");
		headRow.createCell(2).setCellValue("登录IP");
		headRow.createCell(3).setCellValue("登录时间");
		headRow.createCell(4).setCellValue("维护内容");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(null != list && list.size() > 0) {
			for (Nlcadminlog nlcadminlog : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(nlcadminlog.getUsername());
				
				Byte role = nlcadminlog.getRole();
				if(null != role) {
					if(0 == role) {
						dataRow.createCell(1).setCellValue("超管");
					}else if(1 == role) {
						dataRow.createCell(1).setCellValue("编辑");
					}else if(2 == role) {
						dataRow.createCell(1).setCellValue("查看");
					}
				}else {
					dataRow.createCell(1).setCellValue("");
				}
				
				dataRow.createCell(2).setCellValue(nlcadminlog.getIp());
				
				Date time = nlcadminlog.getTime();
				if(null != time) {
					String strTime = sdf1.format(time);
					dataRow.createCell(3).setCellValue(strTime);
				}else {
					dataRow.createCell(3).setCellValue("");
				}
				dataRow.createCell(4).setCellValue(nlcadminlog.getOperate());
			}
		}
		
		String filename = "管理日志.xls";
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
	 * 插入管理日志
	 */
	@RequestMapping("/insertLog")
	@ResponseBody
	public String insertLog(Nlcadminlog nlcadminlog) {
		try{
			nlcadminlogService.insertNlcadminlog(nlcadminlog);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
}
