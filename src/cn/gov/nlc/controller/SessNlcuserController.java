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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.pojo.Nlcuserloginlog;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlcuserService;
import cn.gov.nlc.service.NlcuserloginlogService;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/user")
public class SessNlcuserController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.SessNlcuserController.class);
	
	@Autowired
	private NlcuserService nlcuserService;
	@Autowired
	private NlcuserloginlogService nlcuserloginlogService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	
	/**
	 * 跳转到用户列表
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
		nlcadminlog.setOperate("查看用户列表");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "user/view";
	}
	
	/**
	 * 用户展示页面包括高级查询
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, Nlcuser nlcuser, Date pstartDate, Date pendDate) {
		EasyUiDataGridResult result = nlcuserService.getUserList(page, rows, nlcuser, pstartDate, pendDate);
		return result;
	}
	
	/**
	 * 根据id删除用户记录，单个删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = nlcuserService.deleteSingleById(id);
		
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
			nlcuserService.deleteSingleById(Integer.parseInt(str));
		}
		return "{result:true}";
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		List<Nlcuser> list = nlcuserService.getAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("用户列表");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("读者类别");
		headRow.createCell(1).setCellValue("读者姓名");
		headRow.createCell(2).setCellValue("账号");
		headRow.createCell(3).setCellValue("证件类别");
		headRow.createCell(4).setCellValue("读者证件号码");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(null != list && list.size() > 0) {
			for (Nlcuser nlcuser : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				
				String rdrolecode = nlcuser.getRdrolecode();
				if("0000".equalsIgnoreCase(rdrolecode)) {
					dataRow.createCell(0).setCellValue("虚拟");
				}else if("JS0001".equalsIgnoreCase(rdrolecode)) {
					dataRow.createCell(0).setCellValue("实名");
				}else {
					dataRow.createCell(0).setCellValue("物理卡");
				}
				
				dataRow.createCell(1).setCellValue(nlcuser.getName());
				dataRow.createCell(2).setCellValue(nlcuser.getLoginaccount());
				String cardtype = nlcuser.getCardtype();
				String cardTypeStr = "";
				if(StringUtils.isNotBlank(cardtype))
				switch(cardtype) {
					case "01": cardTypeStr = "身份证"; break;
					case "02": cardTypeStr = "军官证"; break;
					case "03": cardTypeStr = "护照"; break;
					case "04": cardTypeStr = "港澳通行证"; break;
					case "05": cardTypeStr = "台湾通讯证"; break;
					default: cardTypeStr = "其他";
				}
				dataRow.createCell(3).setCellValue(cardTypeStr);
				dataRow.createCell(4).setCellValue(nlcuser.getCardno());
			}
		}
		
		String filename = "用户列表.xls";
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
	 * 登录日志
	 */
	@RequestMapping("/logInfo")
	public String logInfo(Integer id, Model model) {
		Nlcuser nlcuser = nlcuserService.getById(id);
		if(null != nlcuser) {
			String cardtypeno = nlcuser.getCardtype();
			if(StringUtils.isNotBlank(cardtypeno)) {
				if("01".equals(cardtypeno)) {
					cardtypeno = "身份证";
				}else if("02".equals(cardtypeno)) {
					cardtypeno = "军官证";
				}else if("03".equals(cardtypeno)) {
					cardtypeno = "护照";
				}else if("04".equals(cardtypeno)) {
					cardtypeno = "港澳通行证";
				}else if("05".equals(cardtypeno)) {
					cardtypeno = "台湾通讯证";
				}else {
					cardtypeno = "其他";
				}
				nlcuser.setCardtype(cardtypeno);	
			}
			
		}
		model.addAttribute("nlcuser", nlcuser);
		List<Nlcuserloginlog> reslist = nlcuserloginlogService.getListByUsername(nlcuser.getLoginaccount());
		model.addAttribute("reslist", reslist);
		return "user/log";
	}
	
}
