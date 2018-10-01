package cn.gov.nlc.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.OrderinfoExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.OrderinfoService;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/orderinfo")
public class OrderinfoController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.OrderinfoController.class);
	
	@Autowired
	private OrderinfoService orderinfoService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	
	/**
	 * 跳转到支付记录页面
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
		nlcadminlog.setOperate("查看押金支付记录");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "order/view";
	}
	
	/**
	 * 支付记录页面，包括高级查询
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getOrderList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, String loginaccount, String name, String cardno, Date pstartDate, Date pendDate) {
		EasyUiDataGridResult result = orderinfoService.getOrderList(page, rows, loginaccount, name, cardno, pstartDate, pendDate);
		return result;
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String sloginaccount, String sname, String scardno, Date spstartDate, Date spendDate) {
		List<OrderinfoExt> list = orderinfoService.getExportList(sloginaccount, sname, scardno, spstartDate, spendDate);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("押金支付记录");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("书类");
		headRow.createCell(1).setCellValue("订单号");
		headRow.createCell(2).setCellValue("交易号");
		headRow.createCell(3).setCellValue("支付金额");
		headRow.createCell(4).setCellValue("支付时间");
		headRow.createCell(5).setCellValue("读者卡号");
		headRow.createCell(6).setCellValue("用户类型");
		headRow.createCell(7).setCellValue("姓名");
		headRow.createCell(8).setCellValue("证件号");
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		if (null != list && list.size() > 0) {
			for(OrderinfoExt orderinfoExt : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				
				String type = orderinfoExt.getType();
				if("national".equalsIgnoreCase(type)) {
					dataRow.createCell(0).setCellValue("中文书");
				}else {
					dataRow.createCell(0).setCellValue("外文书");
				}
				
				dataRow.createCell(1).setCellValue(orderinfoExt.getOrderno());
				dataRow.createCell(2).setCellValue(orderinfoExt.getTradeno());
				
				dataRow.createCell(3).setCellValue(orderinfoExt.getSum()+"");
				
				Date time = orderinfoExt.getTime();
				if (null != time) {
					String strTime = sdf1.format(time);
					dataRow.createCell(4).setCellValue(strTime);
				} else {
					dataRow.createCell(4).setCellValue("");
				}
				
				dataRow.createCell(5).setCellValue(orderinfoExt.getLoginaccount());
				
				String rdrolecode = orderinfoExt.getRdrolecode();
				if("0000".equals(rdrolecode)) {
					dataRow.createCell(6).setCellValue("虚拟");
				}else if("JS0001".equals(rdrolecode)) {
					dataRow.createCell(6).setCellValue("实名");
				}else if("JS0002".equals(rdrolecode)) {
					dataRow.createCell(6).setCellValue("物理卡");
				}else {
					dataRow.createCell(6).setCellValue("");
				}
				
				dataRow.createCell(7).setCellValue(orderinfoExt.getName());
				dataRow.createCell(8).setCellValue(orderinfoExt.getCardno());
			}
		}
		
		String filename = "押金支付记录.xls";
		String agent = request.getHeader("User-Agent");
		try {
			filename = FileUtils.encodeDownloadFilename(filename, agent);
			ServletOutputStream out = response.getOutputStream();
			String mimeType = request.getServletContext().getMimeType(filename);
			response.setContentType(mimeType);
			response.setHeader("content-disposition", "attachment;filename=" + filename);
			workbook.write(out);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
