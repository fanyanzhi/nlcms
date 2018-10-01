package cn.gov.nlc.controller.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import cn.gov.nlc.pojo.Delayorderinfo;
import cn.gov.nlc.pojo.Orderinfo;
import cn.gov.nlc.service.DelayorderinfoService;
import cn.gov.nlc.service.OrderinfoService;
import cn.gov.nlc.util.PropertiesUtils;
import net.sf.json.JSONObject;

@Controller
public class AsynNotifyAlipayController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.AsynNotifyAlipayController.class);
	private static final String ACCOUNT = PropertiesUtils.getPropertyValue("account"); // 商户的seller_id
	private static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	private static final String aliPayAppID = PropertiesUtils.getPropertyValue("aliPayAppID");

	@Autowired
	private DelayorderinfoService delayorderinfoService;
	@Autowired
	private OrderinfoService orderinfoService;
	
	@RequestMapping("/asynnotify")
	@ResponseBody
	public String asynNotify(HttpServletRequest request) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "utf-8");
				params.put(name, valueStr);
			}
			boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8", "RSA");
			if (flag) {
				String outtradeno = params.get("out_trade_no"); // 原订单号需要根据它去数据库中取得订单信息，然后和异步通知的信息作对比
				Delayorderinfo delayorderinfo = delayorderinfoService.getByOrderno(outtradeno);
				if(null == delayorderinfo) {
					return "failure";
				}
				
				String totalamount = params.get("total_amount");// 单位为元
				BigDecimal sum = delayorderinfo.getSum();
				try {
					int compareTo = sum.compareTo(new BigDecimal(totalamount));
					if (compareTo > 0) {
						return "failure";
					}
				} catch (Exception e) {
					return "failure";
				}
				
				String sellerid = params.get("seller_id");
				if (!ACCOUNT.equals(sellerid)) {
					return "failure";
				}
				String appid = params.get("app_id");
				if (!aliPayAppID.equals(appid)) {
					return "failure";
				}
				
				String tradestatus = params.get("trade_status");
				if ("TRADE_SUCCESS".equals(tradestatus) || "TRADE_FINISHED".equals(tradestatus)) {
					String loginaccount = delayorderinfo.getLoginaccount();
					String borid = delayorderinfo.getBorid();
					String ip = delayorderinfo.getIp();
					String tradeno = params.get("trade_no"); 	//支付宝交易号
					
					try {
						delayorderinfoService.updateStatus(outtradeno, tradeno);
					} catch (Exception e) {
						logger.error(e.getMessage() + outtradeno + "本地数据库滞纳金回写更新状态出错");
						return "failure";
					}
					
					JSONObject json = delayorderinfoService.updateDelayCash(loginaccount, borid, ip, outtradeno, tradeno, sum.toPlainString());
					logger.info("【滞纳金异步更新返回的json数据：】" + json);
					String result = json.getString("result");
					if("true".equals(result)) {
						logger.info("【滞纳金异步更新成功】");
						return "success";
					}
				}
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return "failure";
	}
	
	/**
	 * 押金的异步回写
	 * @param request
	 * @return
	 */
	@RequestMapping("/pledgeAsynnotify")
	@ResponseBody
	public String pledgeAsynnotify(HttpServletRequest request) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "utf-8");
				params.put(name, valueStr);
			}
			boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8", "RSA");
			if (flag) {
				String outtradeno = params.get("out_trade_no"); // 原订单号需要根据它去数据库中取得订单信息，然后和异步通知的信息作对比
				Orderinfo orderinfo = orderinfoService.selectByOrderno(outtradeno);
				if(null == orderinfo) {
					return "failure";
				}
				
				String totalamount = params.get("total_amount");// 单位为元
				BigDecimal sum = orderinfo.getSum();
				try {
					int compareTo = sum.compareTo(new BigDecimal(totalamount));
					if (compareTo > 0) {
						return "failure";
					}
				} catch (Exception e) {
					return "failure";
				}
				
				String sellerid = params.get("seller_id");
				if (!ACCOUNT.equals(sellerid)) {
					return "failure";
				}
				String appid = params.get("app_id");
				if (!aliPayAppID.equals(appid)) {
					return "failure";
				}
				
				String tradestatus = params.get("trade_status");
				if ("TRADE_SUCCESS".equals(tradestatus) || "TRADE_FINISHED".equals(tradestatus)) {
					
					String tradeno = params.get("trade_no"); 	//支付宝交易号
					orderinfo.setTradeno(tradeno);
					orderinfo.setStatus((byte) 1);
					try {
						orderinfoService.updateByPrimaryKeySelective(orderinfo);
					} catch (Exception e) {
						logger.error(e.getMessage() + outtradeno + "本地数据库押金回写更新状态出错");
						return "failure";
					}
					
					JSONObject json = orderinfoService.updateAlephPledge(orderinfo);
					logger.info("【押金异步更新返回的json数据：】" + json);
					String result = json.getString("result");
					if("true".equals(result)) {
						logger.info("【押金异步更新成功】");
						return "success";
					}
				}
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return "failure";
	}
}
