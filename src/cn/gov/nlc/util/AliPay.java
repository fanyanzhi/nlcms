package cn.gov.nlc.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gov.nlc.pojo.Delayorderinfo;
import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.pojo.Orderinfo;
import cn.gov.nlc.service.DelayorderinfoService;
import cn.gov.nlc.service.NlcuserService;
import cn.gov.nlc.service.OrderinfoService;

@Component
public class AliPay {

	private static final String aliPayAppID = PropertiesUtils.getPropertyValue("aliPayAppID");
	private static final String product_code = PropertiesUtils.getPropertyValue("product_code");

	@Autowired
	NlcuserService nlcuserService;

	private static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANHPn3JIUT6OYwAl"
			+ "nSFTBYOHXw7O9JLVo87bKcyDOlYYkXyBpe+jkAxiZTmgEoPT4k5BbSOpp5tFc35+"
			+ "zXq8+R3zGZEAkt+mSD+oS+aF7FuNORBSfWpqTwKS3neKqD/ZxXRa4ZtG1slRrDPh"
			+ "lZU0uPBPQGS/UHJQHZBsSFpthuOlAgMBAAECgYBiCxSmUdbtVZo7ulf395TPBtZL"
			+ "1DgD2aOniVC6CeSb0PVDak5Bcnxg3SYSTJP6WGwbR4WZsbDp5QV4agbZQS9jj4mP"
			+ "qLJJxfGZaNF4WSOTOyGVFuobfwqhBTqs4n9+fhqKDyVWxIlo76+/BkctxOdDnlan"
			+ "1KQbaUL+URl4/P+hDQJBAPSHz68GU8fog2bJbL0fIGa9tQSqRB6wMeo1FbtTYYBt"
			+ "3rdlb3zHynqzb77dLiAQElxoVtuI9aadp55si6fGp88CQQDbputBIcuwZ1j+1nvv"
			+ "mww7kLZoFnRNUxjZlQbJCKy3LHdsPGICDAHdbXzj3a0FdDmPauPoT3lGB8QwrJ6P"
			+ "pSZLAkEAzQdKpbAic02LT5W37OK6YirSKnUw5KJLV9rmu8hvyiVVRmRwlClYpoTp"
			+ "Pxlo4U1Yd86sdSpi/i7WKklM14IDlQJBAKth9wcv1l1Clgbo1XTj1kyXoite8cnv"
			+ "f2EtzUiIczLNCDU0fAg61C2wIxUsPrcVs1+6uC7n66j/oE2WMnXEFzcCQQCOoPx5"
			+ "8x2rWTQFABA8uczpBji82mxHaaor7U0bqXJSZ0EnBpbc40g69aNBXujX5pFIWRHs" + "KXoDLyNcz0wEBG5D";

	@Autowired
	OrderinfoService orderinfoService;
	@Autowired
	private DelayorderinfoService delayorderinfoService;

	/**
	 * 押金订单信息
	 * 
	 * @param loginAccount
	 * @param type
	 * @return
	 */
	public String createOrderInfo(String loginAccount, String type, String borId) {
		String description = "";
		String sum = "";
		if ("national".equalsIgnoreCase(type)) {
			description = "中文押金";
			sum = PropertiesUtils.getPropertyValue("national");
		} else if ("foreign".equalsIgnoreCase(type)) {
			description = "外文押金";
			sum = PropertiesUtils.getPropertyValue("foreign");
		}
		String orderno = getOutTradeNo(loginAccount);
		String biz_content = "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""
				+ sum + "\",\"subject\":\"押金\",\"body\":\"" + description + "\",\"out_trade_no\":\"" + orderno + "\"}";
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(aliPayAppID, biz_content);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
		// 需要对订单信息加签
		String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
		final String orderInfo = orderParam + "&" + sign;
		
		Nlcuser nlcuser = nlcuserService.getByAccount(loginAccount);
		Orderinfo orderinfo = new Orderinfo();
		orderinfo.setLoginaccount(loginAccount);
		orderinfo.setName(nlcuser.getName());
		orderinfo.setCardno(nlcuser.getCardno());
		orderinfo.setOrderno(orderno);
		orderinfo.setBorid(borId);
		orderinfo.setType(type);
		orderinfo.setStatus((byte) 0);
		orderinfo.setTime(new Date());
		orderinfo.setSum(new BigDecimal(sum));
		
		try {
			orderinfoService.insertOrderinfo(orderinfo);
		} catch (Exception e) {
			return "";
		}
		return orderInfo;
	}

	/**
	 * 要求外部订单号必须唯一。
	 * 
	 * @return
	 */
	public static String getOutTradeNo(String loginAccount) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		Date date = new Date();
		return loginAccount.concat(format.format(date));
	}

	// 获取当前时间
	public static String getData() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String data = formatter.format(curDate);
		return data;
	}
	
	/**
	 * 滞纳金订单信息
	 * 
	 * @param loginAccount
	 * @return
	 */
	public String createDelayOrderInfo(String loginAccount, String sum, String borId, String ip) {
		String orderno = getOutTradeNo(loginAccount);
		String biz_content = "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""
				+ sum + "\",\"subject\":\"滞纳金\",\"body\":\"" + " " + "\",\"out_trade_no\":\"" + orderno + "\"}";
		Map<String, String> params = OrderInfoUtil2_0.buildDelayOrderParamMap(aliPayAppID, biz_content);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
		// 需要对订单信息加签
		String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
		final String orderInfo = orderParam + "&" + sign;
		
		Nlcuser nlcuser = nlcuserService.getByAccount(loginAccount);
		Delayorderinfo pojo = new Delayorderinfo();
		pojo.setLoginaccount(loginAccount);
		pojo.setName(nlcuser.getName());
		pojo.setCardno(nlcuser.getCardno());
		pojo.setOrderno(orderno);
		pojo.setSum(new BigDecimal(sum));
		pojo.setStatus((byte) 0);
		pojo.setTime(new Date());
		pojo.setBorid(borId);
		pojo.setIp(ip);
		
		try {
			delayorderinfoService.insert(pojo);
		} catch (Exception e) {
			return "";
		}
		
		return orderInfo;
	}
}
