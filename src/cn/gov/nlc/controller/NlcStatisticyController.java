package cn.gov.nlc.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.pojo.Nlcuserloginlog;
import cn.gov.nlc.service.NlcStatisticServicey;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.vo.Apptj;
import cn.gov.nlc.vo.Cmyh;
import cn.gov.nlc.vo.Dyfx;
import cn.gov.nlc.vo.DyfxPoExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.Edition;
import cn.gov.nlc.vo.Hyyh;
import cn.gov.nlc.vo.Modelx;
import cn.gov.nlc.vo.PersonPo;
import cn.gov.nlc.vo.Yhhx;
import cn.gov.nlc.vo.YhhxDetail;
import cn.gov.nlc.vo.YmfwPo;

@Controller
@RequestMapping("/session/statisticy")
public class NlcStatisticyController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcStatisticyController.class);

	@Autowired
	private NlcStatisticServicey nlcStatisticServicey;

	// ===========================活跃用户======================

	/**
	 * 跳转到用户分析活跃用户
	 */
	@RequestMapping("/hyyh")
	public String hyyhFrameshow(Model model) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		int yesweeks = yesterday.getWeekOfWeekyear();
		int lastmonthweeks = lastmonth.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("yesweeks", yesterday.toString("yyyy-MM-dd") + " " + yesweeks);
		// 上个月 + 周数
		model.addAttribute("lastmonthweeks", lastmonth.toString("yyyy-MM-dd") + " " + lastmonthweeks);

		// 日
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));
		return "statisticy/hyyh/frame";
	}

	/**
	 * 活跃用户的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hyyhGrid")
	public String hyyhGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticy/hyyh/hyyhGrid";
	}

	/**
	 * 活跃用户的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/hyyhList")
	@ResponseBody
	public EasyUiDataGridResult hyyhList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicey.dayHyyhList(page, rows, startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicey.weekHyyhList(page, rows, startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicey.monHyyhList(page, rows, startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
		}

		return result;
	}

	/**
	 * 活跃用户的export
	 */
	@RequestMapping("/hyyhExport")
	public void hyyhExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("活跃用户");
		HSSFRow headRow0 = sheet.createRow(0);

		List<Hyyh> list = null;
		if ("day".equalsIgnoreCase(status)) {
			headRow0.createCell(0).setCellValue("按天统计(当天启动的用户数去重)");
			list = (List<Hyyh>) nlcStatisticServicey.dayHyyhList(null, null, startDate, endDate).getRows();
		} else if ("week".equalsIgnoreCase(status)) {
			headRow0.createCell(0).setCellValue("按周统计(当周启动的用户数去重)");
			list = (List<Hyyh>) nlcStatisticServicey.weekHyyhList(null, null, startDate, endDate).getRows();
		} else if ("month".equalsIgnoreCase(status)) {
			headRow0.createCell(0).setCellValue("按月统计(当月启动的用户数去重)");
			list = (List<Hyyh>) nlcStatisticServicey.monHyyhList(null, null, startDate, endDate).getRows();
		} else if ("year".equalsIgnoreCase(status)) {
		}

		HSSFRow headRow = sheet.createRow(1);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("活跃用户量");

		if (null != list && list.size() > 0) {
			Iterator<Hyyh> iterator = list.iterator();
			while (iterator.hasNext()) {
				Hyyh detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getAmount());
			}
		}

		String filename = "活跃用户量.xls";
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

	// ==========================沉默用户=========================

	/**
	 * 跳转到用户分析沉默用户
	 */
	@RequestMapping("/cmyh")
	public String cmyhFrameshow(Model model) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		int yesweeks = yesterday.getWeekOfWeekyear();
		int lastmonthweeks = lastmonth.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("yesweeks", yesterday.toString("yyyy-MM-dd") + " " + yesweeks);
		// 上个月 + 周数
		model.addAttribute("lastmonthweeks", lastmonth.toString("yyyy-MM-dd") + " " + lastmonthweeks);

		// 日
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));
		return "statisticy/cmyh/frame";
	}

	/**
	 * 沉默用户的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cmyhGrid")
	public String cmyhGrid(Model model, Date startDate, Date endDate) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statisticy/cmyh/cmyhGrid";
	}

	/**
	 * 沉默用户的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/cmyhList")
	@ResponseBody
	public EasyUiDataGridResult cmyhList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = nlcStatisticServicey.weekCmyhList(page, rows, startDate, endDate);
		return result;
	}

	/**
	 * 沉默用户的export
	 */
	@RequestMapping("/cmyhExport")
	public void cmyhExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<Cmyh> list = (List<Cmyh>) nlcStatisticServicey.weekCmyhList(null, null, startDate, endDate).getRows();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("沉默用户");
		HSSFRow headRow0 = sheet.createRow(0);
		headRow0.createCell(0).setCellValue("沉默用户：按周统计，指在当周新增的用户只在注册的当天和第二天使用过，以后就没再使用过");

		HSSFRow headRow = sheet.createRow(1);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("新增用户");
		headRow.createCell(2).setCellValue("沉默用户");
		headRow.createCell(3).setCellValue("沉默用户占比");

		if (null != list && list.size() > 0) {
			Iterator<Cmyh> iterator = list.iterator();
			while (iterator.hasNext()) {
				Cmyh detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getNewAmount());
				dataRow.createCell(2).setCellValue(detail.getSilAmount());
				dataRow.createCell(3).setCellValue(detail.getPercent());
			}
		}

		String filename = "沉默用户.xls";
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

	// ==============用户画像==========================

	/**
	 * 跳转到用户画像
	 */
	@RequestMapping("/yhhx")
	public String yhhxFrameshow(Model model) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		int yesweeks = yesterday.getWeekOfWeekyear();
		int lastmonthweeks = lastmonth.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("yesweeks", yesterday.toString("yyyy-MM-dd") + " " + yesweeks);
		// 上个月 + 周数
		model.addAttribute("lastmonthweeks", lastmonth.toString("yyyy-MM-dd") + " " + lastmonthweeks);

		// 日
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));
		return "statisticy/yhhx/yhhxGrid";
	}

	/**
	 * 用户画像的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/yhhxList")
	@ResponseBody
	public EasyUiDataGridResult yhhxList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, Yhhx yhhx) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = nlcStatisticServicey.yhhxList(page, rows, startDate, endDate, yhhx);
		return result;
	}

	/**
	 * 用户画像的export
	 */
	@RequestMapping("/yhhxExport")
	public void yhhxExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			Yhhx yhhx) {
		List<Yhhx> list = (List<Yhhx>) nlcStatisticServicey.yhhxList(null, null, startDate, endDate, yhhx).getRows();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("用户画像");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("读者类别");
		headRow.createCell(1).setCellValue("读者姓名");
		headRow.createCell(2).setCellValue("账号");
		headRow.createCell(3).setCellValue("证件类别");
		headRow.createCell(4).setCellValue("证件号");
		headRow.createCell(5).setCellValue("年龄");
		headRow.createCell(6).setCellValue("性别");
		headRow.createCell(7).setCellValue("使用次数");
		headRow.createCell(8).setCellValue("使用时长(秒)");
		headRow.createCell(9).setCellValue("页面访问数");
		headRow.createCell(10).setCellValue("唯一页面访问数");
		headRow.createCell(11).setCellValue("下载次数");

		if (null != list && list.size() > 0) {
			Iterator<Yhhx> iterator = list.iterator();
			while (iterator.hasNext()) {
				Yhhx po = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

				String rdrolecode = po.getRdrolecode();
				if ("0000".equals(rdrolecode)) {
					dataRow.createCell(0).setCellValue("虚拟");
				} else if ("JS0001".equals(rdrolecode)) {
					dataRow.createCell(0).setCellValue("实名");
				} else {
					dataRow.createCell(0).setCellValue("物理卡");
				}

				if (null != po.getName()) {
					dataRow.createCell(1).setCellValue(po.getName());
				} else {
					dataRow.createCell(1).setCellValue("");
				}

				if (null != po.getLoginaccount()) {
					dataRow.createCell(2).setCellValue(po.getLoginaccount());
				} else {
					dataRow.createCell(2).setCellValue("");
				}

				String cardtype = po.getCardtype();
				if ("01".equals(cardtype)) {
					dataRow.createCell(3).setCellValue("身份证");
				} else if ("02".equals(cardtype)) {
					dataRow.createCell(3).setCellValue("军官证");
				} else if ("03".equals(cardtype)) {
					dataRow.createCell(3).setCellValue("护照");
				} else if ("04".equals(cardtype)) {
					dataRow.createCell(3).setCellValue("港澳通行证");
				} else if ("05".equals(cardtype)) {
					dataRow.createCell(3).setCellValue("台湾通行证");
				} else {
					dataRow.createCell(3).setCellValue("");
				}

				if (po.getCardno() != null) {
					dataRow.createCell(4).setCellValue(po.getCardno());
				} else {
					dataRow.createCell(4).setCellValue("");
				}

				if (po.getAge() != null) {
					dataRow.createCell(5).setCellValue(po.getAge());
				} else {
					dataRow.createCell(5).setCellValue("");
				}

				String sextype = po.getSextype();
				if ("null".equals(sextype)) {
					dataRow.createCell(6).setCellValue("");
				} else if ("男".equals(sextype) || "男士".equals(sextype)) {
					dataRow.createCell(6).setCellValue("男士");
				} else if ("女".equals(sextype) || "女士".equals(sextype)) {
					dataRow.createCell(6).setCellValue("女士");
				} else {
					dataRow.createCell(6).setCellValue("");
				}

				if (null != po.getCs()) {
					dataRow.createCell(7).setCellValue(po.getCs());
				} else {
					dataRow.createCell(7).setCellValue("");
				}

				if (null != po.getSc()) {
					dataRow.createCell(8).setCellValue(po.getSc());
				} else {
					dataRow.createCell(8).setCellValue("");
				}

				if (null != po.getPv()) {
					dataRow.createCell(9).setCellValue(po.getPv());
				} else {
					dataRow.createCell(9).setCellValue("");
				}

				if (null != po.getUv()) {
					dataRow.createCell(10).setCellValue(po.getUv());
				} else {
					dataRow.createCell(10).setCellValue("");
				}

				if (null != po.getDs()) {
					dataRow.createCell(11).setCellValue(po.getDs());
				} else {
					dataRow.createCell(11).setCellValue("");
				}

			}
		}

		String filename = "用户画像.xls";
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

	/**
	 * 用户详情页面的跳转
	 */
	@RequestMapping("/detail")
	public String detail(String loginaccount, Model model, Date startDate, Date endDate) {
		model.addAttribute("loginaccount", loginaccount);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statisticy/yhhx/detail";
	}

	/**
	 * 用户详情的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/detailList")
	@ResponseBody
	public EasyUiDataGridResult detailList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String loginaccount) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = nlcStatisticServicey.detailList(page, rows, startDate, endDate, loginaccount);
		return result;
	}

	/**
	 * 用户详情的导出
	 */
	@RequestMapping("/yhhxDetailExport")
	public void yhhxDetailExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String loginaccount) {
		List<YhhxDetail> list = (List<YhhxDetail>) nlcStatisticServicey
				.detailList(null, null, startDate, endDate, loginaccount).getRows();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("用户详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("浏览时间");
		headRow.createCell(1).setCellValue("浏览详情");
		headRow.createCell(2).setCellValue("阅读时间");

		if (null != list && list.size() > 0) {
			Iterator<YhhxDetail> iterator = list.iterator();
			while (iterator.hasNext()) {
				YhhxDetail detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getDetail());
				dataRow.createCell(2).setCellValue(detail.getDuration());
			}
		}

		String filename = "用户详情.xls";
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

	// ===============app统计======================

	/**
	 * 跳转到app统计
	 */
	@RequestMapping("/apptj")
	public String apptjFrameshow(Model model) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		int yesweeks = yesterday.getWeekOfWeekyear();
		int lastmonthweeks = lastmonth.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("yesweeks", yesterday.toString("yyyy-MM-dd") + " " + yesweeks);
		// 上个月 + 周数
		model.addAttribute("lastmonthweeks", lastmonth.toString("yyyy-MM-dd") + " " + lastmonthweeks);

		// 日
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));
		return "statisticy/apptj/frame";
	}

	/**
	 * app统计的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/apptjGrid")
	public String apptjGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticy/apptj/apptjGrid";
	}

	/**
	 * app统计的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/apptjList")
	@ResponseBody
	public EasyUiDataGridResult apptjList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicey.dayApptjList(page, rows, startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicey.weekApptjList(page, rows, startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicey.monthApptjList(page, rows, startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicey.yearApptjList(page, rows, startDate, endDate);
		}

		return result;
	}

	/**
	 * apptj页面访问量的export
	 */
	@RequestMapping("/apptjExport")
	public void apptjExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<Apptj> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = (List<Apptj>) nlcStatisticServicey.dayApptjList(null, null, startDate, endDate).getRows();
		} else if ("week".equalsIgnoreCase(status)) {
			list = (List<Apptj>) nlcStatisticServicey.weekApptjList(null, null, startDate, endDate).getRows();
		} else if ("month".equalsIgnoreCase(status)) {
			list = (List<Apptj>) nlcStatisticServicey.monthApptjList(null, null, startDate, endDate).getRows();
		} else if ("year".equalsIgnoreCase(status)) {
			list = (List<Apptj>) nlcStatisticServicey.yearApptjList(null, null, startDate, endDate).getRows();
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("app统计");
		CellRangeAddress ce1 = new CellRangeAddress(0, 0, 1, 2);
		CellRangeAddress ce2 = new CellRangeAddress(0, 0, 3, 4);
		CellRangeAddress ce3 = new CellRangeAddress(0, 0, 5, 6);
		CellRangeAddress ce4 = new CellRangeAddress(0, 0, 7, 8);
		CellRangeAddress ce5 = new CellRangeAddress(0, 0, 9, 10);
		sheet.addMergedRegion(ce1);
		sheet.addMergedRegion(ce2);
		sheet.addMergedRegion(ce3);
		sheet.addMergedRegion(ce4);
		sheet.addMergedRegion(ce5);
		Row row = sheet.createRow(0);
		row.createCell(0);
		Cell cell_1 = row.createCell(1);
		cell_1.setCellValue("新增用户");
		Cell cell_3 = row.createCell(3);
		cell_3.setCellValue("启动次数");
		Cell cell_5 = row.createCell(5);
		cell_5.setCellValue("下载量");
		Cell cell_7 = row.createCell(7);
		cell_7.setCellValue("更新量");
		Cell cell_9 = row.createCell(9);
		cell_9.setCellValue("使用时长");

		HSSFRow sheadRow = sheet.createRow(1);
		sheadRow.createCell(0).setCellValue("日期");
		sheadRow.createCell(1).setCellValue("IOS");
		sheadRow.createCell(2).setCellValue("Android");
		sheadRow.createCell(3).setCellValue("IOS");
		sheadRow.createCell(4).setCellValue("Android");
		sheadRow.createCell(5).setCellValue("IOS");
		sheadRow.createCell(6).setCellValue("Android");
		sheadRow.createCell(7).setCellValue("IOS");
		sheadRow.createCell(8).setCellValue("Android");
		sheadRow.createCell(9).setCellValue("IOS");
		sheadRow.createCell(10).setCellValue("Android");

		if (null != list && list.size() > 0) {
			Iterator<Apptj> iterator = list.iterator();
			while (iterator.hasNext()) {
				Apptj detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getIosnew());
				dataRow.createCell(2).setCellValue(detail.getAndnew());
				dataRow.createCell(3).setCellValue(detail.getIosqd());
				dataRow.createCell(4).setCellValue(detail.getAndqd());
				dataRow.createCell(5).setCellValue(detail.getIosxzl());
				dataRow.createCell(6).setCellValue(detail.getAndxzl());
				dataRow.createCell(7).setCellValue(detail.getIosgx());
				dataRow.createCell(8).setCellValue(detail.getAndgx());
				dataRow.createCell(9).setCellValue(detail.getIossc());
				dataRow.createCell(10).setCellValue(detail.getAndsc());
			}
		}

		String filename = "app统计.xls";
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

	// ===============版本安装量======================

	/**
	 * 跳转到版本安装量
	 */
	@RequestMapping("/edition")
	public String editionFrameshow(Model model) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		int yesweeks = yesterday.getWeekOfWeekyear();
		int lastmonthweeks = lastmonth.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("yesweeks", yesterday.toString("yyyy-MM-dd") + " " + yesweeks);
		// 上个月 + 周数
		model.addAttribute("lastmonthweeks", lastmonth.toString("yyyy-MM-dd") + " " + lastmonthweeks);

		// 日
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));
		return "statisticy/edition/frame";
	}

	/**
	 * 版本安装量的table
	 * 
	 * @return
	 */
	@RequestMapping(value = "/editionTable")
	public String editionGrid(Model model, Date startDate, Date endDate, String status) {
		List<Edition> resultList = nlcStatisticServicey.EditionTableData(startDate, endDate, status);
		model.addAttribute("resultList", resultList);
		return "statisticy/edition/xztable";
	}

	/**
	 * 版本安装量的导出
	 */
	@RequestMapping("/editionExport")
	public void editionExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<Edition> list = nlcStatisticServicey.EditionTableData(startDate, endDate, status);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("版本安装量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("日期");
		headRow.createCell(1).setCellValue("类别");
		headRow.createCell(2).setCellValue("版本");
		headRow.createCell(3).setCellValue("安装量");

		if (null != list && list.size() > 0) {
			Iterator<Edition> iterator = list.iterator();
			while (iterator.hasNext()) {
				Edition detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getType());
				dataRow.createCell(2).setCellValue(detail.getVersion());
				dataRow.createCell(3).setCellValue(detail.getNum());
			}
		}

		String filename = "版本安装量.xls";
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

	// ===============装机详情--手机型号的统计===================================

	/**
	 * 跳转到装机详情
	 */
	@RequestMapping("/model")
	public String modelFrameshow(Model model) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		int yesweeks = yesterday.getWeekOfWeekyear();
		int lastmonthweeks = lastmonth.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("yesweeks", yesterday.toString("yyyy-MM-dd") + " " + yesweeks);
		// 上个月 + 周数
		model.addAttribute("lastmonthweeks", lastmonth.toString("yyyy-MM-dd") + " " + lastmonthweeks);

		// 日
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));
		return "statisticy/model/frame";
	}

	/**
	 * 装机详情的table
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modelTable")
	public String modelTable(Model model, Date startDate, Date endDate, String status) {
		List<Modelx> resultList = nlcStatisticServicey.modelTableData(startDate, endDate, status);
		model.addAttribute("resultList", resultList);
		return "statisticy/model/xztable";
	}

	/**
	 * 装机详情的导出
	 */
	@RequestMapping("/modelExport")
	public void modelExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<Modelx> list = nlcStatisticServicey.modelTableData(startDate, endDate, status);
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("装机详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("日期");
		headRow.createCell(1).setCellValue("机型");
		headRow.createCell(2).setCellValue("新增装机");
		headRow.createCell(3).setCellValue("启动次数");

		if (null != list && list.size() > 0) {
			Iterator<Modelx> iterator = list.iterator();
			while (iterator.hasNext()) {
				Modelx detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getModelName());
				dataRow.createCell(2).setCellValue(detail.getAddInstallNum());
				dataRow.createCell(3).setCellValue(detail.getStartNum());
			}
		}

		String filename = "装机详情.xls";
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

	// =============================================

	/**
	 * 地域分析
	 */
	@RequestMapping("/dyfx")
	public String dyfxFrameshow(Model model) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		int yesweeks = yesterday.getWeekOfWeekyear();
		int lastmonthweeks = lastmonth.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("yesweeks", yesterday.toString("yyyy-MM-dd") + " " + yesweeks);
		// 上个月 + 周数
		model.addAttribute("lastmonthweeks", lastmonth.toString("yyyy-MM-dd") + " " + lastmonthweeks);

		// 日
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));
		return "statisticy/dyfx/frame";
	}

	/**
	 * 地域分析条形图的数据
	 */
	@RequestMapping(value = "/dyfxData")
	@ResponseBody
	public List<DyfxPoExt> dyfxData(Date startDate, Date endDate, String status) {
		List<DyfxPoExt> list = nlcStatisticServicey.dyfxData(startDate, endDate, status);
		return list;
	}

	/**
	 * 地域分析中的列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dyfxProTable")
	public String dyfxProTable(Model model, Date startDate, Date endDate, String status, String type) {
		List<Dyfx> resultList = nlcStatisticServicey.dyfxTableData(startDate, endDate, status, type);
		model.addAttribute("resultList", resultList);
		return "statisticy/dyfx/xztable";
	}

	/**
	 * 地域分析的导出
	 */
	@RequestMapping("/dyfxExport")
	public void dyfxExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status, String type) {
		List<Dyfx> list = nlcStatisticServicey.dyfxTableData(startDate, endDate, status, type);
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("地域分析");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("日期");
		headRow.createCell(1).setCellValue("省市");
		headRow.createCell(2).setCellValue("新增用户");

		if (null != list && list.size() > 0) {
			Iterator<Dyfx> iterator = list.iterator();
			while (iterator.hasNext()) {
				Dyfx detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getLocation());
				dataRow.createCell(2).setCellValue(detail.getNum());
			}
		}

		String filename = "地域分析.xls";
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
