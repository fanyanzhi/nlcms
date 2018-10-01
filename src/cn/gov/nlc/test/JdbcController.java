package cn.gov.nlc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class JdbcController {

	/*@Autowired
	private JdbcService jdbcService;
	
	*//**
	 * 通过jdbc调用无返回值的存储过程
	 * @return
	 *//*
	@RequestMapping("/show1")
	@ResponseBody
	public String show() {
		System.out.println(11111);
		jdbcService.show1();
		return "success";
	}
	
	*//**
	 * 通过jdbc调用无返回值的存储过程
	 * @return
	 *//*
	@RequestMapping("/show2")
	@ResponseBody
	public String show2() {
		System.out.println(22222);
		jdbcService.show2();
		return "success";
	}*/
	
	@RequestMapping("/sss")
	public String sss() {
//		return "redirect:/test/sx?name=111%0D%0ASet-Cookie:+SID=123321";
		return "redirect:/test/sx?name=111%0D%0A%0D%0A想要显示";
	}
	
	@RequestMapping("/sx")
	public String sx() {
		return "t1";
	}
}
