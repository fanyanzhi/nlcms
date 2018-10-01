package cn.gov.nlc.controller.app;

import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.aleph.Aleph;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.service.DelayorderinfoService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.util.AliPay;
import cn.gov.nlc.util.Common;
import net.sf.json.JSONObject;

/**
 * 滞纳金
 * 
 * @author DAYI
 *
 */
@Controller
@RequestMapping("/app")
public class DelayCashController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.DelayCashController.class);

	@Autowired
	private AliPay aliPay;
	@Autowired
	private DelayorderinfoService delayorderinfoService;

	/**
	 * 详细的未缴纳的滞纳金记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/delaycashList")
	@ResponseBody
	public JSONObject delayCashDetail(HttpServletRequest request) {
		try {
			String loginAccount = request.getAttribute("loginAccount").toString();
			JSONObject resjson = delayorderinfoService.delayCashDetail(loginAccount);
			logger.info("【滞纳金】" + resjson);
			return resjson;
		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("result", false);
			result.put("message", "server wrong");
			return result;
		}
	}

	/**
	 * 滞纳金生成订单信息
	 * 
	 * @return
	 */
	@RequestMapping("/user/delayOrderinfo")
	@ResponseBody
	public JSONObject getDelayOrderInfo(HttpServletRequest request, @RequestParam String sum) {
		JSONObject result = new JSONObject();

		String loginAccount = request.getAttribute("loginAccount").toString();
		String borId = request.getAttribute("borId").toString();
		String ip = Common.getClientIP(request);

		try {
			// 根据账户去aleph查查滞纳金总数对不对
			String alephsumstr = Aleph.delayCash(loginAccount);
			BigDecimal appsum = new BigDecimal(sum);
			BigDecimal alephsum = new BigDecimal(alephsumstr);

			if (alephsum.compareTo(new BigDecimal("0")) == 0) {
				result.put("result", true);
				result.put("payment", 1);
				return result; // 不欠费
			}

			int res = appsum.compareTo(alephsum);
			if (res < 0) {
				// 交的钱少了
				result.put("result", false);
				result.put("message", "amount discrepancy"); // 金额不符
				return result;
			}

			// 加签
			String orderInfo = aliPay.createDelayOrderInfo(loginAccount, sum, borId, ip);
			if (StringUtils.isNotBlank(orderInfo)) {
				result.put("result", true);
				result.put("payment", 0);
				result.put("orderinfo", orderInfo);
			} else {
				result.put("result", false);
				result.put("message", "create orderinfo fail");
			}

		} catch (Exception e) {
			result.put("result", false);
			result.put("message", "server wrong");
		}

		return result;
	}

	/**
	 * 滞纳金回写，写入数据库delayorderinfo表、sysmessage站内信、更新aleph的状态
	 * 
	 * orderno商户订单号， tradeno支付宝订单号，sum的总金额
	 */
	//@RequestMapping("/user/updateDelayOrder")
	//@ResponseBody
	public JSONObject getOrderInfo(HttpServletRequest request, @RequestParam String orderno,
			@RequestParam String tradeno, @RequestParam String sum) {
		try {
			JSONObject result = new JSONObject();

			String loginAccount = request.getAttribute("loginAccount").toString();
			String borId = request.getAttribute("borId").toString();
			String ip = Common.getClientIP(request);
			
			// 根据账户去aleph查查滞纳金总数对不对
			String alephsumstr = Aleph.delayCash(loginAccount);
			BigDecimal appsum = new BigDecimal(sum);
			BigDecimal alephsum = new BigDecimal(alephsumstr);
			int res = appsum.compareTo(alephsum);
			if (res < 0) {
				// 交的钱少了
				result.put("result", false);
				result.put("message", "amount discrepancy"); // 金额不符
				return result;
			}

			try {
				delayorderinfoService.updateStatus(orderno, tradeno);
			} catch (Exception e) {
				logger.error(e.getMessage() + orderno + "本地数据库滞纳金回写更新状态出错");
			}

			JSONObject json = delayorderinfoService.updateDelayCash(loginAccount, borId, ip, orderno, tradeno, sum);
			return json;
		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("result", false);
			result.put("message", "server wrong");
			return result;
		}
	}
}
