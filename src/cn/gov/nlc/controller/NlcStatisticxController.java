package cn.gov.nlc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

import cn.gov.nlc.pojo.GgwDetailPo;
import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.service.NlcStatisticServicex;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.vo.ApptjPo;
import cn.gov.nlc.vo.DetailPo;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import cn.gov.nlc.vo.PersonPo;
import cn.gov.nlc.vo.WjDetailPo;
import cn.gov.nlc.vo.WjYmfwPo;
import cn.gov.nlc.vo.YmfwPo;
import cn.gov.nlc.vo.YmfwfxPo;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/statisticx")
public class NlcStatisticxController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcStatisticxController.class);

	@Autowired
	private NlcStatisticServicex nlcStatisticServicex;

	// =========================页面访问量====================

	/**
	 * 跳转到用访问量下的页面访问量
	 */
	@RequestMapping("/ymfwl")
	public String ymfwlFrameshow(Model model) {
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
		return "statisticx/ymfwl/frame";
	}

	/**
	 * 页面访问量按天统计
	 */
	@RequestMapping("/dayymfw")
	@ResponseBody
	public JSONObject dayymfw(Date startDate, Date endDate) {
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

		List<Integer> flowList = nlcStatisticServicex.dayymfwpv(startDate, endDate);
		List<Integer> flowList2 = nlcStatisticServicex.dayymfwuv(startDate, endDate);
		String result = "{pvflow:" + flowList + ", labels:" + labelsList + ", uvflow:" + flowList2 + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 页面访问按天的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dayymfwGrid")
	public String dayymfwGrid(Model model, Date startDate, Date endDate) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statisticx/ymfwl/dayymfwGrid";
	}

	/**
	 * 页面访问按天的list数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/dayymfwList")
	@ResponseBody
	public EasyUiDataGridResult dayymfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = nlcStatisticServicex.dayymfwList(page, rows, startDate, endDate, "schepageview");
		return result;
	}

	/**
	 * 页面访问按天的导出
	 */
	@RequestMapping("/ymfwExport")
	public void ymfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<YmfwPo> list = nlcStatisticServicex.dayymfwExport(startDate, endDate, "schepageview");

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "页面访问量.xls";
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

	// ======页面访问按月的统计=========

	@RequestMapping("/monymfw")
	@ResponseBody
	public JSONObject monymfw(Date startDate, Date endDate) {
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

		List<Integer> flowList = nlcStatisticServicex.monymfwpv(startDate, endDate);
		List<Integer> flowList2 = nlcStatisticServicex.monymfwuv(startDate, endDate);
		String result = "{pvflow:" + flowList + ", labels:" + labelsList + ", uvflow:" + flowList2 + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 页面访问按月的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/monymfwGrid")
	public String monymfwGrid(Model model, Date startDate, Date endDate) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statisticx/ymfwl/monymfwGrid";
	}

	/**
	 * 页面访问按月的list数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/monymfwList")
	@ResponseBody
	public EasyUiDataGridResult monymfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = nlcStatisticServicex.monymfwList(page, rows, startDate, endDate, "schepageview");
		return result;
	}

	/**
	 * 页面访问按月的导出
	 */
	@RequestMapping("/monymfwExport")
	public void monymfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<YmfwPo> list = nlcStatisticServicex.monymfwExport(startDate, endDate, "schepageview");

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "页面访问量.xls";
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

	// ====================页面访问-年

	@RequestMapping("/yearymfw")
	@ResponseBody
	public JSONObject yearymfw(Date startDate, Date endDate) {
		DateTime dstartDate = new DateTime(startDate);
		DateTime dendDate = new DateTime(endDate);
		int years = Years.yearsBetween(dstartDate, dendDate).getYears();

		List<String> labelsList = new ArrayList<String>();
		for (int i = 0; i < years + 1; i++) {
			labelsList.add(dstartDate.toString("yyyy"));
			dstartDate = dstartDate.plusYears(1);
		}

		List<Integer> flowList = nlcStatisticServicex.yearymfwpv(startDate, endDate);
		List<Integer> flowList2 = nlcStatisticServicex.yearymfwuv(startDate, endDate);
		String result = "{pvflow:" + flowList + ", labels:" + labelsList + ", uvflow:" + flowList2 + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 页面访问按年的datagrid
	 * 
	 */
	@RequestMapping(value = "/yearymfwGrid")
	public String yearymfwGrid(Model model, Date startDate, Date endDate) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statisticx/ymfwl/yearymfwGrid";
	}

	/**
	 * 页面访问按年的list数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/yearymfwList")
	@ResponseBody
	public EasyUiDataGridResult yearymfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = nlcStatisticServicex.yearymfwList(page, rows, startDate, endDate, "schepageview");
		return result;
	}

	/**
	 * 页面访问按年的导出
	 */
	@RequestMapping("/yearymfwExport")
	public void yearymfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<YmfwPo> list = nlcStatisticServicex.yearymfwExport(startDate, endDate, "schepageview");

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "页面访问量.xls";
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

	// ==================页面访问按周的统计

	@RequestMapping("/weekymfw")
	@ResponseBody
	public JSONObject weekymfw(Date startDate, Date endDate) {
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
		
		List<Integer> flowList = nlcStatisticServicex.weekymfwpv(startDate, endDate);
		List<Integer> flowList2 = nlcStatisticServicex.weekymfwuv(startDate, endDate);
		String result = "{pvflow:" + flowList + ", labels:" + labelsList + ", uvflow:" + flowList2 + "}";
		return JSONObject.fromObject(result);
	}

	/**
	 * 页面访问按周的datagrid
	 * 
	 */
	@RequestMapping(value = "/weekymfwGrid")
	public String weekymfwGrid(Model model, Date startDate, Date endDate) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "statisticx/ymfwl/weekymfwGrid";
	}

	/**
	 * 页面访问按周的list数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/weekymfwList")
	@ResponseBody
	public EasyUiDataGridResult weekymfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = nlcStatisticServicex.weekymfwList(page, rows, startDate, endDate, "schepageview");
		return result;
	}

	/**
	 * 页面访问按周的导出
	 */
	@RequestMapping("/weekymfwExport")
	public void weekymfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate) {
		List<YmfwPo> list = nlcStatisticServicex.weekymfwExport(startDate, endDate, "schepageview");

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "页面访问量.xls";
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

	// ==================opac============

	/**
	 * 跳转到页面访问量下的opac检索
	 */
	@RequestMapping("/opac")
	public String opacFrameshow(Model model) {
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
		return "statisticx/opac/frame";
	}

	/**
	 * opac页面访问量的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/opacSearchYmfwGrid")
	public String opacSearchYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/opac/opacSearchYmfwGrid";
	}

	/**
	 * opac页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/opacSearchYmfwList")
	@ResponseBody
	public EasyUiDataGridResult opacSearchYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwList(page, rows, startDate, endDate, "scheopacamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwList(page, rows, startDate, endDate, "scheopacamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwList(page, rows, startDate, endDate, "scheopacamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwList(page, rows, startDate, endDate, "scheopacamount");
		}

		return result;
	}

	/**
	 * opac页面访问量的export
	 */
	@RequestMapping("/opacYmfwExport")
	public void opacYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwExport(startDate, endDate, "scheopacamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwExport(startDate, endDate, "scheopacamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwExport(startDate, endDate, "scheopacamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwExport(startDate, endDate, "scheopacamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("OPAC页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "OPAC页面访问量.xls";
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
	 * opac热词的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/opacHotwordGrid")
	public String opacHotwordGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/opac/opacHotwordGrid";
	}

	/**
	 * opac热词的list数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/opacHotwordList")
	@ResponseBody
	public EasyUiDataGridResult opacHotwordList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayHotwordList(page, rows, startDate, endDate, "schehotword");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monHotwordList(page, rows, startDate, endDate, "schehotword");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekHotwordList(page, rows, startDate, endDate, "schehotword");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearHotwordList(page, rows, startDate, endDate, "schehotword");
		}
		return result;
	}

	/**
	 * opac热词的export
	 */
	@RequestMapping("/opacHotwordExport")
	public void opacHotwordExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<Hotword> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayHotwordExport(startDate, endDate, "schehotword");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekHotwordExport(startDate, endDate, "schehotword");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monHotwordExport(startDate, endDate, "schehotword");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearHotwordExport(startDate, endDate, "schehotword");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("OPAC热词");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("热词");
		headRow.createCell(1).setCellValue("搜索次数");

		if (null != list && list.size() > 0) {
			Iterator<Hotword> iterator = list.iterator();
			while (iterator.hasNext()) {
				Hotword detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getHotword());
				dataRow.createCell(1).setCellValue(detail.getSeacount());
			}
		}

		String filename = "OPAC热词.xls";
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

	// =======================olcc=========================

	/**
	 * 跳转到页面访问量下的olcc检索
	 */
	@RequestMapping("/olcc")
	public String olccFrameshow(Model model) {
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
		return "statisticx/olcc/frame";
	}

	/**
	 * olcc页面访问量的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/olccSearchYmfwGrid")
	public String olccSearchYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/olcc/olccSearchYmfwGrid";
	}

	/**
	 * olcc页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/olccSearchYmfwList")
	@ResponseBody
	public EasyUiDataGridResult olccSearchYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwList(page, rows, startDate, endDate, "scheolccamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwList(page, rows, startDate, endDate, "scheolccamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwList(page, rows, startDate, endDate, "scheolccamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwList(page, rows, startDate, endDate, "scheolccamount");
		}

		return result;
	}

	/**
	 * olcc页面访问量的export
	 */
	@RequestMapping("/olccYmfwExport")
	public void olccYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwExport(startDate, endDate, "scheolccamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwExport(startDate, endDate, "scheolccamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwExport(startDate, endDate, "scheolccamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwExport(startDate, endDate, "scheolccamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("OLCC页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "OLCC页面访问量.xls";
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
	 * olcc热词的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/olccHotwordGrid")
	public String olccHotwordGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/olcc/olccHotwordGrid";
	}

	/**
	 * olcc热词的list数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/olccHotwordList")
	@ResponseBody
	public EasyUiDataGridResult olccHotwordList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayHotwordList(page, rows, startDate, endDate, "scheolcchotword");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monHotwordList(page, rows, startDate, endDate, "scheolcchotword");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekHotwordList(page, rows, startDate, endDate, "scheolcchotword");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearHotwordList(page, rows, startDate, endDate, "scheolcchotword");
		}
		return result;
	}

	/**
	 * olcc热词的export
	 */
	@RequestMapping("/olccHotwordExport")
	public void olccHotwordExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<Hotword> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayHotwordExport(startDate, endDate, "scheolcchotword");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekHotwordExport(startDate, endDate, "scheolcchotword");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monHotwordExport(startDate, endDate, "scheolcchotword");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearHotwordExport(startDate, endDate, "scheolcchotword");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("OLCC热词");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("热词");
		headRow.createCell(1).setCellValue("搜索次数");

		if (null != list && list.size() > 0) {
			Iterator<Hotword> iterator = list.iterator();
			while (iterator.hasNext()) {
				Hotword detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getHotword());
				dataRow.createCell(1).setCellValue(detail.getSeacount());
			}
		}

		String filename = "OLCC热词.xls";
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

	// ==============站内搜索===========================

	/**
	 * 跳转到页面访问量下的站内搜索
	 */
	@RequestMapping("/znss")
	public String znssFrameshow(Model model) {
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
		return "statisticx/znss/frame";
	}

	/**
	 * 站内搜索页面访问量的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/znssSearchYmfwGrid")
	public String znssSearchYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/znss/znssSearchYmfwGrid";
	}

	/**
	 * 站内搜索页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/znssSearchYmfwList")
	@ResponseBody
	public EasyUiDataGridResult znssSearchYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwList(page, rows, startDate, endDate, "scheznssamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwList(page, rows, startDate, endDate, "scheznssamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwList(page, rows, startDate, endDate, "scheznssamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwList(page, rows, startDate, endDate, "scheznssamount");
		}

		return result;
	}

	/**
	 * 站内搜索页面访问量的export
	 */
	@RequestMapping("/znssYmfwExport")
	public void znssYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwExport(startDate, endDate, "scheznssamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwExport(startDate, endDate, "scheznssamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwExport(startDate, endDate, "scheznssamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwExport(startDate, endDate, "scheznssamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("站内搜索页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "站内搜索页面访问量.xls";
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

	// =============扫一扫====================

	/**
	 * 跳转到页面访问量下的扫一扫
	 */
	@RequestMapping("/sys")
	public String sysFrameshow(Model model) {
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
		return "statisticx/sys/frame";
	}

	/**
	 * 扫一扫页面访问量的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sysSearchYmfwGrid")
	public String sysSearchYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/sys/sysSearchYmfwGrid";
	}

	/**
	 * 扫一扫页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/sysSearchYmfwList")
	@ResponseBody
	public EasyUiDataGridResult sysSearchYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwList(page, rows, startDate, endDate, "schesysamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwList(page, rows, startDate, endDate, "schesysamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwList(page, rows, startDate, endDate, "schesysamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwList(page, rows, startDate, endDate, "schesysamount");
		}

		return result;
	}

	/**
	 * 扫一扫页面访问量的export
	 */
	@RequestMapping("/sysYmfwExport")
	public void sysYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwExport(startDate, endDate, "schesysamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwExport(startDate, endDate, "schesysamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwExport(startDate, endDate, "schesysamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwExport(startDate, endDate, "schesysamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("扫一扫页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "扫一扫页面访问量.xls";
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

	// ==========================广告位========================

	/**
	 * 跳转到页面访问量下的广告位
	 */
	@RequestMapping("/ggw")
	public String ggwFrameshow(Model model) {
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
		return "statisticx/ggw/frame";
	}

	/**
	 * 广告位页面访问量的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ggwSearchYmfwGrid")
	public String ggwSearchYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/ggw/ggwSearchYmfwGrid";
	}

	/**
	 * 广告位页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/ggwSearchYmfwList")
	@ResponseBody
	public EasyUiDataGridResult ggwSearchYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwList(page, rows, startDate, endDate, "scheadsamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwList(page, rows, startDate, endDate, "scheadsamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwList(page, rows, startDate, endDate, "scheadsamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwList(page, rows, startDate, endDate, "scheadsamount");
		}

		return result;
	}

	/**
	 * 广告位页面访问量的export
	 */
	@RequestMapping("/ggwYmfwExport")
	public void ggwYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwExport(startDate, endDate, "scheadsamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwExport(startDate, endDate, "scheadsamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwExport(startDate, endDate, "scheadsamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwExport(startDate, endDate, "scheadsamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("广告位页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "广告位页面访问量.xls";
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
	 * 广告位页面访问量详情的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ggwDetailYmfwGrid")
	public String ggwDetailYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/ggw/ggwDetailYmfwGrid";
	}

	/**
	 * 广告位页面访问量详情的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/ggwDetailYmfwList")
	@ResponseBody
	public EasyUiDataGridResult ggwDetailYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayGgwDetailList(page, rows, startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekGgwDetailList(page, rows, startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monGgwDetailList(page, rows, startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearGgwDetailList(page, rows, startDate, endDate);
		}

		return result;
	}

	/**
	 * 广告位页面访问量详情的export
	 */
	@RequestMapping("/ggwDetailYmfwExport")
	public void ggwDetailYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<GgwDetailPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayGgwDetailExport(startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekGgwDetailExport(startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monGgwDetailExport(startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearGgwDetailExport(startDate, endDate);
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("广告位访问量详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("广告名");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<GgwDetailPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				GgwDetailPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTitle());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "广告位访问量详情.xls";
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

	// =========================新闻=================

	/**
	 * 跳转到页面访问量下的新闻
	 */
	@RequestMapping("/news")
	public String newsFrameshow(Model model) {
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
		return "statisticx/news/frame";
	}

	/**
	 * 新闻页面访问量的datagrid
	 */
	@RequestMapping(value = "/newsYmfwGrid")
	public String newsSearchYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/news/newsYmfwGrid";
	}

	/**
	 * 新闻页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/newsYmfwList")
	@ResponseBody
	public EasyUiDataGridResult newsYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwfxList(page, rows, startDate, endDate, "schenewsamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwfxList(page, rows, startDate, endDate, "schenewsamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwfxList(page, rows, startDate, endDate, "schenewsamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwfxList(page, rows, startDate, endDate, "schenewsamount");
		}

		return result;
	}

	/**
	 * 新闻页面访问量的export
	 */
	@RequestMapping("/newsYmfwExport")
	public void newsYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwfxPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwfxExport(startDate, endDate, "schenewsamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwfxExport(startDate, endDate, "schenewsamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwfxExport(startDate, endDate, "schenewsamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwfxExport(startDate, endDate, "schenewsamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("新闻页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<YmfwfxPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwfxPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getShare());
			}
		}

		String filename = "新闻页面访问量.xls";
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
	 * 新闻页面详情访问量的datagrid
	 */
	@RequestMapping(value = "/newsDetailYmfwGrid")
	public String newsDetailYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/news/newsDetailYmfwGrid";
	}

	/**
	 * 新闻页面详情访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/newsDetailYmfwList")
	@ResponseBody
	public EasyUiDataGridResult newsDetailYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayDetailFxList(page, rows, startDate, endDate, "schenewsdetail", "nlcnews", "newsid");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekDetailFxList(page, rows, startDate, endDate, "schenewsdetail", "nlcnews", "newsid");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monDetailFxList(page, rows, startDate, endDate, "schenewsdetail", "nlcnews", "newsid");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearDetailFxList(page, rows, startDate, endDate, "schenewsdetail", "nlcnews", "newsid");
		}

		return result;
	}

	/**
	 * 新闻页面详情访问量的export
	 */
	@RequestMapping("/newsDetailYmfwExport")
	public void newsDetailYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<DetailPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayDetailFxExport(startDate, endDate, "schenewsdetail");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekDetailFxExport(startDate, endDate, "schenewsdetail");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monDetailFxExport(startDate, endDate, "schenewsdetail");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearDetailFxExport(startDate, endDate, "schenewsdetail");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("新闻访问量详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("新闻标题");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("share");

		if (null != list && list.size() > 0) {
			Iterator<DetailPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				DetailPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTitle());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getShare());
			}
		}

		String filename = "新闻访问量详情.xls";
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

	// =========================公告=================

	/**
	 * 跳转到页面访问量下的公告
	 */
	@RequestMapping("/notice")
	public String noticeFrameshow(Model model) {
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
		return "statisticx/notice/frame";
	}

	/**
	 * 公告页面访问量的datagrid
	 */
	@RequestMapping(value = "/noticeYmfwGrid")
	public String noticeYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/notice/noticeYmfwGrid";
	}

	/**
	 * 公告页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/noticeYmfwList")
	@ResponseBody
	public EasyUiDataGridResult noticeYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwfxList(page, rows, startDate, endDate, "schenoticeamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwfxList(page, rows, startDate, endDate, "schenoticeamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwfxList(page, rows, startDate, endDate, "schenoticeamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwfxList(page, rows, startDate, endDate, "schenoticeamount");
		}

		return result;
	}

	/**
	 * 公告页面访问量的export
	 */
	@RequestMapping("/noticeYmfwExport")
	public void noticeYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwfxPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwfxExport(startDate, endDate, "schenoticeamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwfxExport(startDate, endDate, "schenoticeamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwfxExport(startDate, endDate, "schenoticeamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwfxExport(startDate, endDate, "schenoticeamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("公告页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<YmfwfxPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwfxPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getShare());
			}
		}

		String filename = "公告页面访问量.xls";
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
	 * 公告页面详情访问量的datagrid
	 */
	@RequestMapping(value = "/noticeDetailYmfwGrid")
	public String noticeDetailYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/notice/noticeDetailYmfwGrid";
	}

	/**
	 * 公告页面详情访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/noticeDetailYmfwList")
	@ResponseBody
	public EasyUiDataGridResult noticeDetailYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayDetailFxList(page, rows, startDate, endDate, "schenoticedetail", "nlcnotice", "noticeid");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekDetailFxList(page, rows, startDate, endDate, "schenoticedetail", "nlcnotice", "noticeid");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monDetailFxList(page, rows, startDate, endDate, "schenoticedetail", "nlcnotice", "noticeid");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearDetailFxList(page, rows, startDate, endDate, "schenoticedetail", "nlcnotice", "noticeid");
		}

		return result;
	}

	/**
	 * 公告页面详情访问量的export
	 */
	@RequestMapping("/noticeDetailYmfwExport")
	public void noticeDetailYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<DetailPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayDetailFxExport(startDate, endDate, "schenoticedetail");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekDetailFxExport(startDate, endDate, "schenoticedetail");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monDetailFxExport(startDate, endDate, "schenoticedetail");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearDetailFxExport(startDate, endDate, "schenoticedetail");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("公告访问量详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("公告标题");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<DetailPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				DetailPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTitle());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getShare());
			}
		}

		String filename = "公告访问量详情.xls";
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

	// =========================讲座预告=================

	/**
	 * 跳转到页面访问量下的讲座预告
	 */
	@RequestMapping("/trailer")
	public String trailerFrameshow(Model model) {
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
		return "statisticx/trailer/frame";
	}

	/**
	 * 讲座预告页面访问量的datagrid
	 */
	@RequestMapping(value = "/trailerYmfwGrid")
	public String trailerYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/trailer/trailerYmfwGrid";
	}

	/**
	 * 讲座预告页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/trailerYmfwList")
	@ResponseBody
	public EasyUiDataGridResult trailerYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwfxList(page, rows, startDate, endDate, "schetraileramount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwfxList(page, rows, startDate, endDate, "schetraileramount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwfxList(page, rows, startDate, endDate, "schetraileramount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwfxList(page, rows, startDate, endDate, "schetraileramount");
		}

		return result;
	}

	/**
	 * 讲座预告页面访问量的export
	 */
	@RequestMapping("/trailerYmfwExport")
	public void trailerYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<YmfwfxPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwfxExport(startDate, endDate, "schetraileramount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwfxExport(startDate, endDate, "schetraileramount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwfxExport(startDate, endDate, "schetraileramount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwfxExport(startDate, endDate, "schetraileramount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("讲座预告页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<YmfwfxPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwfxPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getShare());
			}
		}

		String filename = "讲座预告页面访问量.xls";
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
	 * 讲座预告页面详情访问量的datagrid
	 */
	@RequestMapping(value = "/trailerDetailYmfwGrid")
	public String trailerDetailYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/trailer/trailerDetailYmfwGrid";
	}

	/**
	 * 讲座预告页面详情访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/trailerDetailYmfwList")
	@ResponseBody
	public EasyUiDataGridResult trailerDetailYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayDetailFxList(page, rows, startDate, endDate, "schetrailerdetail", "nlctrailer", "trailerid");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekDetailFxList(page, rows, startDate, endDate, "schetrailerdetail", "nlctrailer", "trailerid");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monDetailFxList(page, rows, startDate, endDate, "schetrailerdetail", "nlctrailer", "trailerid");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearDetailFxList(page, rows, startDate, endDate, "schetrailerdetail", "nlctrailer", "trailerid");
		}

		return result;
	}

	/**
	 * 讲座预告页面详情访问量的export
	 */
	@RequestMapping("/trailerDetailYmfwExport")
	public void trailerDetailYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<DetailPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayDetailFxExport(startDate, endDate, "schetrailerdetail");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekDetailFxExport(startDate, endDate, "schetrailerdetail");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monDetailFxExport(startDate, endDate, "schetrailerdetail");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearDetailFxExport(startDate, endDate, "schetrailerdetail");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("讲座预告访问量详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("讲座预告标题");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<DetailPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				DetailPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTitle());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getShare());
			}
		}

		String filename = "讲座预告访问量详情.xls";
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

	// =========================专题=================

	/**
	 * 跳转到页面访问量下的专题
	 */
	@RequestMapping("/subject")
	public String subjectFrameshow(Model model) {
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
		return "statisticx/subject/frame";
	}

	/**
	 * 专题页面访问量的datagrid
	 */
	@RequestMapping(value = "/subjectYmfwGrid")
	public String subjectYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/subject/subjectYmfwGrid";
	}

	/**
	 * 专题页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/subjectYmfwList")
	@ResponseBody
	public EasyUiDataGridResult subjectYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwfxList(page, rows, startDate, endDate, "schesubjectamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwfxList(page, rows, startDate, endDate, "schesubjectamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwfxList(page, rows, startDate, endDate, "schesubjectamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwfxList(page, rows, startDate, endDate, "schesubjectamount");
		}

		return result;
	}

	/**
	 * 专题页面访问量的export
	 */
	@RequestMapping("/subjectYmfwExport")
	public void subjectYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<YmfwfxPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwfxExport(startDate, endDate, "schesubjectamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwfxExport(startDate, endDate, "schesubjectamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwfxExport(startDate, endDate, "schesubjectamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwfxExport(startDate, endDate, "schesubjectamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("专题页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<YmfwfxPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwfxPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getShare());
			}
		}

		String filename = "专题页面访问量.xls";
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
	 * 专题页面详情访问量的datagrid
	 */
	@RequestMapping(value = "/subjectDetailYmfwGrid")
	public String subjectDetailYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/subject/subjectDetailYmfwGrid";
	}

	/**
	 * 专题页面详情访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/subjectDetailYmfwList")
	@ResponseBody
	public EasyUiDataGridResult subjectDetailYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayDetailFxList(page, rows, startDate, endDate, "schesubjectdetail", "nlcsubject", "subjectid");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekDetailFxList(page, rows, startDate, endDate, "schesubjectdetail", "nlcsubject", "subjectid");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monDetailFxList(page, rows, startDate, endDate, "schesubjectdetail", "nlcsubject", "subjectid");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearDetailFxList(page, rows, startDate, endDate, "schesubjectdetail", "nlcsubject", "subjectid");
		}

		return result;
	}

	/**
	 * 专题页面详情访问量的export
	 */
	@RequestMapping("/subjectDetailYmfwExport")
	public void subjectDetailYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<DetailPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayDetailFxExport(startDate, endDate, "schesubjectdetail");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekDetailFxExport(startDate, endDate, "schesubjectdetail");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monDetailFxExport(startDate, endDate, "schesubjectdetail");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearDetailFxExport(startDate, endDate, "schesubjectdetail");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("专题访问量详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("专题标题");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<DetailPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				DetailPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTitle());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getShare());
			}
		}

		String filename = "专题访问量详情.xls";
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
	
	//===========================书架==================
	
	/**
	 * 跳转到页面访问量下的书架
	 */
	@RequestMapping("/sj")
	public String sjFrameshow(Model model) {
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
		return "statisticx/sj/frame";
	}

	/**
	 * 书架页面访问量的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sjYmfwGrid")
	public String sjYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/sj/sjYmfwGrid";
	}

	/**
	 * 书架页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/sjYmfwList")
	@ResponseBody
	public EasyUiDataGridResult sjYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwList(page, rows, startDate, endDate, "schesjamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwList(page, rows, startDate, endDate, "schesjamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwList(page, rows, startDate, endDate, "schesjamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwList(page, rows, startDate, endDate, "schesjamount");
		}

		return result;
	}

	/**
	 * 书架页面访问量的export
	 */
	@RequestMapping("/sjYmfwExport")
	public void sjYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwExport(startDate, endDate, "schesjamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwExport(startDate, endDate, "schesjamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwExport(startDate, endDate, "schesjamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwExport(startDate, endDate, "schesjamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("书架页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "书架页面访问量.xls";
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
	
	// =========================文津=================

	/**
	 * 跳转到页面访问量下的文津
	 */
	@RequestMapping("/wj")
	public String wjFrameshow(Model model) {
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
		return "statisticx/wj/frame";
	}

	/**
	 * 文津页面访问量的datagrid
	 */
	@RequestMapping(value = "/wjYmfwGrid")
	public String wjYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/wj/wjYmfwGrid";
	}
	
	/**
	 * 文津页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/wjYmfwList")
	@ResponseBody
	public EasyUiDataGridResult wjYmfwList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwWjList(page, rows, startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwWjList(page, rows, startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwWjList(page, rows, startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwWjList(page, rows, startDate, endDate);
		}
		return result;
	}
	
	/**
	 * 文津页面总访问量的export
	 */
	@RequestMapping("/wjYmfwExport")
	public void wjYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate, String status) {
		List<WjYmfwPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayWjymfwfxExport(startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekWjymfwfxExport(startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monWjymfwfxExport(startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearWjymfwfxExport(startDate, endDate);
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("文津页面总访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("音频播放量");
		headRow.createCell(4).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<WjYmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				WjYmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getAudio());
				dataRow.createCell(4).setCellValue(detail.getShare());
			}
		}

		String filename = "文津页面总访问量.xls";
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
	 * 专题页面详情访问量的datagrid
	 */
	@RequestMapping(value = "/wjDetailYmfwGrid")
	public String wjDetailYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/wj/wjDetailYmfwGrid";
	}
	
	/**
	 * 文津页面详情访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/wjDetailYmfwList")
	@ResponseBody
	public EasyUiDataGridResult wjDetailYmfwList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayWjDetailFxList(page, rows, startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekWjDetailFxList(page, rows, startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monWjDetailFxList(page, rows, startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearWjDetailFxList(page, rows, startDate, endDate);
		}

		return result;
	}
	
	/**
	 * 文津页面详情访问量的export
	 */
	@RequestMapping("/wjDetailYmfwExport")
	public void wjDetailYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<WjDetailPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayWjDetailFxExport(startDate, endDate);
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekWjDetailFxExport(startDate, endDate);
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monWjDetailFxExport(startDate, endDate);
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearWjDetailFxExport(startDate, endDate);
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("文津页面访问量详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("专题标题");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");
		headRow.createCell(3).setCellValue("音频访问量");
		headRow.createCell(4).setCellValue("第三方分享量");

		if (null != list && list.size() > 0) {
			Iterator<WjDetailPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				WjDetailPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTitle());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
				dataRow.createCell(3).setCellValue(detail.getAudio());
				dataRow.createCell(4).setCellValue(detail.getShare());
			}
		}

		String filename = "文津页面访问量详情.xls";
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
	
	//==========================个人================================
	
	/**
	 * 跳转到页面访问量下的个人
	 */
	@RequestMapping("/person")
	public String personFrameshow(Model model) {
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
		return "statisticx/person/frame";
	}

	/**
	 * 个人页面访问量的datagrid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personYmfwGrid")
	public String personYmfwGrid(Model model, Date startDate, Date endDate, String status) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		return "statisticx/person/personYmfwGrid";
	}

	/**
	 * 个人页面访问量的list
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/personYmfwList")
	@ResponseBody
	public EasyUiDataGridResult personYmfwList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, Date startDate, Date endDate, String status) {
		if (null == startDate) {
			return null;
		}

		EasyUiDataGridResult result = null;
		if ("day".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.dayymfwList(page, rows, startDate, endDate, "schepersonamount");
		} else if ("week".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.weekymfwList(page, rows, startDate, endDate, "schepersonamount");
		} else if ("month".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.monymfwList(page, rows, startDate, endDate, "schepersonamount");
		} else if ("year".equalsIgnoreCase(status)) {
			result = nlcStatisticServicex.yearymfwList(page, rows, startDate, endDate, "schepersonamount");
		}

		return result;
	}

	/**
	 * 个人页面访问量的export
	 */
	@RequestMapping("/personYmfwExport")
	public void personYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate, Date endDate,
			String status) {
		List<YmfwPo> list = null;
		if ("day".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.dayymfwExport(startDate, endDate, "schepersonamount");
		} else if ("week".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.weekymfwExport(startDate, endDate, "schepersonamount");
		} else if ("month".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.monymfwExport(startDate, endDate, "schepersonamount");
		} else if ("year".equalsIgnoreCase(status)) {
			list = nlcStatisticServicex.yearymfwExport(startDate, endDate, "schepersonamount");
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("个人页面访问量");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("时间");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<YmfwPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				YmfwPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getTime());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "个人页面访问量.xls";
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
	 * 个人页面访问量详情的table
	 */
	@RequestMapping("/personDetailYmfwTable")
	public String personDetailYmfwTable(Model model, Date startDate, Date endDate, String status) {
		List<PersonPo> resultList = nlcStatisticServicex.personDetailtableData(startDate, endDate, status);
		model.addAttribute("resultList", resultList);
		return "statisticx/person/xztable";
	}
	
	/**
	 * 个人页面详情访问量的export
	 */
	@RequestMapping("/personDetailYmfwExport")
	public void personDetailYmfwExport(HttpServletRequest request, HttpServletResponse response, Date startDate,
			Date endDate, String status) {
		List<PersonPo> list = nlcStatisticServicex.personDetailtableData(startDate, endDate, status);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("个人页面访问量详情");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("页面");
		headRow.createCell(1).setCellValue("pv");
		headRow.createCell(2).setCellValue("uv");

		if (null != list && list.size() > 0) {
			Iterator<PersonPo> iterator = list.iterator();
			while (iterator.hasNext()) {
				PersonPo detail = iterator.next();
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(detail.getName());
				dataRow.createCell(1).setCellValue(detail.getPv());
				dataRow.createCell(2).setCellValue(detail.getUv());
			}
		}

		String filename = "个人页面访问量详情.xls";
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
