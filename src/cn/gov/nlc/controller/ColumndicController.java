package cn.gov.nlc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Columndic;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.service.ColumndicService;
import cn.gov.nlc.service.NlcadminlogService;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Controller
@RequestMapping("/session/columndic")
public class ColumndicController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.ColumndicController.class);

	@Autowired
	private ColumndicService columndicService;
	@Autowired
	private NlcadminlogService nlcadminlogService;

	/**
	 * 展示栏目字典
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<Columndic> getList() {
		List<Columndic> list = columndicService.getList();
		return list;
	}

	/**
	 * 讲座预告栏目
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
		nlcadminlog.setOperate("查看讲座预告栏目");
		nlcadminlogService.insertNlcadminlog(nlcadminlog);
		return "traileritem/view";
	}

	/**
	 * 讲座预告栏目datagrid的数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/dlist")
	@ResponseBody
	public EasyUiDataGridResult getItemList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		EasyUiDataGridResult list = columndicService.getItemList(page, rows);
		return list;
	}

	/**
	 * 根据id删除讲座预告栏目的信息，单个删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		int result = columndicService.deleteSingleById(id);

		if (result == 0) { // 删除失败
			return "{result:false}";
		} else { // 删除成功
			return "{result:true}";
		}
	}

	/**
	 * 跳转到讲座预告栏目
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "traileritem/add";
	}

	/**
	 * 添加讲座预告栏目
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public String addItem(Columndic columndic, HttpSession session) {
		try {
			columndic.setColumnid(UUIDGenerator.getUUID());
			columndicService.insertItem(columndic);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}

	/**
	 * 跳转到讲座预告栏目页面
	 */
	@RequestMapping("/update")
	public String update(Integer id, Model model) {
		Columndic columndic = columndicService.selectByPrimaryKey(id);
		model.addAttribute("columndic", columndic);
		return "traileritem/update";
	}

	/**
	 * 修改讲座预告栏目
	 */
	@RequestMapping("/updateColumn")
	@ResponseBody
	public String updateColumn(Columndic columndic) {
		try {
			columndicService.updateColumn(columndic);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "{result:false}";
		}
		return "{result:true}";
	}

	/**
	 * 根据id查询讲座预告栏目在讲座预告中是否有使用过
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/checktrail/{id}")
	@ResponseBody
	public String checkTrail(@PathVariable Integer id) {
		try {
			boolean b = columndicService.checkTrail(id); // 当有使用过为true,没有使用过为false
			return "{result:" + b + "}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{result:true}";
		}
	}
}
