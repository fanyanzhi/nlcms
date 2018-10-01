package cn.gov.nlc.controller.base;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Menu;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.service.MenuService;

@Controller
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/session/menu")
	@ResponseBody
	public List<Menu> menu(HttpSession session) {
		Nlcadmin admin = (Nlcadmin) session.getAttribute("LoginObj");
		int role = admin.getRole();
		String moduleid = admin.getModuleid();
		List<Menu> list = menuService.getAll(role, moduleid);
		return list;
	}

}
