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
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.NlcnoticeExt;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.service.NlcnoticeService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.SyswindowService;
import cn.gov.nlc.util.FileUtils;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/session/notice")
public class NlcnoticeController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.NlcnoticeController.class);

	@Autowired
	private NlcnoticeService nlcnoticeService;
	@Autowired
	private NlcadminlogService nlcadminlogService;
	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private SyswindowService syswindowService;

	/**
	 * 跳转到新闻管理页面
	 * 
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
		nlcadminlog.setOperate("查看公告管理");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "notice/view";
	}

	/**
	 * 新闻管理展示页面，包括高级查询
	 * 
	 * @param page
	 * @param rows
	 * @param nlcnoticeExt
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGridResult getUserList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows, NlcnoticeExt nlcnoticeExt) {
		EasyUiDataGridResult result = nlcnoticeService.getNoticeList(page, rows, nlcnoticeExt);
		return result;
	}

	/**
	 * 根据id删除公告，单个删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable String id) {
		int result = nlcnoticeService.deleteSingleById(id);

		if (result == 0) { // 删除失败
			return "{result:false}";
		} else { // 删除成功
			return "{result:true}";
		}
	}

	/**
	 * 置顶公告
	 * @param noticeid
	 * @return
	 */
	@RequestMapping("/settop/{noticeid}")
	@ResponseBody
	public String settop(@PathVariable String noticeid) {
		try{
			nlcnoticeService.settop(noticeid);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 取消置顶公告
	 * @param noticeid
	 * @return
	 */
	@RequestMapping("/cantop/{noticeid}")
	@ResponseBody
	public String cantop(@PathVariable String noticeid) {
		try{
			nlcnoticeService.cantop(noticeid);
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	
	/**
	 * 删除多个
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public String deleteAll(String ids) {
		if (StringUtils.isBlank(ids)) {
			return "{result:false}";
		}

		String[] strArr = ids.split(",");
		for (String str : strArr) {
			nlcnoticeService.deleteSingleById(str);
		}
		return "{result:true}";
	}

	/**
	 * 导出excel
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		List<Nlcnotice> list = nlcnoticeService.getAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("公告管理");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("上传时间");
		headRow.createCell(1).setCellValue("公告标题");
		headRow.createCell(2).setCellValue("上传人");
		headRow.createCell(3).setCellValue("内容发布时间");
		headRow.createCell(4).setCellValue("状态");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (null != list && list.size() > 0) {
			for (Nlcnotice nlcnotice : list) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

				Date subTime = nlcnotice.getSubTime();
				if (null != subTime) {
					String strTime = sdf1.format(subTime);
					dataRow.createCell(0).setCellValue(strTime);
				} else {
					dataRow.createCell(0).setCellValue("");
				}

				dataRow.createCell(1).setCellValue(nlcnotice.getTitle());
				dataRow.createCell(2).setCellValue(nlcnotice.getSubPerson());

				Date pubTime = nlcnotice.getPubTime();
				if (null != pubTime) {
					String strTime2 = sdf1.format(pubTime);
					dataRow.createCell(3).setCellValue(strTime2);
				} else {
					dataRow.createCell(3).setCellValue("");
				}

				dataRow.createCell(4).setCellValue(nlcnotice.getStatus());
			}
		}

		String filename = "公告管理.xls";
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
	 * 跳转到添加公告页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "notice/add";
	}

	/**
	 * 添加公告
	 * 
	 * @param nlcnotice
	 * @return
	 */
	@RequestMapping("/saveNotice")
	@ResponseBody
	public String saveNotice(Nlcnotice nlcnotice, HttpSession session) {
		try {
			Nlcadmin nlcadmin = (Nlcadmin) session.getAttribute("LoginObj");
			nlcnotice.setSubPerson(nlcadmin.getUsername()); // 上传人
			nlcnotice.setSubTime(new Date()); // 上传时间是当前时间
			nlcnotice.setSource("原创");
			nlcnotice.setPraisecount(0);
			nlcnotice.setCollectcount(0);
			String noticeid = UUIDGenerator.getUUID();
			nlcnotice.setNoticeid(noticeid);
			nlcnotice.setTops(0);

			if ("已发布".equals(nlcnotice.getStatus())) { // 已发布状态，才插入站内信、弹窗表
				sysmessageService.insertMessageThfour(nlcnotice.getPushmethod(), Byte.valueOf("0"),
						"", nlcnotice.getNoticeid(), new Date(), nlcadmin.getUsername(), nlcnotice.getTitle(), "notice");
				syswindowService.insertWindowThfour(nlcnotice.getPushmethod(), Byte.valueOf("5"), nlcnotice.getTitle(),
						nlcnotice.getNoticeid(), new Date(), nlcadmin.getUsername());
				nlcnotice.setBkpbtime(new Date());
			}
			nlcnoticeService.insertNotice(nlcnotice);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}

	/**
	 * 公告修改页面
	 */
	@RequestMapping("/update")
	public String update(String id, Model model) {
		Nlcnotice nlcnotice = nlcnoticeService.selectByPrimaryKey(id);
		model.addAttribute("nlcnotice", nlcnotice);
		return "notice/update";
	}

	/**
	 * 修改公告
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateNotice")
	@ResponseBody
	public String updateNotice(HttpServletRequest request) {
		try {
			Map<String, String[]> parameterMap = request.getParameterMap();
			nlcnoticeService.updateNotice(parameterMap);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}

	/**
	 * 在公告管理中的预览
	 */
	@RequestMapping("/itemPreview")
	public String itemPreview(String noticeid, Model model) {
		model.addAttribute("noticeid", noticeid);
		return "notice/itemPreview";
	}

	/**
	 * 通过noticeid得到notice
	 */
	@RequestMapping("/getByNoticeid")
	@ResponseBody
	public Nlcnotice getByNoticeid(String noticeid) {
		Nlcnotice nlcnotice = nlcnoticeService.selectByPrimaryKey(noticeid);
		return nlcnotice;
	}

	/**
	 * 新增、编辑页面的预览
	 */
	@RequestMapping("/addPreview")
	public String addPreview() {
		return "notice/addPreview";
	}

	/**
	 * 公告发布 status 1发布，0取消
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public String shelf(String noticeid, String status) {
		try {
			nlcnoticeService.publish(noticeid, status);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}
	
	/**
	 * 拉取公告
	 */
	@RequestMapping("/pull")
	@ResponseBody
	public JSONObject pull(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try{
			result = nlcnoticeService.pull();
		}catch(Exception e){
			logger.info(e.getMessage());
			result.put("result", false);
			result.put("message", "服务器错误");
		}
		return result;
	}
}
