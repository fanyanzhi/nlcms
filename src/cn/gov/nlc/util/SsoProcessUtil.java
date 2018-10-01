package cn.gov.nlc.util;

import com.jit.client.sp.AssertClient;
import com.jit.client.sp.bean.AccessResult;
import com.jit.client.sp.bean.LoginResult;
import com.jit.client.sp.model.LoginProcessUtil;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public class SsoProcessUtil {
	
	/**
	 * 判断ticket，并刷新ticket信息
	 * @param ticket
	 * @return
	 */
	public static String chkTicket(String ticket) {
		AccessResult accessResult = new AssertClient().requestAssertion(ticket);
		JSON json = null;
		String returnObj = "";
		if (accessResult.getResult() != 200) {
			json = JSONObject.fromObject(accessResult);
		} else {
			LoginResult loginResult = LoginProcessUtil.getLoginResult(accessResult, ticket);
			json = JSONObject.fromObject(loginResult);
		}
		returnObj = json.toString();
		return returnObj;

	}
}
