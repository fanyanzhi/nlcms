package cn.gov.nlc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.service.NlcadminService;

@Controller
@RequestMapping("/para")
public class ParallelController {

	@Autowired
	private NlcadminService nlcadminService;
	
	@RequestMapping("/check")
	@ResponseBody
	public void checkPool(@RequestBody Nlcadmin nlcadmin) {
		nlcadminService.ttt(nlcadmin);
	}
}
