package cn.gov.nlc.controller.app;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.gov.nlc.service.OrderinfoService;
import cn.gov.nlc.util.AliPay;

/**
 * 
 * @author DAYI
 *
 */
@Controller
@RequestMapping("/app")
public class PledgeCashController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.PledgeCashController.class);
	
	@Autowired
	private AliPay aliPay;
	@Autowired
	private OrderinfoService orderinfoService;
	
	
	
}
