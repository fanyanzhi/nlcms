package cn.gov.nlc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Nlcuseronline;
import cn.gov.nlc.service.NlcStatisticService;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlcuseronlineService;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.vo.DyfxPo;
import cn.gov.nlc.vo.DyfxPoExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.GzyhxPo;
import cn.gov.nlc.vo.NewInstallUserDetail;
import cn.gov.nlc.vo.OsDetail;
import cn.gov.nlc.vo.ResultVo;
import cn.gov.nlc.vo.RfwlfbPo;
import cn.gov.nlc.vo.SexDis;
import cn.gov.nlc.vo.SharePo;
import cn.gov.nlc.vo.StartCountDetail;
import cn.gov.nlc.vo.UserTypeDis;
import cn.gov.nlc.vo.VirtualUserDetail;
import cn.gov.nlc.vo.XsdfbPo;
import cn.gov.nlc.vo.Ydqk;
import cn.gov.nlc.vo.Ydyh;
import cn.gov.nlc.vo.ZyhxPo;
import cn.gov.nlc.vo.ZyhxTablePo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/statistic")
public class NlcStatisticController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcStatisticController.class);

	@Autowired
	private NlcStatisticService nlcStatisticService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	@Autowired
	private NlcuseronlineService nlcuseronlineService;

	/**
	 * 跳转到实时统计页面
	 */
	@RequestMapping("/sstj")
	public String show(HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看实时统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "statistic/sstj";
	}

	/**
	 * 实时统计今日新增用户数
	 */
	@RequestMapping("/sstjXzyhsjr")
	@ResponseBody
	public Collection<Object> sstjXzyhsjr() {
		Collection<Object> list = nlcStatisticService.sstjXzyhsjr();
		return list;
	}

	/**
	 * 实时统计今日新增用户总数
	 */
	@RequestMapping("/sstjXzyhsjr2")
	@ResponseBody
	public Integer sstjXzyhsjr2() {
		Integer result = nlcStatisticService.sstjXzyhsjr2();
		return result;
	}

	/**
	 * 实时统计今日启动次数
	 */
	@RequestMapping("/sstjQdcsjr")
	@ResponseBody
	public Collection<Object> sstjQdcsjr() {
		Collection<Object> list = nlcStatisticService.sstjQdcsjr();
		return list;
	}

	/**
	 * 实时统计今日启动总次数
	 */
	@RequestMapping("/sstjQdcsjr2")
	@ResponseBody
	public Integer sstjQdcsjr2() {
		Integer result = nlcStatisticService.sstjQdcsjr2();
		return result;
	}

	// ==============新增用户start========================

	/**
	 * 跳转到用户分析下的 新增用户页面
	 */
	@RequestMapping("/yhfx/xzyh")
	public String xzyhFrameshow(Model model, HttpSession session) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		int yesweeks = yesterday.getWeekOfWeekyear();
		int lastmonthweeks = lastmonth.getWeekOfWeekyear();

		model.addAttribute("yesweeks", yesterday.toString("yyyy-MM-dd") + " " + yesweeks);
		model.addAttribute("lastmonthweeks", lastmonth.toString("yyyy-MM-dd") + " " + lastmonthweeks);

		// 日
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));
		return "statistic/xzyh/frame";
	}

	/**
	 * 新增用户趋势
	 * 
	 * @return
	 */
	@RequestMapping("/xzyhqs")
	@ResponseBody
	public JSONObject xzyhqs(Date startDate, Date endDate) {
		List<String> labelsList = new ArrayList<String>();
		List<Integer> xnList = new ArrayList<Integer>();
		List<Integer> smList = new ArrayList<Integer>();
		List<Integer> ckList = new ArrayList<Integer>();
		List<Integer> allList = new ArrayList<Integer>();

		List<VirtualUserDetail> list1=nlcStatisticService.xnyhslDataList(startDate, endDate, "day");
		List<VirtualUserDetail> list2=nlcStatisticService.smyhslDataList(startDate, endDate, "day");
		List<VirtualUserDetail> list3=nlcStatisticService.ckyhslDataList(startDate, endDate, "day");
		
		for ( int i=0;i<list1.size();i++) {
			labelsList.add("\"" + list1.get(i).getMtime() + "\"");
			xnList.add(list1.get(i).getMonthNewAddNum());
			smList.add(list2.get(i).getMonthNewAddNum());
			ckList.add(list3.get(i).getMonthNewAddNum());
			allList.add(list1.get(i).getMonthNewAddNum()+list2.get(i).getMonthNewAddNum()+list3.get(i).getMonthNewAddNum());
		}
		String result = "{labels:" + labelsList  + ",flow1:" + xnList + ",flow2:"+smList+",flow3:"+ckList+",flow4:"+allList+"}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 新增用户中的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/xzyhGrid")
	public String xzyhGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statistic/xzyh/xzyhGrid";
	}

	/**
	 * 新增用户中的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/xzyhList")
	@ResponseBody
	public EasyUiDataGridResult xzyhList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}
		String type = status;
		//虚拟用户
		List<VirtualUserDetail> result_xn = nlcStatisticService.xnyhtableList(page, rows, startDate, endDate, type, false);
		//实名用户
		List<VirtualUserDetail> result_sm = nlcStatisticService.smyhtableList(page, rows, startDate, endDate, type, false);
		//持卡用户
		List<VirtualUserDetail> result_ck = nlcStatisticService.ckyhtableList(page, rows, startDate, endDate, type, false);
		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticService.rxzyhList(page, rows, startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticService.zxzyhList(page, rows, startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticService.monrxzyhList(page, rows, startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticService.nrxzyhList(page, rows, startDate, endDate);
		}
		List<NewInstallUserDetail> list =(List<NewInstallUserDetail>) result.getRows();
		
		
		for (int i = 0; i < list.size(); i++) {
			NewInstallUserDetail nu = list.get(i);
			int u1= result_xn.get(i).getMonthNewAddNum();
			int u2= result_sm.get(i).getMonthNewAddNum();
			int u3= result_ck.get(i).getMonthNewAddNum();
			nu.setXnyh_NewAddNum(u1);
			nu.setSmyh_NewAddNum(u2);
			nu.setCkyh_NewAddNum(u3);
			nu.setAll_NewAddNum(u1+u2+u3);
		}
		return result;
	}

	/**
	 * 新增用户导出统计
	 */
	@RequestMapping("/xzyhExport")
	public void xzyhExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		String type = status;
		//虚拟用户
		List<VirtualUserDetail> result_xn = nlcStatisticService.xnyhtableList(null, null, startDate, endDate, type, true);
		//实名用户
		List<VirtualUserDetail> result_sm = nlcStatisticService.smyhtableList(null, null, startDate, endDate, type, true);
		//持卡用户
		List<VirtualUserDetail> result_ck = nlcStatisticService.ckyhtableList(null, null, startDate, endDate, type, true);
		List<NewInstallUserDetail> list = new ArrayList<NewInstallUserDetail>();
		for (int i = 0; i < result_xn.size(); i++) {
			NewInstallUserDetail nu = new NewInstallUserDetail();
			nu.setPeriod(result_xn.get(i).getMtime());
			int u1= result_xn.get(i).getMonthNewAddNum();
			int u2= result_sm.get(i).getMonthNewAddNum();
			int u3= result_ck.get(i).getMonthNewAddNum();
			nu.setXnyh_NewAddNum(u1);
			nu.setSmyh_NewAddNum(u2);
			nu.setCkyh_NewAddNum(u3);
			nu.setAll_NewAddNum(u1+u2+u3);
			list.add(nu);
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("新增用户");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("日期");
		headRow.createCell(1).setCellValue("虚拟用户");
		headRow.createCell(2).setCellValue("实名用户");
		headRow.createCell(3).setCellValue("持卡用户");
		headRow.createCell(4).setCellValue("新增用户总数");

		if (null != list && list.size() > 0) {
			Iterator<NewInstallUserDetail> iterator = list.iterator();
			while (iterator.hasNext()) {
				NewInstallUserDetail detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getPeriod());
				dataRow.createCell(1).setCellValue(detail.getXnyh_NewAddNum());
				dataRow.createCell(2).setCellValue(detail.getSmyh_NewAddNum());
				dataRow.createCell(3).setCellValue(detail.getCkyh_NewAddNum());
				dataRow.createCell(4).setCellValue(detail.getAll_NewAddNum());
			}
		}

		String filename = "新增用户.xls";
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

	// ======按月的统计=========

	/**
	 * 新增用户趋势 按月统计
	 * 
	 * @return
	 */
	@RequestMapping("/monxzyhqs")
	@ResponseBody
	public JSONObject monxzyhqs(Date startDate, Date endDate) {
		List<String> labelsList = new ArrayList<String>();
		List<Integer> xnList = new ArrayList<Integer>();
		List<Integer> smList = new ArrayList<Integer>();
		List<Integer> ckList = new ArrayList<Integer>();
		List<Integer> allList = new ArrayList<Integer>();

		List<VirtualUserDetail> list1=nlcStatisticService.xnyhslDataList(startDate, endDate, "month");
		List<VirtualUserDetail> list2=nlcStatisticService.smyhslDataList(startDate, endDate, "month");
		List<VirtualUserDetail> list3=nlcStatisticService.ckyhslDataList(startDate, endDate, "month");
		
		for ( int i=0;i<list1.size();i++) {
			labelsList.add("\"" + list1.get(i).getMtime() + "\"");
			xnList.add(list1.get(i).getMonthNewAddNum());
			smList.add(list2.get(i).getMonthNewAddNum());
			ckList.add(list3.get(i).getMonthNewAddNum());
			allList.add(list1.get(i).getMonthNewAddNum()+list2.get(i).getMonthNewAddNum()+list3.get(i).getMonthNewAddNum());
		}
		String result = "{labels:" + labelsList  + ",flow1:" + xnList + ",flow2:"+smList+",flow3:"+ckList+",flow4:"+allList+"}";
		return JSONObject.fromObject(result);
	}

	// =================按年统计=================================

	/**
	 * 新增用户趋势 按年统计
	 * update by JJJ 20170313
	 * @return
	 */
	@RequestMapping("/nxzyhqs")
	@ResponseBody
	public JSONObject nxzyhqs(Date startDate, Date endDate) {

		List<String> labelsList = new ArrayList<String>();
		List<Integer> xnList = new ArrayList<Integer>();
		List<Integer> smList = new ArrayList<Integer>();
		List<Integer> ckList = new ArrayList<Integer>();
		List<Integer> allList = new ArrayList<Integer>();

		List<VirtualUserDetail> list1=nlcStatisticService.xnyhslDataList(startDate, endDate, "year");
		List<VirtualUserDetail> list2=nlcStatisticService.smyhslDataList(startDate, endDate, "year");
		List<VirtualUserDetail> list3=nlcStatisticService.ckyhslDataList(startDate, endDate, "year");
		
		for ( int i=0;i<list1.size();i++) {
			labelsList.add("\"" + list1.get(i).getMtime() + "\"");
			xnList.add(list1.get(i).getMonthNewAddNum());
			smList.add(list2.get(i).getMonthNewAddNum());
			ckList.add(list3.get(i).getMonthNewAddNum());
			allList.add(list1.get(i).getMonthNewAddNum()+list2.get(i).getMonthNewAddNum()+list3.get(i).getMonthNewAddNum());
		}
		String result = "{labels:" + labelsList  + ",flow1:" + xnList + ",flow2:"+smList+",flow3:"+ckList+",flow4:"+allList+"}";
		return JSONObject.fromObject(result);
	}

	// ===========按周统计===================

	/**
	 * 新增用户趋势 按周统计
	 * 
	 * @return
	 */
	@RequestMapping("/zxzyhqs")
	@ResponseBody
	public JSONObject zxzyhqs(Date startDate, Date endDate) {
		List<String> labelsList = new ArrayList<String>();
		List<Integer> xnList = new ArrayList<Integer>();
		List<Integer> smList = new ArrayList<Integer>();
		List<Integer> ckList = new ArrayList<Integer>();
		List<Integer> allList = new ArrayList<Integer>();

		List<VirtualUserDetail> list1=nlcStatisticService.xnyhslDataList(startDate, endDate, "week");
		List<VirtualUserDetail> list2=nlcStatisticService.smyhslDataList(startDate, endDate, "week");
		List<VirtualUserDetail> list3=nlcStatisticService.ckyhslDataList(startDate, endDate, "week");
		
		for ( int i=0;i<list1.size();i++) {
			labelsList.add("\"" + list1.get(i).getMtime() + "\"");
			xnList.add(list1.get(i).getMonthNewAddNum());
			smList.add(list2.get(i).getMonthNewAddNum());
			ckList.add(list3.get(i).getMonthNewAddNum());
			allList.add(list1.get(i).getMonthNewAddNum()+list2.get(i).getMonthNewAddNum()+list3.get(i).getMonthNewAddNum());
		}
		String result = "{labels:" + labelsList  + ",flow1:" + xnList + ",flow2:"+smList+",flow3:"+ckList+",flow4:"+allList+"}";
		return JSONObject.fromObject(result);
	}

	// ================新增用户end====================

	/**
	 * 跳转到用户分析下的 启动次数页面
	 */
	@RequestMapping("/yhfx/qdcs")
	public String qdcsshow(Model model, HttpSession session) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastweek = now.minusWeeks(1);
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastweek", lastweek.toString("yyyy-MM-dd"));

		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看启动次数统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "statistic/yhfx/qdcs";
	}

	/**
	 * 跳转到用户分析/启动次数/启动次数明细页面
	 */
	@RequestMapping("/qdcsmx")
	public String qdcsmx(Date startDate, Date endDate, Model model) {
		List<StartCountDetail> startList = nlcStatisticService.qdcsmx(startDate, endDate);
		Collections.reverse(startList);
		model.addAttribute("startList", startList);
		return "statistic/yhfx/qdcsmx";
	}

	// ================用户分析/用户数量==
	/**
	 * 跳转到用户分析/用户数量 update by JJJ 20170223 am
	 * 
	 * @return
	 */
	@RequestMapping("/yhfx/yhsl")
	public String yhsl(Model model, HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看用户数量统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		
		
		DateTime now = new DateTime();
		DateTime startDate = new DateTime(now.minusMonths(3).toString("yyyy-MM-01"));
		DateTime endDate = new DateTime(now.minusDays(1).toString("yyyy-MM-01"));

		int startDateweeks = startDate.getWeekOfWeekyear();
		int endDateweeks = endDate.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("startDateweeks", startDate.toString("yyyy-MM-dd") + " " + startDateweeks);
		// 上个月 + 周数
		model.addAttribute("endDateweeks", endDate.toString("yyyy-MM-dd") + " " + endDateweeks);

		// 日
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "statistic/yhfx/yhsl";
	}

	/**
	 * 跳转到用户分析/用户数量/用户类型分布
	 *  update by JJJ 20170223 am
	 * 
	 * @return
	 */
	@RequestMapping("/yhlxfb")
	public String yhlxfb(Model model, Date startDate, Date endDate) {
		Map<String, String> resmap = nlcStatisticService.yhlxfb1(startDate, endDate);
		model.addAttribute("resmap", resmap);
		List<Integer> list = nlcStatisticService.yhlxfb2(startDate, endDate);
		model.addAttribute("numList", list);
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		model.addAttribute("startDate", sdt.toString("yyyy-MM-dd"));
		model.addAttribute("endDate", edt.toString("yyyy-MM-dd"));
		return "statistic/yhfx/yhlxfb";
	}

	// /**
	// * 跳转到用户分析/用户数量/用户类型分布1
	// *
	// * @return
	// */
	// @RequestMapping("/yhlxfb1")
	// public String yhlxfb1(Model model) {
	// Map<String, String> resmap = nlcStatisticService.yhlxfb1();
	// model.addAttribute("resmap", resmap);
	// return "statistic/yhfx/yhlxfb1";
	// }

	// /**
	// * 跳转到用户分析/用户数量/用户类型分布2
	// *
	// * @return
	// */
	// @RequestMapping("/yhlxfb2")
	// public String yhlxfb2(Model model) {
	// List<Integer> list = nlcStatisticService.yhlxfb2();
	// model.addAttribute("numList", list);
	// return "statistic/yhfx/yhlxfb2";
	// }

	/**
	 * 各类型用户数据导出excel
	 */
	@RequestMapping("/yhsjExport")
	public void yhsjExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<Integer> list = nlcStatisticService.yhlxfb2(startDate, endDate);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("各类型用户数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("用户类型");
		headRow.createCell(1).setCellValue("数量");

		HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow.createCell(0).setCellValue("实名用户");
		dataRow.createCell(1).setCellValue(list.get(0));

		HSSFRow dataRow2 = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow2.createCell(0).setCellValue("持卡用户");
		dataRow2.createCell(1).setCellValue(list.get(1));

		HSSFRow dataRow3 = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow3.createCell(0).setCellValue("虚拟用户");
		dataRow3.createCell(1).setCellValue(list.get(2));

		HSSFRow dataRow4 = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow4.createCell(0).setCellValue("总计");
		dataRow4.createCell(1).setCellValue(list.get(3));

		String filename = "各类型用户数据.xls";
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
	 * 跳转到用户分析/用户数量/虚拟用户
	 * 
	 * @return
	 */
	@RequestMapping("/xnyhs")
	public String yhslsec2(Model model, HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看用户数量-虚拟用户统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);

		DateTime now = new DateTime();
		DateTime startDate = new DateTime(now.minusMonths(3).toString("yyyy-MM-01"));
		DateTime endDate = new DateTime(now.minusDays(1).toString("yyyy-MM-01"));

		int startDateweeks = startDate.getWeekOfWeekyear();
		int endDateweeks = endDate.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("startDateweeks", startDate.toString("yyyy-MM-dd") + " " + startDateweeks);
		// 上个月 + 周数
		model.addAttribute("endDateweeks", endDate.toString("yyyy-MM-dd") + " " + endDateweeks);

		// 日
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statistic/yhfx/xnyhFrame";
	}

	/**
	 * 新增用户趋势 update by JJJ
	 * 
	 * @return
	 */
	@RequestMapping("/yhslPic3List")
	@ResponseBody
	public JSONObject yhslPic3List(Date startDate, Date endDate, String type) {

		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {// 起始时间必须小于等于结束时间
			String res = "{result:false}";
			return JSONObject.fromObject(res);
		}

		//String result = nlcStatisticService.xnyhslDataList(startDate, endDate, type);

		return JSONObject.fromObject(null);

		// List<String> labelsList = new ArrayList<String>();
		// while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
		// labelsList.add(startDateJoda.toString("yyyy-MM"));
		// startDateJoda = startDateJoda.plusMonths(1);
		// }
		// List<Integer> flowList =
		// nlcStatisticService.xnyhslDataList(startDate, endDate);
		// String result = "{result:true,flow:" + flowList + ", labels:" +
		// labelsList + "}";
	}

	/**
	 * 跳转到用户分析/用户数量/虚拟用户数量第二个图
	 * 
	 * @return
	 */
	@RequestMapping("/xnyhtable")
	public String xnyhtable(Model model, Date startDate, Date endDate, String type) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("type", type);
		return "statistic/yhfx/xnyhtable";
	}

	/**
	 * 用户分析/用户数量/虚拟用户数量第二个图的list update by JJJ
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/xnyhtableList")
	@ResponseBody
	public EasyUiDataGridResult xnyhtableList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String type) {
		//EasyUiDataGridResult result = nlcStatisticService.xnyhtableList(page, rows, startDate, endDate, type, false);
		//return result;
		return null;
	}

	/**
	 * 虚拟用户导出excel update by JJJ
	 */
	@RequestMapping("/xnyhExport")
	public void xnyhExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String type) {
		EasyUiDataGridResult result =null;// nlcStatisticService.xnyhtableList(null, null, startDate, endDate, type, true);
		List<VirtualUserDetail> list = new ArrayList<VirtualUserDetail>();
		if (result != null) {
			list = (List<VirtualUserDetail>) result.getRows();
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("虚拟用户数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("新增量");
		headRow.createCell(2).setCellValue("本年累计新增量");

		if (null != list && list.size() > 0) {
			Iterator<VirtualUserDetail> iterator = list.iterator();
			while (iterator.hasNext()) {
				VirtualUserDetail detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

				dataRow.createCell(0).setCellValue(detail.getMtime());
				dataRow.createCell(1).setCellValue(detail.getMonthNewAddNum());
				dataRow.createCell(2).setCellValue(detail.getThisYearAccNum());
			}
		}

		String filename = "虚拟用户数据-" + type + ".xls";
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
	 * 跳转到用户分析下的 用户属性页面
	 */
	@RequestMapping("/yhfx/yhsx")
	public String yhsxshow(Model model, HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看用户属性统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);

		
		DateTime now = new DateTime();
		DateTime startDate = new DateTime(now.minusMonths(3).toString("yyyy-MM-01"));
		DateTime endDate = new DateTime(now.minusDays(1).toString("yyyy-MM-01"));

		int startDateweeks = startDate.getWeekOfWeekyear();
		int endDateweeks = endDate.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("startDateweeks", startDate.toString("yyyy-MM-dd") + " " + startDateweeks);
		// 上个月 + 周数
		model.addAttribute("endDateweeks", endDate.toString("yyyy-MM-dd") + " " + endDateweeks);

		// 日
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "statistic/yhsx/showFrame";
	}

	/**
	 * 跳转到用户分析/用户属性 第一个图的frameset
	 */
	@RequestMapping("/yhsx1")
	public String yhsx1Frame(Model model, Date startDate, Date endDate) {
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		model.addAttribute("startDate", sdt.toString("yyyy-MM-dd"));
		model.addAttribute("endDate", edt.toString("yyyy-MM-dd"));
		return "statistic/yhsx/yhsx1Frame";
	}

	/**
	 * 跳转到用户分析/用户属性 第一个图的第一个小图 
	 * update by JJJ 20170222 pm
	 */
	@RequestMapping("/yhsx1p1")
	public String yhsx1p1(Model model, Date startDate, Date endDate) {
		Map<String, String> resmap = nlcStatisticService.yhsx1p1(startDate, endDate);
		model.addAttribute("resmap", resmap);
		return "statistic/yhsx/yhsx1p1";
	}

	/**
	 * 跳转到用户分析/用户属性 第一个图的第二个小图
	 */
	@RequestMapping("/yhsx1p2")
	public String yhsx1p2(Model model, Date startDate, Date endDate) {
		Map<String, String> resmap = nlcStatisticService.yhsx1p2(startDate, endDate);
		model.addAttribute("resmap", resmap);
		return "statistic/yhsx/yhsx1p2";
	}

	/**
	 * 跳转到用户分析/用户属性 第一个图的第二个小图 
	 * update by JJJ 20170222 pm
	 */
	@RequestMapping("/yhsx1p3")
	public String yhsx1p3(Model model, Date startDate, Date endDate) {
		List<UserTypeDis> ageList = nlcStatisticService.yhsx1p3(startDate, endDate);
		List<SexDis> sexList = nlcStatisticService.yhsx1p3_2(startDate, endDate);
		model.addAttribute("ageList", ageList);
		model.addAttribute("sexList", sexList);

		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		model.addAttribute("startDate", sdt.toString("yyyy-MM-dd"));
		model.addAttribute("endDate", edt.toString("yyyy-MM-dd"));
		return "statistic/yhsx/yhsx1p3";
	}

	/**
	 * 用户属性年龄分布数据导出excel
	 */
	@RequestMapping("/yhsxnlExport")
	public void yhsxnlExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<UserTypeDis> list = nlcStatisticService.yhsx1p3(startDate, endDate);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("年龄分布数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("用户类型");
		headRow.createCell(1).setCellValue("18岁以下");
		headRow.createCell(2).setCellValue("19~23岁");
		headRow.createCell(3).setCellValue("24~30岁");
		headRow.createCell(4).setCellValue("31~40岁");
		headRow.createCell(5).setCellValue("40岁以上");
		headRow.createCell(6).setCellValue("总计");

		if (null != list && list.size() > 0) {
			for (UserTypeDis userTypeDis : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(userTypeDis.getText());
				dataRow.createCell(1).setCellValue(userTypeDis.getLevel1());
				dataRow.createCell(2).setCellValue(userTypeDis.getLevel2());
				dataRow.createCell(3).setCellValue(userTypeDis.getLevel3());
				dataRow.createCell(4).setCellValue(userTypeDis.getLevel4());
				dataRow.createCell(5).setCellValue(userTypeDis.getLevel5());
				dataRow.createCell(6).setCellValue(userTypeDis.getSum());

			}
		}

		String filename = "年龄分布数据.xls";
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
	 * 用户属性性别分布数据导出excel
	 */
	@RequestMapping("/yhsxxbExport")
	public void yhsxxbExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<SexDis> list = nlcStatisticService.yhsx1p3_2(startDate, endDate);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("性别分布数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("用户类型");
		headRow.createCell(1).setCellValue("男");
		headRow.createCell(2).setCellValue("女");
		headRow.createCell(3).setCellValue("总计");

		if (null != list && list.size() > 0) {
			for (SexDis sexDis : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(sexDis.getText());
				dataRow.createCell(1).setCellValue(sexDis.getLevel1());
				dataRow.createCell(2).setCellValue(sexDis.getLevel2());
				dataRow.createCell(3).setCellValue(sexDis.getSum());

			}
		}

		String filename = "性别分布数据.xls";
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
	 * 跳转到用户分析/用户属性 第二个图
	 */
	@RequestMapping("/yhsx2")
	public String yhsx2(Model model, Date startDate, Date endDate) {
		Map<String, String> resmap = nlcStatisticService.yhsxxl(startDate, endDate);
		List<UserTypeDis> xlList = nlcStatisticService.yhsxxlsj(startDate, endDate);
		model.addAttribute("resmap", resmap);
		model.addAttribute("xlList", xlList);
		DateTime sdt = new DateTime(startDate);
		DateTime edt = new DateTime(endDate);
		model.addAttribute("startDate", sdt.toString("yyyy-MM-dd"));
		model.addAttribute("endDate", edt.toString("yyyy-MM-dd"));
		return "statistic/yhsx/yhsx2Frame";
	}

	/**
	 * 用户属性年龄分布数据导出excel
	 */
	@RequestMapping("/yhsxxlExport")
	public void yhsxxlExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<UserTypeDis> list = nlcStatisticService.yhsxxlsj(startDate, endDate);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("学历分布数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("用户类型");
		headRow.createCell(1).setCellValue("专科以下");
		headRow.createCell(2).setCellValue("专科");
		headRow.createCell(3).setCellValue("本科");
		headRow.createCell(4).setCellValue("硕士");
		headRow.createCell(5).setCellValue("博士");
		headRow.createCell(6).setCellValue("总计");

		if (null != list && list.size() > 0) {
			for (UserTypeDis userTypeDis : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(userTypeDis.getText());
				dataRow.createCell(1).setCellValue(userTypeDis.getLevel1());
				dataRow.createCell(2).setCellValue(userTypeDis.getLevel2());
				dataRow.createCell(3).setCellValue(userTypeDis.getLevel3());
				dataRow.createCell(4).setCellValue(userTypeDis.getLevel4());
				dataRow.createCell(5).setCellValue(userTypeDis.getLevel5());
				dataRow.createCell(6).setCellValue(userTypeDis.getSum());

			}
		}

		String filename = "学历分布数据.xls";
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

	// /**
	// * 跳转到用户分析/用户属性 第二个图
	// */
	// @RequestMapping("/yhsx3")
	// public String yhsx3(Model model) {
	// Map<String, String> resmap = nlcStatisticService.yhsxxl();
	// model.addAttribute("resmap", resmap);
	// return "statistic/yhsx/yhsx3Frame";
	// }

	/**
	 * =========================================================================
	 * =========
	 */

	/**
	 * 跳转到终端属性下的 增加用户页面
	 */
	@RequestMapping("/zdsx/xzyh")
	public String zxzyhshow(Model model, HttpSession session) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastmonth = now.minusMonths(1);
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastmonth", lastmonth.toString("yyyy-MM-dd"));

		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看终端属性增加用户的统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "statistic/zdsx/xzyhFrame";
	}

	/**
	 * 新增用户os1的数据
	 * 
	 * @return
	 */
	@RequestMapping("/xzyhOs1")
	@ResponseBody
	public JSONObject xzyhOs1(Date startDate, Date endDate) {
		Map<String, String> resmap = nlcStatisticService.xzyhOs1(startDate, endDate);
		String result = "{flow:" + resmap + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 跳转到终端属性/增加用户/数据明细
	 */
	@RequestMapping("/xzyhOsmx")
	public String xzyhOsmx(Date startDate, Date endDate, Model model) {
		List<OsDetail> resList = nlcStatisticService.xzyhOsmx(startDate, endDate);
		model.addAttribute("resList", resList);
		return "statistic/zdsx/xzyhOsmx";
	}

	/**
	 * 新增用户os2的数据
	 * 
	 * @return
	 */
	@RequestMapping("/xzyhOs2")
	@ResponseBody
	public JSONObject xzyhOs2(Date startDate, Date endDate) {
		Map<String, String> resmap = nlcStatisticService.xzyhOs2(startDate, endDate);
		String result = "{flow:" + resmap + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * =========================================================================
	 * =========
	 */

	/**
	 * 跳转到终端属性下的 地域分析页面
	 */
	@RequestMapping("/zdsx/dyfx")
	public String dyfxshow(Model model, HttpSession session) {
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime lastweek = now.minusWeeks(1);
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("lastweek", lastweek.toString("yyyy-MM-dd"));

		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看终端属性地域分析的统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "statistic/zdsx/dyfxFrame";
	}

	/**
	 * 地域分析条形图的数据
	 */
	@RequestMapping(value = "/dyfxData")
	@ResponseBody
	public List<DyfxPoExt> dyfxData(Date startDate, Date endDate) {
		List<DyfxPoExt> list = nlcStatisticService.dyfxData(startDate, endDate);
		return list;
	}

	/**
	 * 地域分析中的列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dyfxGrid")
	public String dyfxGrid(Model model, Date startDate, Date endDate) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statistic/zdsx/dyfxGrid";
	}

	/**
	 * 地域分析中的列表的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/dyfxList")
	@ResponseBody
	public EasyUiDataGridResult dyfxList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate) {
		EasyUiDataGridResult result = nlcStatisticService.dyfxList(page, rows, startDate, endDate);
		return result;
	}

	/**
	 * 地域分析导出excel
	 */
	@RequestMapping("/dyfxExport")
	public void dyfxExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<DyfxPo> list = nlcStatisticService.dyfxExportList(startDate, endDate);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("省市数据明细");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("省市");
		headRow.createCell(1).setCellValue("新增用户");

		if (null != list && list.size() > 0) {
			Iterator<DyfxPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				DyfxPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

				dataRow.createCell(0).setCellValue(detail.getName());
				dataRow.createCell(1).setCellValue(detail.getValue());
			}
		}

		String filename = "省市数据明细.xls";
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

	// ===============实名用户数量

	/**
	 * 跳转到用户分析/用户数量/实名用户
	 * 
	 * @return
	 */
	@RequestMapping("/smyhs")
	public String smyhsview(Model model, HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看用户数量-实名用户统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);

		DateTime now = new DateTime();
		DateTime startDate = new DateTime(now.minusMonths(3).toString("yyyy-MM-01"));
		DateTime endDate = new DateTime(now.minusDays(1).toString("yyyy-MM-01"));

		int startDateweeks = startDate.getWeekOfWeekyear();
		int endDateweeks = endDate.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("startDateweeks", startDate.toString("yyyy-MM-dd") + " " + startDateweeks);
		// 上个月 + 周数
		model.addAttribute("endDateweeks", endDate.toString("yyyy-MM-dd") + " " + endDateweeks);

		// 日
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statistic/yhfx/smyhsFrame";
	}

	/**
	 * 新增实名用户数趋势 update by JJJ
	 * 
	 * @return
	 */
	@RequestMapping("/smyhs1List")
	@ResponseBody
	public JSONObject smyhs1List(Date startDate, Date endDate, String type) {
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {// 起始时间必须小于等于结束时间
			String res = "{result:false}";
			return JSONObject.fromObject(res);
		}

		//String result = nlcStatisticService.smyhslDataList(startDate, endDate, type);
		return JSONObject.fromObject(null);
	}

	/**
	 * 跳转到用户分析/用户数量/实名用户数量第二个图 update by JJJ
	 * 
	 * @return
	 */
	@RequestMapping("/smyhstable")
	public String smyhstable(Model model, Date startDate, Date endDate, String type) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("type", type);
		return "statistic/yhfx/smyhtable";
	}

	/**
	 * 用户分析/用户数量/实名用户数量第二个图的list update by JJJ
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/smyhtableList")
	@ResponseBody
	public EasyUiDataGridResult smyhs2List(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String type) {
//		EasyUiDataGridResult result = nlcStatisticService.smyhtableList(page, rows, startDate, endDate, type, false);
//		return result;
		return null;

	}

	/**
	 * 实名用户导出excel update by JJJ
	 */
	@RequestMapping("/smyhExport")
	public void smyhExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String type) {
		List<VirtualUserDetail> list = new ArrayList<VirtualUserDetail>();
		EasyUiDataGridResult result =null;// nlcStatisticService.smyhtableList(null, null, startDate, endDate, type, true);
		if (result != null) {
			list = (List<VirtualUserDetail>) result.getRows();
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("实名用户数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("新增量");
		headRow.createCell(2).setCellValue("本年累计新增量");

		if (null != list && list.size() > 0) {
			Iterator<VirtualUserDetail> iterator = list.iterator();
			while (iterator.hasNext()) {
				VirtualUserDetail detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

				dataRow.createCell(0).setCellValue(detail.getMtime());
				dataRow.createCell(1).setCellValue(detail.getMonthNewAddNum());
				dataRow.createCell(2).setCellValue(detail.getThisYearAccNum());
			}
		}

		String filename = "实名用户数据.xls";
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

	// =================持卡用户数量

	/**
	 * 跳转到用户分析/用户数量/持卡用户
	 * 
	 * @return
	 */
	@RequestMapping("/ckyhs")
	public String ckyhsview(Model model, HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");
		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看用户数量-持卡用户统计");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);

		DateTime now = new DateTime();
		DateTime startDate = new DateTime(now.minusMonths(3).toString("yyyy-MM-01"));
		DateTime endDate = new DateTime(now.minusDays(1).toString("yyyy-MM-01"));

		int startDateweeks = startDate.getWeekOfWeekyear();
		int endDateweeks = endDate.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("startDateweeks", startDate.toString("yyyy-MM-dd") + " " + startDateweeks);
		// 上个月 + 周数
		model.addAttribute("endDateweeks", endDate.toString("yyyy-MM-dd") + " " + endDateweeks);

		// 日
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statistic/yhfx/ckyhs";
	}

	/**
	 * 跳转到用户分析/用户数量/持卡用户数量第一个大图 （包含两个小图） update by JJJ
	 * 
	 * @return
	 */
	@RequestMapping("/ckyhsFrame")
	public String ckyhsFrame(Model model, Date startDate, Date endDate, String startDateweeks, String endDateweeks) {
		// 昨天 + 周数
		model.addAttribute("startDateweeks", startDateweeks);
		model.addAttribute("endDateweeks", endDateweeks);
		model.addAttribute("startDate", new DateTime(startDate));
		model.addAttribute("endDate", new DateTime(endDate));
		return "statistic/yhfx/ckyhsFrame";
	}

	// /**
	// * 新增持卡用户数趋势
	// *
	// * @return
	// */
	// @RequestMapping("/ckyhs1List")
	// @ResponseBody
	// public JSONObject ckyhs1List(Date startDate, Date endDate) {
	// List<String> labelsList = new ArrayList<String>();
	//
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// String res = "{result:false}";
	// return JSONObject.fromObject(res);
	// }
	//
	// while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// labelsList.add(startDateJoda.toString("yyyy-MM"));
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// List<Integer> flowList = nlcStatisticService.ckyhslDataList(startDate,
	// endDate);
	//
	// String result = "{result:true,flow:" + flowList + ", labels:" +
	// labelsList + "}";
	// return JSONObject.fromObject(result);
	// }
	/**
	 * 新增持卡用户数趋势 add by JJJ
	 * 
	 * @return
	 */
	@RequestMapping("/ckyhs1List")
	@ResponseBody
	public JSONObject ckyhs1List(Date startDate, Date endDate, String type) {
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {// 起始时间必须小于等于结束时间
			String res = "{result:false}";
			return JSONObject.fromObject(res);
		}

		//String result = nlcStatisticService.ckyhslDataList(startDate, endDate, type);
		return JSONObject.fromObject(null);
	}

	/**
	 * 跳转到用户分析/用户数量/持卡用户数量第一个大图的第二个列表图
	 * 
	 * @return
	 */
	@RequestMapping("/ckyhtable")
	public String ckyhtable(Model model, Date startDate, Date endDate, String type) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("type", type);
		return "statistic/yhfx/ckyhtable";
	}

	/**
	 * 用户分析/用户数量/持卡用户数量第二个图的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/ckyhtableList")
	@ResponseBody
	public EasyUiDataGridResult ckyhtableList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String type) {
//		EasyUiDataGridResult result = nlcStatisticService.ckyhtableList(page, rows, startDate, endDate, type, false);
//		return result;
		return null;

	}

	/**
	 * 持卡用户导出excel update by JJJ
	 */
	@RequestMapping("/ckyhExport")
	public void ckyhExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String type) {
		List<VirtualUserDetail> list = new ArrayList<VirtualUserDetail>();
		EasyUiDataGridResult result = null;//nlcStatisticService.ckyhtableList(null, null, startDate, endDate, type, true);
		if (request != null) {
			list = (List<VirtualUserDetail>) result.getRows();
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("持卡用户数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("新增量");
		headRow.createCell(2).setCellValue("本年累计新增量");

		if (null != list && list.size() > 0) {
			Iterator<VirtualUserDetail> iterator = list.iterator();
			while (iterator.hasNext()) {
				VirtualUserDetail detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

				dataRow.createCell(0).setCellValue(detail.getMtime());
				dataRow.createCell(1).setCellValue(detail.getMonthNewAddNum());
				dataRow.createCell(2).setCellValue(detail.getThisYearAccNum());
			}
		}

		String filename = "持卡用户数据" + type + ".xls";
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

	// /**
	// * 跳转到用户分析/用户数量/持卡用户数量第三个图
	// *
	// * @return
	// */
	// @RequestMapping("/ckyhs3")
	// public String ckyhs3pic(Model model, Date startDate, Date endDate) {
	// DateTime formermonth = new DateTime(startDate);
	// DateTime yesterday = new DateTime(endDate);
	// model.addAttribute("yesterday", yesterday.toString("yyyy-MM-01"));
	// model.addAttribute("formermonth", formermonth.toString("yyyy-MM-01"));
	//
	// return "statistic/yhfx/ckyhs3";
	// }
	/**
	 * 跳转到用户分析/用户数量/持卡用户数量第三个图 add by JJJ
	 * 
	 * @return
	 */
	@RequestMapping("/ckyhs3")
	public String ckyhs3pic(Model model, Date startDate, Date endDate, String startDateweeks, String endDateweeks) {
		model.addAttribute("startDateweeks", startDateweeks);
		model.addAttribute("endDateweeks", endDateweeks);
		model.addAttribute("startDate", new DateTime(startDate));
		model.addAttribute("endDate", new DateTime(endDate));
		return "statistic/xzyh/ckyhs3";
	}

	/**
	 * 预约 续借趋势 add by JJJ
	 * 
	 * @return
	 */
	/**
	 */
	@RequestMapping("/ckyhyyxjList")
	@ResponseBody
	public JSONObject ckyhyyxjList(Date startDate, Date endDate, String type, String flag) {
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {// 起始时间必须小于等于结束时间
			String res = "{result:false}";
			return JSONObject.fromObject(res);
		}

		String result = nlcStatisticService.ckyhyyxjList(startDate, endDate, type, flag);

		return JSONObject.fromObject(result);
	}
	// /**
	// * 预约趋势
	// *
	// * @return
	// */
	// @RequestMapping("/ckyhs3List")
	// @ResponseBody
	// public JSONObject ckyhs3List(Date startDate, Date endDate) {
	// List<String> labelsList = new ArrayList<String>();
	//
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// String res = "{result:false}";
	// return JSONObject.fromObject(res);
	// }
	//
	// while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// labelsList.add(startDateJoda.toString("yyyy-MM"));
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// List<Integer> flowList = nlcStatisticService.ckholdList(startDate,
	// endDate);
	//
	// String result = "{result:true,flow:" + flowList + ", labels:" +
	// labelsList + "}";
	// return JSONObject.fromObject(result);
	// }
	//
	// /**
	// * 续借趋势
	// *
	// * @return
	// */
	// @RequestMapping("/ckyhs4List")
	// @ResponseBody
	// public JSONObject ckyhs4List(Date startDate, Date endDate) {
	// List<String> labelsList = new ArrayList<String>();
	//
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// String res = "{result:false}";
	// return JSONObject.fromObject(res);
	// }
	//
	// while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// labelsList.add(startDateJoda.toString("yyyy-MM"));
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// List<Integer> flowList = nlcStatisticService.renewList(startDate,
	// endDate);
	//
	// String result = "{result:true,flow:" + flowList + ", labels:" +
	// labelsList + "}";
	// return JSONObject.fromObject(result);
	// }

	/**
	 * 跳转到在线情况分析页面
	 */
	@RequestMapping("/zxqkfx")
	public String zxqkfxshow(HttpSession session) {
		return "statistic/zxqkfx";
	}

	/**
	 * 在线情况分析页面
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/zxqkfxList")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		EasyUiDataGridResult result = nlcuseronlineService.getNlcuseronlineList(page, rows);
		return result;
	}

	/**
	 * 在线情况分析导出excel
	 */
	@RequestMapping("/zxqkfxExport")
	public void zxqkfxExport(HttpServletRequest request, HttpServletResponse response) {
		List<Nlcuseronline> list = nlcuseronlineService.getAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("在线情况分析");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("用户ID");
		headRow.createCell(1).setCellValue("来访时间");
		headRow.createCell(2).setCellValue("用户地域");
		headRow.createCell(3).setCellValue("用户IP");

		if (null != list && list.size() > 0) {
			for (Nlcuseronline po : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(po.getLoginaccount());
				dataRow.createCell(1).setCellValue(po.getTime());
				dataRow.createCell(2).setCellValue(po.getLocation());
				dataRow.createCell(3).setCellValue(po.getAddress());
			}
		}

		String filename = "在线情况分析.xls";
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
	 * 跳转到页面统计页面
	 */
	/*
	 * @RequestMapping("/ymtj") public String ymtjshow(Model model, HttpSession
	 * session) { DateTime now = new DateTime(); DateTime yesterday =
	 * now.minusDays(1); DateTime formermonth = now.minusMonths(4);
	 * model.addAttribute("yesterday", yesterday.toString("yyyy-MM-01"));
	 * model.addAttribute("formermonth", formermonth.toString("yyyy-MM-01"));
	 * 
	 * return "statistic/ymtj"; }
	 */

	/**
	 * 针对年月的选择器的时间进行比较，确定起始时间要<=结束时间
	 * 
	 * @return
	 */
	@RequestMapping("/checkdate")
	@ResponseBody
	public JSONObject checkdate(Date startDate, Date endDate) {

		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		String res = "{result:true}";
		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {// 起始时间必须小于等于结束时间
			res = "{result:false}";
		}

		return JSONObject.fromObject(res);
	}

	/**
	 * 跳转到实时统计/页面统计/表格的页面
	 */
	/*
	 * @RequestMapping("/ymtjtable") public String ymtjtable(Date startDate,
	 * Date endDate, Model model) { List<PageStatistic> reslist =
	 * nlcStatisticService.ymtjtable(startDate, endDate);
	 * model.addAttribute("reslist", reslist); return "statistic/ymtjtable"; }
	 */

	/**
	 * 页面统计的excel导出
	 */
	/*
	 * @RequestMapping("/ymtjExport") public void ymtjExport(HttpServletRequest
	 * request, HttpServletResponse response, Date startDate, Date endDate) {
	 * List<PageStatistic> list = nlcStatisticService.ymtjtable(startDate,
	 * endDate); HSSFWorkbook workbook = new HSSFWorkbook(); HSSFSheet sheet =
	 * workbook.createSheet("页面统计"); HSSFRow headRow = sheet.createRow(0);
	 * headRow.createCell(0).setCellValue("");
	 * headRow.createCell(1).setCellValue("列表页访问次数");
	 * headRow.createCell(2).setCellValue("详情页访问次数");
	 * headRow.createCell(3).setCellValue("列表页停留时间");
	 * headRow.createCell(4).setCellValue("详情页停留时间");
	 * 
	 * if (null != list && list.size() > 0) { for (PageStatistic po : list) {
	 * HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1); if(null !=
	 * po.getModule()) { dataRow.createCell(0).setCellValue(po.getModule()); }
	 * 
	 * if(null != po.getListnum()) {
	 * dataRow.createCell(1).setCellValue(po.getListnum()); }
	 * 
	 * if(null != po.getDetailnum()) {
	 * dataRow.createCell(2).setCellValue(po.getDetailnum()); }
	 * 
	 * if(null != po.getListRemainTime()) {
	 * dataRow.createCell(3).setCellValue(po.getListRemainTime()); }
	 * 
	 * if(null != po.getDetailRemainTime()) {
	 * dataRow.createCell(4).setCellValue(po.getDetailRemainTime()); } } }
	 * 
	 * String filename = "页面统计.xls"; String agent =
	 * request.getHeader("User-Agent"); try { filename =
	 * FileUtils.encodeDownloadFilename(filename, agent); ServletOutputStream
	 * out = response.getOutputStream(); String mimeType =
	 * request.getServletContext().getMimeType(filename);
	 * response.setContentType(mimeType);
	 * response.setHeader("content-disposition", "attachment;filename=" +
	 * filename); workbook.write(out); } catch (IOException e) {
	 * logger.error(e.getMessage()); } }
	 */

	// ============================小时段分布start==============================

	/**
	 * 跳转到小时段分布页面
	 */
	@RequestMapping("/xsdfb")
	public String xsdfbshow(Model model, HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看启动次数-小时段分布");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);

		model.addAttribute("todaydate", new DateTime(new Date()).toString("yyyy-MM-dd"));
		return "statistic/llfxx/xsdfbframe";
	}

	/**
	 * 启动次数含重复的数据 启动次数
	 */
	@RequestMapping("/qdcsycList")
	@ResponseBody
	public Collection<Object> qdcsycList(Date calcdate) {
		Collection<Object> list = nlcStatisticService.qdcsycList(calcdate);
		return list;
	}

	/**
	 * 启动次数去重的数据 用户数量
	 */
	@RequestMapping("/qdcsqcList")
	@ResponseBody
	public Collection<Object> qdcsqcList(Date calcdate) {
		Collection<Object> list = nlcStatisticService.qdcsqcList(calcdate);
		return list;
	}

	/**
	 * 跳转到小时段分布的table
	 * 
	 * @return
	 */
	@RequestMapping("/xsdfbTable")
	public String xsdfbTable(Model model, Date calcdate) {
		List<XsdfbPo> resultList = nlcStatisticService.xsdfbTableDate(calcdate);
		model.addAttribute("resultList", resultList);
		return "statistic/llfxx/xsdfbTable";
	}

	/**
	 * 小时段分布的excel导出
	 */
	@RequestMapping("/xsdfbExport")
	public void xsdfbExport(HttpServletRequest request, HttpServletResponse response, Date calcdate) {
		List<XsdfbPo> list = nlcStatisticService.xsdfbTableDate(calcdate);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("小时段分布");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("启动次数");
		headRow.createCell(2).setCellValue("用户数量");

		if (null != list && list.size() > 0) {
			for (XsdfbPo po : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(po.getTime());
				dataRow.createCell(1).setCellValue(po.getQdcs());
				dataRow.createCell(2).setCellValue(po.getYhsl());
			}
		}

		String filename = "小时段分布.xls";
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

	// ============================小时度分布end=============================

	// ============================日访问量分布start=========================

	/**
	 * 跳转到日访问量分布页面
	 */
	@RequestMapping("/rfwlfb")
	public String rfwlfbshow(Model model, HttpSession session) {
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
		return "statistic/llfxx/rfwlfbframe";
	}

	/**
	 * 日访问量分布中的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/xrfwlfbGrid")
	public String xrfwlfbGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statistic/llfxx/xrfwlfbGrid";
	}

	/**
	 * 日访问量分布中的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/xrfwlfbList")
	@ResponseBody
	public EasyUiDataGridResult xrfwlfbList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticService.rfwlfbList(page, rows, startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticService.zRfwlfbList(page, rows, startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticService.monRfwlfbList(page, rows, startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticService.nRfwlfbList(page, rows, startDate, endDate);
		}

		return result;
	}

	/**
	 * 日访问量分布中的export
	 */
	@RequestMapping("/xrfwlExport")
	public void xrfwlExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<RfwlfbPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticService.rfwlExport1(startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticService.zRfwlExport(startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticService.monRfwlExport(startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticService.nRfwlExport(startDate, endDate);
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("日访问量分布");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("启动次数");
		headRow.createCell(2).setCellValue("用户数量");

		if (null != list && list.size() > 0) {
			Iterator<RfwlfbPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				RfwlfbPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getQdcs());
				dataRow.createCell(2).setCellValue(detail.getYhsl());
			}
		}

		String filename = "日访问量分布.xls";
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

	// *****==按月统计

	/**
	 * 启动次数趋势 不去重的，启动次数
	 */
	@RequestMapping("/monqdcsqs")
	@ResponseBody
	public JSONObject monqdcsqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int months = Months.monthsBetween(dstartDate, dendDate).getMonths();

		List<String> labelsList = new ArrayList<String>();
		if (months <= 55) {
			for (int i = 0; i < months + 1; i++) {
				labelsList.add(dstartDate.toString("yyyy-MM"));
				dstartDate = dstartDate.plusMonths(1);
			}
		} else {
			int base = ((Double) Math.ceil(months / 55.0)).intValue();
			for (int i = 0; i < (months / base) + 1; i++) {
				labelsList.add(dstartDate.toString("yyyy-MM"));
				dstartDate = dstartDate.plusMonths(base);
			}
		}

		List<Integer> flowList = nlcStatisticService.monQdcsqs(startDate, endDate);

		String result = "{flow:" + flowList + ", labels:" + labelsList + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 日访问量分布按月统计 去重的 用户数量
	 */
	@RequestMapping("/monyhslqs")
	@ResponseBody
	public JSONObject monyhslqs(Date startDate, Date endDate) {
		List<Integer> flowList = nlcStatisticService.monyhslqs(startDate, endDate);

		String result = "{flow:" + flowList + "}";
		return JSONObject.fromObject(result);
	}

	// *****==按年统计

	/**
	 * 启动次数趋势 按年统计 不去重的，启动次数
	 */
	@RequestMapping("/nqdcsqs")
	@ResponseBody
	public JSONObject nqdcsqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int years = Years.yearsBetween(dstartDate, dendDate).getYears();

		List<String> labelsList = new ArrayList<String>();
		for (int i = 0; i < years + 1; i++) {
			labelsList.add(dstartDate.toString("yyyy"));
			dstartDate = dstartDate.plusYears(1);
		}

		List<Integer> flowList = nlcStatisticService.nQdcsqs(startDate, endDate);

		String result = "{flow:" + flowList + ", labels:" + labelsList + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 日访问量分布按年统计 去重的 用户数量
	 */
	@RequestMapping("/nyhslqs")
	@ResponseBody
	public JSONObject nyhslqs(Date startDate, Date endDate) {
		List<Integer> flowList = nlcStatisticService.nyhslqs(startDate, endDate);

		String result = "{flow:" + flowList + "}";
		return JSONObject.fromObject(result);
	}

	// *************按周

	public static void main(String[] args) {
		DateTime x = new DateTime("2017-1-30");
		DateTime s = x.plusMonths(1);
		System.out.println(s);
		DateTime d = new DateTime("2016-10-26");
		/*
		 * int s = d.getDayOfWeek(); int ss = d.getWeekOfWeekyear();
		 * System.out.println(ss); int weeks = Weeks.weeksBetween(x,
		 * d).getWeeks(); System.out.println(weeks);
		 */

		// System.out.println(s);
		// System.out.println(ss);
		// DateTime m = d.minusDays(s).plusDays(7); //minusdays s 得到的是周日 12月27号；
		// 再plusdays7 得到的是下个周日 1月3号
		// System.out.println(m.toString());
		/*
		 * DateTime mweek = d.minusDays(s - 1);
		 * 
		 * // System.out.println(mweek.toString()); //12月28号.. 本周的周一
		 * System.out.println(mweek.plusDays(6).toString()); // 1月3号，本周的周日
		 * System.out.println(mweek.plusWeeks(1).toString()); // 下周的周一
		 */ }

	/**
	 * 启动次数趋势 按周统计 不去重的，启动次数
	 */
	@RequestMapping("/zqdcsqs")
	@ResponseBody
	public JSONObject zqdcsqs(Date startDate, Date endDate) {
		DateTime ds = new DateTime(startDate);
		DateTime de = new DateTime(endDate);
		
		int minus = ds.getDayOfWeek() - 1;
		DateTime m = ds.minusDays(minus); // 得到刚刚好的本周的周一
		int p = de.getDayOfWeek();
		DateTime e = de.plusDays(7 - p);
		int weeks = Weeks.weeksBetween(m, e).getWeeks();

		List<String> labelsList = new ArrayList<String>();
		if (weeks <= 55) {
			for (int i = 0; i < weeks + 1; i++) {
				String str = m.toString("yyyy") + "第" + m.getWeekOfWeekyear() + "周  ";
				labelsList.add("\"" + str + "\"");
				m = m.plusWeeks(1);
			}
		} else {
			int base = ((Double) Math.ceil(weeks / 55.0)).intValue();
			for (int i = 0; i < (weeks / base) + 1; i++) {
				String str = m.toString("yyyy") + "第" + m.getWeekOfWeekyear() + "周  ";
				labelsList.add("\"" + str + "\"");
				m = m.plusWeeks(base);
			}
		}
		
		List<Integer> flowList = nlcStatisticService.zQdcsqs(startDate, endDate);

		String result = "{flow:" + flowList + ", labels:" + labelsList + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 日访问量分布按周统计 去重的 用户数量
	 */
	@RequestMapping("/zyhslqs")
	@ResponseBody
	public JSONObject zyhslqs(Date startDate, Date endDate) {
		List<Integer> flowList = nlcStatisticService.zyhslqs(startDate, endDate);

		String result = "{flow:" + flowList + "}";
		return JSONObject.fromObject(result);
	}

	// ============================日访问量分布end===========================

	/**
	 * 日访问量分布 去重的 用户数量
	 */
	@RequestMapping("/rfwlll")
	@ResponseBody
	public JSONObject rfwlllList(Date startDate, Date endDate) {
		List<Integer> flowList = nlcStatisticService.rfwlllList(startDate, endDate);

		String result = "{flow:" + flowList + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 启动次数趋势 不去重的，启动次数
	 */
	@RequestMapping("/qdcsqs")
	@ResponseBody
	public JSONObject qdcsqs(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int days = Days.daysBetween(dstartDate, dendDate).getDays();

		List<String> labelsList = new ArrayList<String>();
		if (days <= 55) {
			for (int i = 0; i < days + 1; i++) {
				labelsList.add(dstartDate.toString("yy-MM-dd"));
				dstartDate = dstartDate.plusDays(1);
			}
		} else {
			int base = ((Double) Math.ceil(days / 55.0)).intValue();
			for (int i = 0; i < (days / base) + 1; i++) {
				labelsList.add(dstartDate.toString("yy-MM-dd"));
				dstartDate = dstartDate.plusDays(base);
			}
		}

		List<Integer> flowList = nlcStatisticService.qdcsqs(startDate, endDate);

		String result = "{flow:" + flowList + ", labels:" + labelsList + "}";
		return JSONObject.fromObject(result);
	}

//	/**
//	 * 跳转到用户分析下的 新增用户页面
//	 */
//	@RequestMapping("/apptj")
//	public String apptjshow(Model model, HttpSession session) {
//
//		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
//		Date loginTime = (Date) session.getAttribute("loginTime");
//		String loginIp = (String) session.getAttribute("loginIp");
//
//		Nlcadminlog nlcadminlog = new Nlcadminlog();
//		nlcadminlog.setUsername(dbNlcadmin.getUsername());
//		nlcadminlog.setRole(dbNlcadmin.getRole());
//		nlcadminlog.setIp(loginIp);
//		nlcadminlog.setTime(loginTime);
//		nlcadminlog.setOperate("查看APP统计");
//		nlcadminlogService.insertNlcadminlog(nlcadminlog);
//
//		DateTime now = new DateTime();
//		DateTime startDate = new DateTime(now.minusMonths(3).toString("yyyy-MM-01"));
//		DateTime endDate = new DateTime(now.minusDays(1).toString("yyyy-MM-01"));
//
//		int startDateweeks = startDate.getWeekOfWeekyear();
//		int endDateweeks = endDate.getWeekOfWeekyear();
//
//		// 昨天 + 周数
//		model.addAttribute("startDateweeks", startDate.toString("yyyy-MM-dd") + " " + startDateweeks);
//		// 上个月 + 周数
//		model.addAttribute("endDateweeks", endDate.toString("yyyy-MM-dd") + " " + endDateweeks);
//
//		// 日
//		model.addAttribute("startDate", startDate);
//		model.addAttribute("endDate", endDate);
//		return "statistic/zdsx/apptj";
//	}

//	/**
//	 * add by jjj
//	 * 
//	 * @param startDate
//	 * @param endDate
//	 * @param type
//	 * @param flag 下载量 更新量 使用时长
//	 * @return
//	 */
//	@RequestMapping("/apptjLine")
//	@ResponseBody
//	public JSONObject apptjLine(Date startDate, Date endDate, String type, String flag) {
//		DateTime startDateJoda = new DateTime(startDate);
//		DateTime endDateJoda = new DateTime(endDate);
//		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {// 起始时间必须小于等于结束时间
//			String res = "{result:false}";
//			return JSONObject.fromObject(res);
//		}
//
//		List<List<?>> List = nlcStatisticService.apptjLine(startDate, endDate, type, flag);
//		if (List == null) {
//			return null;
//		}
//		String result = "{result:true,labels:" + List.get(0) + ", flow:" + List.get(1) + ", flow2:" + List.get(2) + "}";
//		return JSONObject.fromObject(result);
//	}
//	/**
//	 * APP统计Iphone下载量的折线图数据
//	 * 
//	 * @return
//	 */
	// @RequestMapping("/apptjiphonexz")
	// @ResponseBody
	// public JSONObject apptjiphonexzList(Date startDate, Date endDate) {
	// List<String> labelsList = new ArrayList<String>();
	//
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// String res = "{result:false}";
	// return JSONObject.fromObject(res);
	// }
	//
	// while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// labelsList.add(startDateJoda.toString("yyyy-MM"));
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// List<Integer> flowList = nlcStatisticService.apptjiphonexzList(startDate,
	// endDate);
	//
	// String result = "{result:true,flow:" + flowList + ", labels:" +
	// labelsList + "}";
	// return JSONObject.fromObject(result);
	// }

//	/**
//	 * APP统计android下载量的折线图数据
//	 * 
//	 * @return
//	 */
	// @RequestMapping("/apptjandroidxz")
	// @ResponseBody
	// public JSONObject apptjandroidxzList(Date startDate, Date endDate) {
	// List<Integer> flowList =
	// nlcStatisticService.apptjandroidxzList(startDate, endDate);
	// String result = "{flow:" + flowList + "}";
	// return JSONObject.fromObject(result);
	// }

//	/**
//	 * update by jjj 跳转到终端属性/APP统计/列表
//	 */
//	@RequestMapping("/table")
//	public String xztableShow(@RequestParam(defaultValue = "1") Integer page,
//			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String type, Model model) {
//		model.addAttribute("startDate", startDate);
//		model.addAttribute("endDate", endDate);
//		model.addAttribute("type", type);
//		return "statistic/zdsx/xztable";
//		// return resultList;
//	}

//	/**
//	 * add by JJJ
//	 * 
//	 * @param page
//	 * @param rows
//	 * @param startDate
//	 * @param endDate
//	 * @param type
//	 * @param flag
//	 *            下载量 更新量 使用时长
//	 * @return
//	 */
//	@RequestMapping("/tableList")
//	@ResponseBody
//	public EasyUiDataGridResult tableList(@RequestParam(defaultValue = "1") Integer page,
//			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String type, String flag,
//			Model model) {
//		EasyUiDataGridResult resultList = nlcStatisticService.tableData(page, rows, startDate, endDate, type, flag,
//				false);
//		model.addAttribute("resultList", resultList);
//		return resultList;
//	}
//	/**
//	 * add by jjj app下载量数据
//	 * 
//	 * @param page
//	 * @param rows
//	 * @param startDate
//	 * @param endDate
//	 * @param type
//	 * @param model
//	 * @return
//	 */
	// @RequestMapping("/xztableList")
	// @ResponseBody
	// public EasyUiDataGridResult xztableList(@RequestParam(defaultValue = "1")
	// Integer page,
	// @RequestParam(defaultValue = "10") Integer rows,Date startDate, Date
	// endDate,String type, Model model) {
	// EasyUiDataGridResult resultList = nlcStatisticService.xztableData(page,
	// rows,startDate, endDate,type,false);
	// //Collections.reverse(resultList);
	// model.addAttribute("resultList", resultList);
	// return resultList;
	// }

//	/**
//	 * APP统计Iphone更新量的折线图数据
//	 * 
//	 * @return
//	 */
	// @RequestMapping("/apptjiphonegx")
	// @ResponseBody
	// public JSONObject apptjiphonegxList(Date startDate, Date endDate) {
	// List<String> labelsList = new ArrayList<String>();
	//
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// String res = "{result:false}";
	// return JSONObject.fromObject(res);
	// }
	//
	// while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// labelsList.add(startDateJoda.toString("yyyy-MM"));
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// List<Integer> flowList = nlcStatisticService.apptjiphonegxList(startDate,
	// endDate);
	//
	// String result = "{result:true,flow:" + flowList + ", labels:" +
	// labelsList + "}";
	// return JSONObject.fromObject(result);
	// }

//	/**
//	 * APP统计android更新量的折线图数据
//	 * 
//	 * @return
//	 */
	// @RequestMapping("/apptjandroidgx")
	// @ResponseBody
	// public JSONObject apptjandroidgxList(Date startDate, Date endDate) {
	// List<Integer> flowList =
	// nlcStatisticService.apptjandroidgxList(startDate, endDate);
	// String result = "{flow:" + flowList + "}";
	// return JSONObject.fromObject(result);
	// }

//	/**
//	 * update by jjj 跳转到终端属性/APP统计/更新量的列表
//	 */
	// @RequestMapping("/gxtable")
	// public String gxtableShow(@RequestParam(defaultValue = "1") Integer page,
	// @RequestParam(defaultValue = "10") Integer rows,Date startDate, Date
	// endDate,String type, Model model) {
	// model.addAttribute("startDate", startDate);
	// model.addAttribute("endDate", endDate);
	// model.addAttribute("type", type);
	// return "statistic/zdsxj/xztable";
	// }
//	/**
//	 * add by jjj app更新量数据
//	 * 
//	 * @param page
//	 * @param rows
//	 * @param startDate
//	 * @param endDate
//	 * @param type
//	 *            ： year month day week
//	 * @param model
//	 * @return
//	 */
	// @RequestMapping("/gxtableList")
	// @ResponseBody
	// public EasyUiDataGridResult gxtableList(@RequestParam(defaultValue = "1")
	// Integer page,
	// @RequestParam(defaultValue = "10") Integer rows,Date startDate, Date
	// endDate,String type, Model model) {
	// EasyUiDataGridResult resultList = nlcStatisticService.gxtableData(page,
	// rows,startDate, endDate,type,false);
	// model.addAttribute("resultList", resultList);
	// return resultList;
	// }
//	/**
//	 * add by jjj
//	 * 
//	 * @param startDate
//	 * @param endDate
//	 * @param type
//	 * @return
//	 */
	// @RequestMapping("/apptjgx")
	// @ResponseBody
	// public JSONObject apptjgxList(Date startDate, Date endDate,String type) {
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// String res = "{result:false}";
	// return JSONObject.fromObject(res);
	// }
	//
	// List<List<?>> List = nlcStatisticService.apptjgxList(startDate,
	// endDate,type);
	// if(List==null){
	// return null;
	// }
	// String result = "{result:true,labels:" + List.get(0) + ", flow:" +
	// List.get(1) + ", flow2:" + List.get(2) + "}";
	// return JSONObject.fromObject(result);
	// }
//	/**
//	 * update by jjj app统计的excel导出
//	 * 
//	 * @param startDate
//	 * @param endDate
//	 * @param type
//	 *            : year month day week
//	 * @param flag
//	 *            : 下载量 更新量 使用时长
//	 */
//	@RequestMapping("/apptjExport")
//	public void apptjExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
//			String type, String flag) {
//		EasyUiDataGridResult eugr = nlcStatisticService.tableData(null, null, startDate, endDate, type, flag, true);
//		if (flag.equals("1")) {
//			flag = "下载量";
//		} else if (flag.equals("2")) {
//			flag = "更新量";
//		} else if (flag.equals("3")) {
//			flag = "使用时长";
//		}
//		List<ApptjPo> list = (List<ApptjPo>) eugr.getRows();
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet(flag);
//		HSSFRow headRow = sheet.createRow(0);
//		headRow.createCell(0).setCellValue("时间");
//		headRow.createCell(1).setCellValue("IOS");
//		headRow.createCell(2).setCellValue("Android");
//
//		if (null != list && list.size() > 0) {
//			for (ApptjPo po : list) {
//				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
//				dataRow.createCell(0).setCellValue(po.getMonthdate());
//				dataRow.createCell(1).setCellValue(po.getIosnum());
//				dataRow.createCell(2).setCellValue(po.getAndroidnum());
//
//			}
//		}
//		String filename = flag + "-" + type + ".xls";
//		String agent = request.getHeader("User-Agent");
//		try {
//			filename = FileUtils.encodeDownloadFilename(filename, agent);
//			ServletOutputStream out = response.getOutputStream();
//			String mimeType = request.getServletContext().getMimeType(filename);
//			response.setContentType(mimeType);
//			response.setHeader("content-disposition", "attachment;filename=" + filename);
//			workbook.write(out);
//		} catch (IOException e) {
//			logger.error(e.getMessage());
//		}
//	}
//
//	/**
//	 * app统计更新量的excel导出
//	 */
	// @RequestMapping("/apptjgxExport")
	// public void apptjgxExport(HttpServletRequest request, HttpServletResponse
	// response, Date startDate, Date endDate) {
	// List<ApptjPo> list = nlcStatisticService.gxtableData(startDate, endDate);
	// HSSFWorkbook workbook = new HSSFWorkbook();
	// HSSFSheet sheet = workbook.createSheet("更新量");
	// HSSFRow headRow = sheet.createRow(0);
	// headRow.createCell(0).setCellValue("月份");
	// headRow.createCell(1).setCellValue("IOS");
	// headRow.createCell(2).setCellValue("Android");
	//
	// if (null != list && list.size() > 0) {
	// for (ApptjPo po : list) {
	// HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
	// dataRow.createCell(0).setCellValue(po.getMonthdate());
	// dataRow.createCell(1).setCellValue(po.getIosnum());
	// dataRow.createCell(2).setCellValue(po.getAndroidnum());
	//
	// }
	// }
	//
	// String filename = "更新量.xls";
	// String agent = request.getHeader("User-Agent");
	// try {
	// filename = FileUtils.encodeDownloadFilename(filename, agent);
	// ServletOutputStream out = response.getOutputStream();
	// String mimeType = request.getServletContext().getMimeType(filename);
	// response.setContentType(mimeType);
	// response.setHeader("content-disposition", "attachment;filename=" +
	// filename);
	// workbook.write(out);
	// } catch (IOException e) {
	// logger.error(e.getMessage());
	// }
	// }

//	/**
//	 * APP统计Iphone使用时长的折线图数据
//	 * 
//	 * @return
//	 */
	// @RequestMapping("/apptjiphonesc")
	// @ResponseBody
	// public JSONObject apptjiphonescList(Date startDate, Date endDate) {
	// List<String> labelsList = new ArrayList<String>();
	//
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// String res = "{result:false}";
	// return JSONObject.fromObject(res);
	// }
	//
	// while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
	// labelsList.add(startDateJoda.toString("yyyy-MM"));
	// startDateJoda = startDateJoda.plusMonths(1);
	// }
	//
	// List<Integer> flowList = nlcStatisticService.apptjiphonescList(startDate,
	// endDate);
	//
	// String result = "{result:true,flow:" + flowList + ", labels:" +
	// labelsList + "}";
	// return JSONObject.fromObject(result);
	// }

//	/**
//	 * APP统计android更新量的折线图数据
//	 * 
//	 * @return
//	 */
	// @RequestMapping("/apptjandroidsc")
	// @ResponseBody
	// public JSONObject apptjandroidscList(Date startDate, Date endDate) {
	// List<Integer> flowList =
	// nlcStatisticService.apptjandroidscList(startDate, endDate);
	// String result = "{flow:" + flowList + "}";
	// return JSONObject.fromObject(result);
	// }

//	/**
//	 * 跳转到终端属性/APP统计/更新量的列表
//	 */
	// @RequestMapping("/sctable")
	// public String sctableShow(Date startDate, Date endDate, Model model) {
	// List<ApptjPo> resultList = nlcStatisticService.sctableData(startDate,
	// endDate);
	// Collections.reverse(resultList);
	// model.addAttribute("resultList", resultList);
	// return "statistic/zdsx/sctable";
	// }

//	/**
//	 * app统计使用时长的excel导出
//	 */
	// @RequestMapping("/apptjscExport")
	// public void apptjscExport(HttpServletRequest request, HttpServletResponse
	// response, Date startDate, Date endDate) {
	// List<ApptjPo> list = nlcStatisticService.sctableData(startDate, endDate);
	// HSSFWorkbook workbook = new HSSFWorkbook();
	// HSSFSheet sheet = workbook.createSheet("使用时长");
	// HSSFRow headRow = sheet.createRow(0);
	// headRow.createCell(0).setCellValue("月份");
	// headRow.createCell(1).setCellValue("IOS");
	// headRow.createCell(2).setCellValue("Android");
	//
	// if (null != list && list.size() > 0) {
	// for (ApptjPo po : list) {
	// HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
	// dataRow.createCell(0).setCellValue(po.getMonthdate());
	// dataRow.createCell(1).setCellValue(po.getIosnum());
	// dataRow.createCell(2).setCellValue(po.getAndroidnum());
	//
	// }
	// }
	//
	// String filename = "使用时长.xls";
	// String agent = request.getHeader("User-Agent");
	// try {
	// filename = FileUtils.encodeDownloadFilename(filename, agent);
	// ServletOutputStream out = response.getOutputStream();
	// String mimeType = request.getServletContext().getMimeType(filename);
	// response.setContentType(mimeType);
	// response.setHeader("content-disposition", "attachment;filename=" +
	// filename);
	// workbook.write(out);
	// } catch (IOException e) {
	// logger.error(e.getMessage());
	// }
	// }

	/**
	 * 跳转到资源画像下的资源画像页面
	 */
	@RequestMapping("/zyhx")
	public String zyhxshow(Model model, HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看资源画像");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);

		DateTime now = new DateTime();
		DateTime startDate = new DateTime(now.minusMonths(3).toString("yyyy-MM-01"));
		DateTime endDate = new DateTime(now.minusDays(1).toString("yyyy-MM-01"));

		int startDateweeks = startDate.getWeekOfWeekyear();
		int endDateweeks = endDate.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("startDateweeks", startDate.toString("yyyy-MM-dd") + " " + startDateweeks);
		// 上个月 + 周数
		model.addAttribute("endDateweeks", endDate.toString("yyyy-MM-dd") + " " + endDateweeks);

		// 日
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "statistic/zyhxx/zyhx";
	}

	/**
	 * 资源画像期刊浏览量
	 * update by JJJ 20170223 pm
	 * @param type 年 月 周 日
	 * @param zytype  期刊 听书 问津阅读 特色专题 总访问量
	 * @param flag : lll 浏览量 ，xzl 下载量，scl 收藏量
	 * @return
	 */
	@RequestMapping("/zyhxDataList")
	@ResponseBody
	public ResultVo zyhxDataList(Date startDate, Date endDate, String type, String zytype, String flag) {
		ResultVo res = new ResultVo();
		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {// 起始时间必须小于等于结束时间
			res.setResult(false);
			return res;
		}
		List<ZyhxPo> flowList = null;
		if ("4".equals(zytype)) {// 总访问量
			flowList = nlcStatisticService.zyhxzfwlList(startDate, endDate, type, flag);
		} else {
			flowList = nlcStatisticService.zyhxDataList(startDate, endDate, type, zytype, flag);
		}
		res.setResult(true);
		res.setList(flowList);
		return res;
	}

	// /**
	// * 资源画像期刊下载量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxqkxz")
	// @ResponseBody
	// public ResultVo zyhxqkxzList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxqkxzList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像期刊收藏量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxqksc")
	// @ResponseBody
	// public ResultVo zyhxqkscList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxqkscList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像听书浏览量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxtsll")
	// @ResponseBody
	// public ResultVo zyhxtsllList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxtsllList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像听书下载量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxtsxz")
	// @ResponseBody
	// public ResultVo zyhxtsxzList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxtsxzList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像听书收藏量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxtssc")
	// @ResponseBody
	// public ResultVo zyhxtsscList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxtsscList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像文津诵读浏览量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxwjll")
	// @ResponseBody
	// public ResultVo zyhxwjllList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxwjllList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像特色专题浏览量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxztll")
	// @ResponseBody
	// public ResultVo zyhxztllList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxztllList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像特色专题收藏量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxztsc")
	// @ResponseBody
	// public ResultVo zyhxztscList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxztscList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像总访问量浏览量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxzll")
	// @ResponseBody
	// public ResultVo zyhxzllList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxzllList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像总访问量下载量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxzxz")
	// @ResponseBody
	// public ResultVo zyhxzxzList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxzxzList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	// /**
	// * 资源画像总访问量收藏量
	// *
	// * @return
	// */
	// @RequestMapping("/zyhxzsc")
	// @ResponseBody
	// public ResultVo zyhxzscList(Date startDate, Date endDate) {
	// ResultVo res = new ResultVo();
	// DateTime startDateJoda = new DateTime(startDate);
	// DateTime endDateJoda = new DateTime(endDate);
	// if (startDateJoda.getMillis() > endDateJoda.getMillis()) {//
	// 起始时间必须小于等于结束时间
	// res.setResult(false);
	// return res;
	// }
	//
	// List<ZyhxPo> flowList = nlcStatisticService.zyhxzscList(startDate,
	// endDate);
	// res.setResult(true);
	// res.setList(flowList);
	// return res;
	// }

	/**
	 * 跳转到资源画像/资源画像中的访问数据的列表 update by JJJ 20170223 pm
	 */
	@RequestMapping("/zyhxtable")
	public String zyhxtable(Model model, Date startDate, Date endDate, String type, String zytype) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("type", type);
		model.addAttribute("zytype", zytype);
		return "statistic/zyhxx/zyhxtable";
	}

	/**
	 * 跳转到资源画像/资源画像中的访问数据的列表数据  
	 * add by JJJ20170223 pm
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/zyhxtableList")
	@ResponseBody
	public EasyUiDataGridResult zyhxtableList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String type, String zytype) {

		EasyUiDataGridResult result = null;
		if ("4".equals(zytype)) {// 总访问量
			result = nlcStatisticService.zyhxzfwltableData(page, rows, startDate, endDate, type, false);
		} else {
			result = nlcStatisticService.zyhxtableData(page, rows, startDate, endDate, type, zytype, false);
		}
		return result;
	}
	// /**
	// * 跳转到资源画像/资源画像中的听书的访问数据的列表
	// */
	// @RequestMapping("/zyhxtstable")
	// public String zyhxtstableShow(Date startDate, Date endDate, Model model)
	// {
	// List<ZyhxTablePo> resultList =
	// nlcStatisticService.zyhxtstableData(startDate, endDate);
	// Collections.reverse(resultList);
	// model.addAttribute("resultList", resultList);
	// return "statistic/zyhxx/zyhxtable";
	// }

	// /**
	// * 跳转到资源画像/资源画像中的文津诵读的访问数据的列表
	// */
	// @RequestMapping("/zyhxwjtable")
	// public String zyhxwjtableShow(Date startDate, Date endDate, Model model)
	// {
	// List<ZyhxTablePo> resultList =
	// nlcStatisticService.zyhxwjtableData(startDate, endDate);
	// Collections.reverse(resultList);
	// model.addAttribute("resultList", resultList);
	// return "statistic/zyhxx/zyhxtable";
	// }

	/**
	 * 跳转到资源画像/资源画像中的特色专题的访问数据的列表
	 */
	// @RequestMapping("/zyhxzttable")
	// public String zyhxzttableShow(Date startDate, Date endDate, Model model)
	// {
	// List<ZyhxTablePo> resultList =
	// nlcStatisticService.zyhxzttableData(startDate, endDate);
	// Collections.reverse(resultList);
	// model.addAttribute("resultList", resultList);
	// return "statistic/zyhxx/zyhxtable";
	// }

	// /**
	// * 跳转到资源画像/资源画像中的总访问量数据的列表
	// */
	// @RequestMapping("/zyhxzfltable")
	// public String zyhxzfltableShow(Date startDate, Date endDate, Model model)
	// {
	// List<ZyhxTablePo> resultList =
	// nlcStatisticService.zyhxzfltableData(startDate, endDate);
	// Collections.reverse(resultList);
	// model.addAttribute("resultList", resultList);
	// return "statistic/zyhxx/zyhxtable";
	// }

	/**
	 * 资源画像期刊访问数据的excel导出 
	 * update by JJJ 20170223 pm
	 */
	@RequestMapping("/zyhxExport")
	public void zyhxExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String type, String zytype) {
		String name = "";

		EasyUiDataGridResult eugr = null;

		if ("4".equals(zytype)) {
			name = "总访问数据";
			eugr = nlcStatisticService.zyhxzfwltableData(null, null, startDate, endDate, type, true);
		} else {
			if ("0".equals(zytype)) {// 期刊
				name = "期刊访问数据";
			} else if ("1".equals(zytype)) {// 听书
				name = "听书访问数据";
			} else if ("2".equals(zytype)) {
				name = "文津诵读访问数据";
			} else if ("3".equals(zytype)) {
				name = "特色专题访问数据";
			} else {
				return;
			}
			eugr = nlcStatisticService.zyhxtableData(null, null, startDate, endDate, type, zytype, true);
		}
		if (eugr != null) {
			List<ZyhxTablePo> list = (List<ZyhxTablePo>) eugr.getRows();
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(name);// "听书访问数据"
			HSSFRow headRow = sheet.createRow(0);
			headRow.createCell(0).setCellValue("时间");
			headRow.createCell(1).setCellValue("浏览量");
			if (!"2".equals(zytype)&&!"3".equals(zytype)) {
				headRow.createCell(2).setCellValue("下载量");
			}
			if (!"2".equals(zytype)) {
				headRow.createCell(3).setCellValue("收藏量");
			} 

			if (null != list && list.size() > 0) {
				for (ZyhxTablePo po : list) {
					HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
					dataRow.createCell(0).setCellValue(po.getDate());
					dataRow.createCell(1).setCellValue(po.getLlnum());
					if (!"2".equals(zytype)&&!"3".equals(zytype)) {
						dataRow.createCell(2).setCellValue(po.getXznum());
					}
					if (!"2".equals(zytype)) {
						dataRow.createCell(3).setCellValue(po.getScnum());
					} 
				}
			}

			String filename = name + ".xls";
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

	// /**
	// * 资源画像听书访问数据的excel导出
	// */
	// @RequestMapping("/zyhxtsExport")
	// public void zyhxtsExport(HttpServletRequest request, HttpServletResponse
	// response, Date startDate, Date endDate) {
	// List<ZyhxTablePo> list = nlcStatisticService.zyhxtstableData(startDate,
	// endDate);
	// HSSFWorkbook workbook = new HSSFWorkbook();
	// HSSFSheet sheet = workbook.createSheet("听书访问数据");
	// HSSFRow headRow = sheet.createRow(0);
	// headRow.createCell(0).setCellValue("月份");
	// headRow.createCell(1).setCellValue("浏览量");
	// headRow.createCell(2).setCellValue("下载量");
	// headRow.createCell(3).setCellValue("收藏量");
	//
	// if (null != list && list.size() > 0) {
	// for (ZyhxTablePo po : list) {
	// HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
	// dataRow.createCell(0).setCellValue(po.getDate());
	// dataRow.createCell(1).setCellValue(po.getLlnum());
	// dataRow.createCell(2).setCellValue(po.getXznum());
	// dataRow.createCell(3).setCellValue(po.getScnum());
	// }
	// }
	//
	// String filename = "听书访问数据.xls";
	// String agent = request.getHeader("User-Agent");
	// try {
	// filename = FileUtils.encodeDownloadFilename(filename, agent);
	// ServletOutputStream out = response.getOutputStream();
	// String mimeType = request.getServletContext().getMimeType(filename);
	// response.setContentType(mimeType);
	// response.setHeader("content-disposition", "attachment;filename=" +
	// filename);
	// workbook.write(out);
	// } catch (IOException e) {
	// logger.error(e.getMessage());
	// }
	// }

	// /**
	// * 资源画像文津诵读访问数据的excel导出
	// */
	// @RequestMapping("/zyhxwjExport")
	// public void zyhxwjExport(HttpServletRequest request, HttpServletResponse
	// response, Date startDate, Date endDate) {
	// List<ZyhxTablePo> list = nlcStatisticService.zyhxwjtableData(startDate,
	// endDate);
	// HSSFWorkbook workbook = new HSSFWorkbook();
	// HSSFSheet sheet = workbook.createSheet("文津诵读访问数据");
	// HSSFRow headRow = sheet.createRow(0);
	// headRow.createCell(0).setCellValue("月份");
	// headRow.createCell(1).setCellValue("浏览量");
	// headRow.createCell(2).setCellValue("下载量");
	// headRow.createCell(3).setCellValue("收藏量");
	//
	// if (null != list && list.size() > 0) {
	// for (ZyhxTablePo po : list) {
	// HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
	// dataRow.createCell(0).setCellValue(po.getDate());
	// dataRow.createCell(1).setCellValue(po.getLlnum());
	// dataRow.createCell(2).setCellValue(po.getXznum());
	// dataRow.createCell(3).setCellValue(po.getScnum());
	// }
	// }
	//
	// String filename = "文津诵读访问数据.xls";
	// String agent = request.getHeader("User-Agent");
	// try {
	// filename = FileUtils.encodeDownloadFilename(filename, agent);
	// ServletOutputStream out = response.getOutputStream();
	// String mimeType = request.getServletContext().getMimeType(filename);
	// response.setContentType(mimeType);
	// response.setHeader("content-disposition", "attachment;filename=" +
	// filename);
	// workbook.write(out);
	// } catch (IOException e) {
	// logger.error(e.getMessage());
	// }
	// }

	// /**
	// * 资源画像特色专题访问数据的excel导出
	// */
	// @RequestMapping("/zyhxztExport")
	// public void zyhxztExport(HttpServletRequest request, HttpServletResponse
	// response, Date startDate, Date endDate) {
	// List<ZyhxTablePo> list = nlcStatisticService.zyhxzttableData(startDate,
	// endDate);
	// HSSFWorkbook workbook = new HSSFWorkbook();
	// HSSFSheet sheet = workbook.createSheet("特色专题访问数据");
	// HSSFRow headRow = sheet.createRow(0);
	// headRow.createCell(0).setCellValue("月份");
	// headRow.createCell(1).setCellValue("浏览量");
	// headRow.createCell(2).setCellValue("下载量");
	// headRow.createCell(3).setCellValue("收藏量");
	//
	// if (null != list && list.size() > 0) {
	// for (ZyhxTablePo po : list) {
	// HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
	// dataRow.createCell(0).setCellValue(po.getDate());
	// dataRow.createCell(1).setCellValue(po.getLlnum());
	// dataRow.createCell(2).setCellValue(po.getXznum());
	// dataRow.createCell(3).setCellValue(po.getScnum());
	// }
	// }
	//
	// String filename = "特色专题访问数据.xls";
	// String agent = request.getHeader("User-Agent");
	// try {
	// filename = FileUtils.encodeDownloadFilename(filename, agent);
	// ServletOutputStream out = response.getOutputStream();
	// String mimeType = request.getServletContext().getMimeType(filename);
	// response.setContentType(mimeType);
	// response.setHeader("content-disposition", "attachment;filename=" +
	// filename);
	// workbook.write(out);
	// } catch (IOException e) {
	// logger.error(e.getMessage());
	// }
	// }

	// /**
	// * 资源画像总访问数据的excel导出
	// */
	// @RequestMapping("/zyhxzflExport")
	// public void zyhxzflExport(HttpServletRequest request, HttpServletResponse
	// response, Date startDate, Date endDate) {
	// List<ZyhxTablePo> list = nlcStatisticService.zyhxzfltableData(startDate,
	// endDate);
	// HSSFWorkbook workbook = new HSSFWorkbook();
	// HSSFSheet sheet = workbook.createSheet("总访问数据");
	// HSSFRow headRow = sheet.createRow(0);
	// headRow.createCell(0).setCellValue("月份");
	// headRow.createCell(1).setCellValue("浏览量");
	// headRow.createCell(2).setCellValue("下载量");
	// headRow.createCell(3).setCellValue("收藏量");
	//
	// if (null != list && list.size() > 0) {
	// for (ZyhxTablePo po : list) {
	// HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
	// dataRow.createCell(0).setCellValue(po.getDate());
	// dataRow.createCell(1).setCellValue(po.getLlnum());
	// dataRow.createCell(2).setCellValue(po.getXznum());
	// dataRow.createCell(3).setCellValue(po.getScnum());
	// }
	// }
	//
	// String filename = "总访问数据.xls";
	// String agent = request.getHeader("User-Agent");
	// try {
	// filename = FileUtils.encodeDownloadFilename(filename, agent);
	// ServletOutputStream out = response.getOutputStream();
	// String mimeType = request.getServletContext().getMimeType(filename);
	// response.setContentType(mimeType);
	// response.setHeader("content-disposition", "attachment;filename=" +
	// filename);
	// workbook.write(out);
	// } catch (IOException e) {
	// logger.error(e.getMessage());
	// }
	// }

	/**
	 * 跳转到各资源画像页面
	 * 
	 * @return
	 */
	@RequestMapping("/gzyhxshow")
	public String gzyhxshow(Model model,HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看各资源画像页面");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		
		DateTime now = new DateTime();
		DateTime yesterday = now.minusDays(1);
		DateTime formermonth = now.minusMonths(3);
		model.addAttribute("yesterday", yesterday.toString("yyyy-MM-dd"));
		model.addAttribute("formermonth", formermonth.toString("yyyy-MM-dd"));
		
		return "statistic/zyhxx/gzyhx";
	}

	/**
	 * 各资源画像的list数据
	 * update by JJJ 20170224 pm
	 * @param type			资源类别 ：期刊、听书
	 * @param magazineid	资源ID
	 * @param title			名称
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/gzyhxList")
	@ResponseBody
	public EasyUiDataGridResult gzyhxList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows,String sort, String order, String type, String magazineid,
			String title,Date startDate,Date endDate) {
		if(startDate==null||endDate==null||startDate.getTime()>endDate.getTime()){
			return null;
		}
		EasyUiDataGridResult result = nlcStatisticService.gzyhxList(page, rows,sort,order, type, magazineid, title,startDate,endDate,false);
		return result;
	}
	/**
	 * 各资源画像导出 add by JJJ
	 */
	@RequestMapping("/gzyhxExport")
	@ResponseBody
	public void gzyhxExport(HttpServletRequest request, HttpServletResponse response, String type, String magazineid,String title,Date startDate,Date endDate) {
		List<GzyhxPo> list = null;
		if(startDate!=null&&endDate!=null&&startDate.getTime()<=endDate.getTime()){
			EasyUiDataGridResult result = nlcStatisticService.gzyhxList(null, null,null,null, type, magazineid, title,startDate,endDate,true);
			if(result!=null){
				list = (List<GzyhxPo>) result.getRows();
			}
		}
		 HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet sheet = workbook.createSheet("各资源画像");
		 HSSFRow headRow = sheet.createRow(0);
		 headRow.createCell(0).setCellValue("资源类别");
		 headRow.createCell(1).setCellValue("资源ID");
		 headRow.createCell(2).setCellValue("名称");
		 headRow.createCell(3).setCellValue("浏览量");
		 headRow.createCell(4).setCellValue("收藏量");
		 headRow.createCell(5).setCellValue("下载量");
	
		 if (null != list && list.size() > 0) {
			 for (GzyhxPo po : list) {
			 HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			 dataRow.createCell(0).setCellValue(po.getType().equals("r")?"期刊":"听书");
			 dataRow.createCell(1).setCellValue(po.getMagazineid());
			 dataRow.createCell(2).setCellValue(po.getTitle());
			 dataRow.createCell(3).setCellValue(po.getBrowse());
			 dataRow.createCell(4).setCellValue(po.getCollect());
			 dataRow.createCell(5).setCellValue(po.getDown());
			 }
		 }
		
		 String filename = "各资源画像数据.xls";
		 String agent = request.getHeader("User-Agent");
		 try {
		 filename = FileUtils.encodeDownloadFilename(filename, agent);
		 ServletOutputStream out = response.getOutputStream();
		 String mimeType = request.getServletContext().getMimeType(filename);
		 response.setContentType(mimeType);
		 response.setHeader("content-disposition", "attachment;filename=" +
		 filename);
		 workbook.write(out);
		 } catch (IOException e) {
		 logger.error(e.getMessage());
		 }
	}

	/**
	 * 各资源画像/查看资源画像
	 * update by JJJ 20170224 pm
	 */
	@RequestMapping("/checkdetail")
	public String checkdetail(Model model, String type, String magazineid, String title,Date startDate,Date endDate) {
		DateTime sdt = new DateTime(startDate);	
		DateTime edt = new DateTime(endDate);
		
		model.addAttribute("startDate", sdt.toString("yyyy-MM-dd"));
		model.addAttribute("endDate", edt.toString("yyyy-MM-dd"));
		model.addAttribute("type", type);
		model.addAttribute("magazineid", magazineid);
		model.addAttribute("title", title);
		return "statistic/zyhxx/ckzyhxFrame";
	}
	/**
	 * 各资源画像/阅读用户
	 * update by JJJ 20170227 pm
	 */
	@RequestMapping("/gzyhx/ydyh")
	public String ydyh(Model model, String type, String magazineid,Date startDate,Date endDate) {
		DateTime sdt = new DateTime(startDate);	
		DateTime edt = new DateTime(endDate);
		
		model.addAttribute("startDate", sdt.toString("yyyy-MM-dd"));
		model.addAttribute("endDate", edt.toString("yyyy-MM-dd"));
		model.addAttribute("type", type);
		model.addAttribute("magazineid", magazineid);
		return "statistic/zyhxx/ydyh";
	}
	/**
	 * 各资源画像/阅读用户列表
	 * update by JJJ 20170228 pm
	 */
	@RequestMapping("/gzyhx/ydyhList")
	@ResponseBody
	public EasyUiDataGridResult ydyhList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, String type, String magazineid,Date startDate,Date endDate) {
		
		EasyUiDataGridResult result = nlcStatisticService.ydyhList(page, rows,type,magazineid,startDate,endDate,false);
		return result;
	}
	/**
	 * 各资源画像/阅读用户列表 导出
	 * update by JJJ 20170228 pm
	 */
	@RequestMapping("/gzyhx/ydyhExport")
	@ResponseBody
	public void ydyhExport(HttpServletRequest request, HttpServletResponse
			response, String type, String magazineid,Date startDate,Date endDate) {
		
		EasyUiDataGridResult result = nlcStatisticService.ydyhList(null, null,type,magazineid,startDate,endDate,true);
		List<Ydyh> list = null;
		if(result!=null){
			list = (List<Ydyh>) result.getRows();
		}
		 HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet sheet = workbook.createSheet("阅读用户数据");
		 HSSFRow headRow = sheet.createRow(0);
		 headRow.createCell(0).setCellValue("用户ID");
		 headRow.createCell(1).setCellValue("用户IP");
		
		 if (null != list && list.size() > 0) {
			 for (Ydyh po : list) {
			 HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			 dataRow.createCell(0).setCellValue(po.getUserName());
			 dataRow.createCell(1).setCellValue(po.getAddress());
			 }
		 }
		
		 String filename = "阅读用户数据.xls";
		 String agent = request.getHeader("User-Agent");
		 try {
		 filename = FileUtils.encodeDownloadFilename(filename, agent);
		 ServletOutputStream out = response.getOutputStream();
		 String mimeType = request.getServletContext().getMimeType(filename);
		 response.setContentType(mimeType);
		 response.setHeader("content-disposition", "attachment;filename=" +
		 filename);
		 workbook.write(out);
		 } catch (IOException e) {
		 logger.error(e.getMessage());
		 }
	}
	/**
	 * 各资源画像/阅读用户/阅读情况
	 * add by JJJ 20170228 pm
	 */
	@RequestMapping("/gzyhx/ydqk")
	public String readDetail(Model model,String username,Date startDate,Date endDate) {
		DateTime sdt = new DateTime(startDate);	
		DateTime edt = new DateTime(endDate);
		
		model.addAttribute("startDate", sdt.toString("yyyy-MM-dd"));
		model.addAttribute("endDate", edt.toString("yyyy-MM-dd"));
		model.addAttribute("username", username);
		return "statistic/zyhxx/ydqk";
	}
	/**
	 * 各资源画像/阅读用户/阅读情况List
	 * add by JJJ 20170228 pm
	 */
	@RequestMapping("/gzyhx/ydqkList")
	@ResponseBody
	public EasyUiDataGridResult readDetailList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "20") Integer rows,String username,Date startDate,Date endDate) {
		EasyUiDataGridResult result = nlcStatisticService.readDetailList(page, rows,username,startDate,endDate,false);
		return result;
	}
	/**
	 * 各资源画像/阅读用户列表 导出
	 * update by JJJ 20170228 pm
	 */
	@RequestMapping("/gzyhx/ydxqExport")
	@ResponseBody
	public void ydxqExport(HttpServletRequest request, HttpServletResponse response, String username,Date startDate,Date endDate) {
		EasyUiDataGridResult result = nlcStatisticService.readDetailList(null, null,username,startDate,endDate,true);
		List<Ydqk> list = null;
		if(result!=null){
			list = (List<Ydqk>) result.getRows();
		}
		 HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet sheet = workbook.createSheet("阅读详情数据");
		 HSSFRow headRow = sheet.createRow(0);
		 headRow.createCell(0).setCellValue("资源ID");
		 headRow.createCell(1).setCellValue("资源名称");
		 headRow.createCell(2).setCellValue("阅读次数");
		 headRow.createCell(3).setCellValue("阅读时长（分）");
	
		 if (null != list && list.size() > 0) {
			 for (Ydqk po : list) {
			 HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			 dataRow.createCell(0).setCellValue(po.getZyID());
			 dataRow.createCell(1).setCellValue(po.getZymc());
			 dataRow.createCell(2).setCellValue(po.getYdcs());
			 dataRow.createCell(3).setCellValue(po.getYdsc());
			 }
		 }
		
		 String filename = "阅读详情数据.xls";
		 String agent = request.getHeader("User-Agent");
		 try {
		 filename = FileUtils.encodeDownloadFilename(filename, agent);
		 ServletOutputStream out = response.getOutputStream();
		 String mimeType = request.getServletContext().getMimeType(filename);
		 response.setContentType(mimeType);
		 response.setHeader("content-disposition", "attachment;filename=" +
		 filename);
		 workbook.write(out);
		 } catch (IOException e) {
		 logger.error(e.getMessage());
		 }
	}
	
	/**
	 * 各资源画像/查看各资源画像中性别分布的图
	 * update by JJJ 20170224 pm
	 */
	@RequestMapping("/gzyhx/leaf1")
	public String leaf1(Model model, String type, String magazineid,Date startDate,Date endDate) {
		Map<String, String> resmap = nlcStatisticService.ckzyhxxbfb(type, magazineid,startDate,endDate);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("resmap", resmap);
		model.addAttribute("type", type);
		model.addAttribute("magazineid", magazineid);
		return "statistic/zyhxx/leaf1";
	}

	/**
	 * 查看资源画像--性别分布数据的导出
	 * update by JJJ 20170224 pm
	 */
	@RequestMapping("/leaf1Export")
	public void leaf1Export(HttpServletRequest request, HttpServletResponse response, String type, String magazineid,Date startDate,Date endDate) {
		Map<String, String> resmap = nlcStatisticService.ckzyhxxbfb(type, magazineid,startDate,endDate);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("性别分布数据");
		HSSFRow dataRow0 = sheet.createRow(0);
		dataRow0.createCell(0).setCellValue("女");
		dataRow0.createCell(1).setCellValue(resmap.get("fenum"));

		HSSFRow dataRow1 = sheet.createRow(1);
		dataRow1.createCell(0).setCellValue("男");
		dataRow1.createCell(1).setCellValue(resmap.get("manum"));

		HSSFRow dataRow2 = sheet.createRow(2);
		dataRow2.createCell(0).setCellValue("总计");
		dataRow2.createCell(1).setCellValue(resmap.get("sum"));

		String filename = "性别分布数据.xls";
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
	 * 各资源画像/查看各资源画像中年龄分布的图
	 * update by JJJ 20170224 pm
	 */
	@RequestMapping("/gzyhx/leaf2")
	public String leaf2(Model model, String type, String magazineid,Date startDate,Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();
		List<DyfxPoExt> reslist = nlcStatisticService.ckzyhxnlfb1(type, magazineid, resmap,startDate,endDate);
		JSONArray js = JSONArray.fromObject(reslist);
		model.addAttribute("reslist", js);
		model.addAttribute("type", type);
		model.addAttribute("magazineid", magazineid);
		model.addAttribute("resmap", resmap);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statistic/zyhxx/leaf2";
	}

	/**
	 * 查看资源画像--年龄分布数据的导出
	 * update by JJJ 20170224 pm
	 */
	@RequestMapping("/leaf2Export")
	public void leaf2Export(HttpServletRequest request, HttpServletResponse response, String type, String magazineid,Date startDate,Date endDate) {
		Map<String, String> resmap = new HashMap<String, String>();
		List<DyfxPoExt> reslist = nlcStatisticService.ckzyhxnlfb1(type, magazineid, resmap,startDate,endDate);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("年龄分布数据");
		HSSFRow dataRow0 = sheet.createRow(0);
		dataRow0.createCell(0).setCellValue("18岁以下");
		dataRow0.createCell(1).setCellValue(resmap.get("res5"));

		HSSFRow dataRow1 = sheet.createRow(1);
		dataRow1.createCell(0).setCellValue("19~23岁");
		dataRow1.createCell(1).setCellValue(resmap.get("res4"));

		HSSFRow dataRow2 = sheet.createRow(2);
		dataRow2.createCell(0).setCellValue("24~30岁");
		dataRow2.createCell(1).setCellValue(resmap.get("res3"));

		HSSFRow dataRow3 = sheet.createRow(3);
		dataRow3.createCell(0).setCellValue("31~40岁");
		dataRow3.createCell(1).setCellValue(resmap.get("res2"));

		HSSFRow dataRow4 = sheet.createRow(4);
		dataRow4.createCell(0).setCellValue("40岁以上");
		dataRow4.createCell(1).setCellValue(resmap.get("res1"));

		HSSFRow dataRow5 = sheet.createRow(5);
		dataRow5.createCell(0).setCellValue("信息缺失");
		dataRow5.createCell(1).setCellValue(resmap.get("xxqs"));

		HSSFRow dataRow6 = sheet.createRow(6);
		dataRow6.createCell(0).setCellValue("总计");
		dataRow6.createCell(1).setCellValue(resmap.get("sum"));

		String filename = "年龄分布数据.xls";
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
	 * 各资源画像/查看各资源画像中学历分布的图
	 * update by JJJ 20170224 pm
	 */
	@RequestMapping("/gzyhx/leaf3")
	public String leaf3(Model model, String type, String magazineid,Date startDate,Date endDate) {
		Map<String, String> resmap = nlcStatisticService.ckzyhxxlfb(type, magazineid,startDate,endDate);
		model.addAttribute("resmap", resmap);
		model.addAttribute("type", type);
		model.addAttribute("magazineid", magazineid);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statistic/zyhxx/leaf3";
	}

	/**
	 * 查看资源画像--学历分布数据的导出
	 * update by JJJ 20170224 pm
	 */
	@RequestMapping("/leaf3Export")
	public void leaf3Export(HttpServletRequest request, HttpServletResponse response, String type, String magazineid,Date startDate,Date endDate) {
		Map<String, String> resmap = nlcStatisticService.ckzyhxxlfb(type, magazineid,startDate,endDate);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("学历分布数据");
		HSSFRow dataRow0 = sheet.createRow(0);
		dataRow0.createCell(0).setCellValue("专科以下");
		dataRow0.createCell(1).setCellValue(resmap.get("qitaNum"));

		HSSFRow dataRow1 = sheet.createRow(1);
		dataRow1.createCell(0).setCellValue("专科");
		dataRow1.createCell(1).setCellValue(resmap.get("zhuankeNum"));

		HSSFRow dataRow2 = sheet.createRow(2);
		dataRow2.createCell(0).setCellValue("本科");
		dataRow2.createCell(1).setCellValue(resmap.get("benkeNum"));

		HSSFRow dataRow3 = sheet.createRow(3);
		dataRow3.createCell(0).setCellValue("硕士");
		dataRow3.createCell(1).setCellValue(resmap.get("shuoshiNum"));

		HSSFRow dataRow4 = sheet.createRow(4);
		dataRow4.createCell(0).setCellValue("博士");
		dataRow4.createCell(1).setCellValue(resmap.get("boshiNum"));

		HSSFRow dataRow5 = sheet.createRow(5);
		dataRow5.createCell(0).setCellValue("总计");
		dataRow5.createCell(1).setCellValue(resmap.get("sum"));

		String filename = "学历分布数据.xls";
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
	 * 查看资源画像--本资源数据分布全部导出
	 */
	@RequestMapping("/zleafExport")
	public void zleafExport(HttpServletRequest request, HttpServletResponse response, String title, String type,
			String magazineid,Date startDate,Date endDate) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title + "资源画像");

		HSSFRow dataRow0 = sheet.createRow(0);
		dataRow0.createCell(0).setCellValue("资源名称");
		dataRow0.createCell(1).setCellValue(title);
		dataRow0.createCell(2).setCellValue("资源ID");
		dataRow0.createCell(3).setCellValue(magazineid);
		sheet.createRow(1);

		// 性别
		Map<String, String> resmap = nlcStatisticService.ckzyhxxbfb(type, magazineid,startDate,endDate);
		HSSFRow dataRow1 = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow1.createCell(0).setCellValue("性别分布数据");

		HSSFRow dataRow2 = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow2.createCell(0).setCellValue("女");
		dataRow2.createCell(1).setCellValue(resmap.get("fenum"));

		HSSFRow dataRow3 = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow3.createCell(0).setCellValue("男");
		dataRow3.createCell(1).setCellValue(resmap.get("manum"));

		HSSFRow dataRow4 = sheet.createRow(sheet.getLastRowNum() + 1);
		dataRow4.createCell(0).setCellValue("总计");
		dataRow4.createCell(1).setCellValue(resmap.get("sum"));

		sheet.createRow(1);
		// 年龄
		Map<String, String> lresmap = new HashMap<String, String>();
		List<DyfxPoExt> reslist = nlcStatisticService.ckzyhxnlfb1(type, magazineid, lresmap,startDate,endDate);
		HSSFRow ldataRow = sheet.createRow(sheet.getLastRowNum() + 1);
		ldataRow.createCell(0).setCellValue("年龄分布数据");

		HSSFRow ldataRow1 = sheet.createRow(sheet.getLastRowNum() + 1);
		ldataRow1.createCell(0).setCellValue("18岁以下");
		ldataRow1.createCell(1).setCellValue(lresmap.get("res5"));

		HSSFRow ldataRow2 = sheet.createRow(sheet.getLastRowNum() + 1);
		ldataRow2.createCell(0).setCellValue("19~23岁");
		ldataRow2.createCell(1).setCellValue(lresmap.get("res4"));

		HSSFRow ldataRow3 = sheet.createRow(sheet.getLastRowNum() + 1);
		ldataRow3.createCell(0).setCellValue("24~30岁");
		ldataRow3.createCell(1).setCellValue(lresmap.get("res3"));

		HSSFRow ldataRow4 = sheet.createRow(sheet.getLastRowNum() + 1);
		ldataRow4.createCell(0).setCellValue("31~40岁");
		ldataRow4.createCell(1).setCellValue(lresmap.get("res2"));

		HSSFRow ldataRow5 = sheet.createRow(sheet.getLastRowNum() + 1);
		ldataRow5.createCell(0).setCellValue("40岁以上");
		ldataRow5.createCell(1).setCellValue(lresmap.get("res1"));

		HSSFRow ldataRow6 = sheet.createRow(sheet.getLastRowNum() + 1);
		ldataRow6.createCell(0).setCellValue("信息缺失");
		ldataRow6.createCell(1).setCellValue(lresmap.get("xxqs"));

		HSSFRow ldataRow7 = sheet.createRow(sheet.getLastRowNum() + 1);
		ldataRow7.createCell(0).setCellValue("总计");
		ldataRow7.createCell(1).setCellValue(lresmap.get("sum"));

		sheet.createRow(1);
		// 学历
		Map<String, String> xresmap = nlcStatisticService.ckzyhxxlfb(type, magazineid,startDate,endDate);
		HSSFRow xdataRow = sheet.createRow(sheet.getLastRowNum() + 1);
		xdataRow.createCell(0).setCellValue("学历分布数据");

		HSSFRow xdataRow1 = sheet.createRow(sheet.getLastRowNum() + 1);
		xdataRow1.createCell(0).setCellValue("专科以下");
		xdataRow1.createCell(1).setCellValue(xresmap.get("qitaNum"));

		HSSFRow xdataRow2 = sheet.createRow(sheet.getLastRowNum() + 1);
		xdataRow2.createCell(0).setCellValue("专科");
		xdataRow2.createCell(1).setCellValue(xresmap.get("zhuankeNum"));

		HSSFRow xdataRow3 = sheet.createRow(sheet.getLastRowNum() + 1);
		xdataRow3.createCell(0).setCellValue("本科");
		xdataRow3.createCell(1).setCellValue(xresmap.get("benkeNum"));

		HSSFRow xdataRow4 = sheet.createRow(sheet.getLastRowNum() + 1);
		xdataRow4.createCell(0).setCellValue("硕士");
		xdataRow4.createCell(1).setCellValue(xresmap.get("shuoshiNum"));

		HSSFRow xdataRow5 = sheet.createRow(sheet.getLastRowNum() + 1);
		xdataRow5.createCell(0).setCellValue("博士");
		xdataRow5.createCell(1).setCellValue(xresmap.get("boshiNum"));

		HSSFRow xdataRow6 = sheet.createRow(sheet.getLastRowNum() + 1);
		xdataRow6.createCell(0).setCellValue("总计");
		xdataRow6.createCell(1).setCellValue(xresmap.get("sum"));

		// =============================
		String filename = title + "资源画像.xls";
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
	 * 日期插件的联动样例
	 */
	@RequestMapping("/sample")
	public String sample(Model model) {
		return "statistic/sample";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */

	@RequestMapping("/yhfx/sffx")
	public String sffx(Model model, HttpSession session) {
		Nlcadmin dbNlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
		Date loginTime = (Date) session.getAttribute("loginTime");
		String loginIp = (String) session.getAttribute("loginIp");

		Nlcadminlog nlcadminlog = new Nlcadminlog();
		nlcadminlog.setUsername(dbNlcadmin.getUsername());
		nlcadminlog.setRole(dbNlcadmin.getRole());
		nlcadminlog.setIp(loginIp);
		nlcadminlog.setTime(loginTime);
		nlcadminlog.setOperate("查看第三方用户分享");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);

		DateTime now = new DateTime();
		DateTime startDate = new DateTime(now.minusMonths(3).toString("yyyy-MM-01"));
		DateTime endDate = new DateTime(now.minusDays(1).toString("yyyy-MM-01"));

		int startDateweeks = startDate.getWeekOfWeekyear();
		int endDateweeks = endDate.getWeekOfWeekyear();

		// 昨天 + 周数
		model.addAttribute("startDateweeks", startDate.toString("yyyy-MM-dd") + " " + startDateweeks);
		// 上个月 + 周数
		model.addAttribute("endDateweeks", endDate.toString("yyyy-MM-dd") + " " + endDateweeks);

		// 日
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "statistic/yhfx/sffx";
	}

	/**
	 * 新增用户趋势
	 * 
	 * @return
	 */
	@RequestMapping("/sffxPic3List")
	@ResponseBody
	public JSONObject sffxPic3List(Date startDate, Date endDate) {
		List<String> labelsList = new ArrayList<String>();

		DateTime startDateJoda = new DateTime(startDate);
		DateTime endDateJoda = new DateTime(endDate);
		if (startDateJoda.getMillis() > endDateJoda.getMillis()) {// 起始时间必须小于等于结束时间
			String res = "{result:false}";
			return JSONObject.fromObject(res);
		}

		while (startDateJoda.getMillis() <= endDateJoda.getMillis()) {
			labelsList.add(startDateJoda.toString("yyyy-MM"));
			startDateJoda = startDateJoda.plusMonths(1);
		}

		List<Integer> flowList = nlcStatisticService.sffxDataList(startDate, endDate);

		String result = "{result:true,flow:" + flowList + ", labels:" + labelsList + "}";
		return JSONObject.fromObject(result);
	}

	@RequestMapping("/sffxtable")
	public String sffxtable(Model model, Date startDate, Date endDate, String type) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("type", type);
		return "statistic/yhfx/sffxlb";
	}

	/**
	 * 第三方分享列表 update by JJJ
	 */
	@RequestMapping("/sffxtableList")
	@ResponseBody
	public EasyUiDataGridResult sffxtableList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String type) {
		if (startDate == null)
			return null;
		EasyUiDataGridResult result = nlcStatisticService.sffxtableList(page, rows, startDate, endDate, type, false);
		return result;
	}

	/**
	 * 第三方分享饼图数据 update by JJJ
	 */
	@RequestMapping("/sffxhuan")
	@ResponseBody
	public Map<String, String> sffxhuan(Model model, Date startDate, Date endDate) {
		Map<String, String> xlList = nlcStatisticService.sffxsj(startDate, endDate);
		model.addAttribute("resmap", xlList);
		return xlList;
	}

	@RequestMapping("/sffxExport")
	@ResponseBody
	public void sffxExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,String type) {
		EasyUiDataGridResult result = nlcStatisticService.sffxtableList(null, null, startDate, endDate, type, true);
		List<SharePo> list = null;
		if(result!=null){
			list = (List<SharePo>) result.getRows();
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("第三方分享数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间 ");
		headRow.createCell(1).setCellValue("QQ好友 ");
		headRow.createCell(2).setCellValue("QQ空间 ");
		headRow.createCell(3).setCellValue("微信好友 ");
		headRow.createCell(4).setCellValue("微信朋友圈 ");
		headRow.createCell(5).setCellValue("微信收藏 ");
		headRow.createCell(6).setCellValue("新浪微博 ");

		if (null != list && list.size() > 0) {
			Iterator<SharePo> iterator = list.iterator();
			while (iterator.hasNext()) {
				SharePo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

				dataRow.createCell(0).setCellValue(detail.getMonth());
				dataRow.createCell(1).setCellValue(detail.getQqfriend());
				dataRow.createCell(2).setCellValue(detail.getQqzone());
				dataRow.createCell(3).setCellValue(detail.getWxfriend());
				dataRow.createCell(4).setCellValue(detail.getWxquanzi());
				dataRow.createCell(5).setCellValue(detail.getWxfavorite());
				dataRow.createCell(6).setCellValue(detail.getSinaweibo());
			}
		}

		String filename = "第三方分享数据.xls";
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
