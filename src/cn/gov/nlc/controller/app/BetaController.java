package cn.gov.nlc.controller.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.PropertiesUtil;

@Controller
public class BetaController {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.BetaController.class);
	private static String alephUrl = PropertiesUtil.getPropValue("properties/configuration.properties", "aleph");

	@RequestMapping("/beta/update")
	@ResponseBody
	public String updateTest() throws IOException, DocumentException {
		String loginaccount = "8888888516673467";
		String borid = "LSX841011";
		
		
		String rdInfoUrl = alephUrl + "/X?op=bor-info-nlc&library=NLC50&bor_id=" + loginaccount + "&verification=123456"
				+ "&user_name=www-app&user_password=pwdapp";
		String content = "";
		URLConnection connection;
		URL url;
		try {
			url = new URL(rdInfoUrl);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/xml");
			String sLine = "";
			InputStream l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sLine = l_reader.readLine()) != null) {
				content += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		StringBuffer ressb = new StringBuffer();
		ressb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		ressb.append("<p-file-20>");
		ressb.append("<patron-record>");

		Document doc = DocumentHelper.parseText(content);
		Element rootx = doc.getRootElement();
		Iterator iterator = rootx.elementIterator();
		while (iterator.hasNext()) {
			Element item = (Element) iterator.next();
			String elementName = item.getName();
			if (elementName.indexOf("z3") > -1) {
				if (elementName.indexOf("z303") > -1) {
					StringBuffer z303sb = new StringBuffer(item.asXML());
					String ins = "<match-id-type>00</match-id-type><match-id>" + borid
							+ "</match-id>";
					z303sb.insert(6, ins);
					ressb.append(z303sb.toString());
				} else {
					StringBuffer zsb = new StringBuffer(item.asXML());
					/*String ins = "<record-action>A</record-action>";
					zsb.insert(6, ins);*/
					ressb.append(zsb.toString());
				}
			}
		}

		int lastIndexOf = ressb.lastIndexOf("</z305>");
		String conins = "<z305><record-action>A</record-action><z305-id>" + borid
				+ "</z305-id><z305-sub-library>WJCN</z305-sub-library></z305>";
		ressb.insert(lastIndexOf + 7, conins);
		ressb.append("</patron-record>");
		ressb.append("</p-file-20>");

		String res = ressb.toString();
		String _url = alephUrl + "/X";
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
		jsonparam.put("op", "update-bor");
		jsonparam.put("user_name", "www-app");
		jsonparam.put("user_password", "pwdapp");
		jsonparam.put("LIBRARY", "NLC50");
		jsonparam.put("UPDATE_FLAG", "Y");
		jsonparam.put("XML_FULL_REQ", res);
		String resstr = Common.sendPostForm(_url, jsonparam);
		logger.info("返回的信息：" + resstr);
		return resstr;
	}
	
	@RequestMapping("/beta/update12")
	@ResponseBody
	public String updateTest12() throws IOException, DocumentException {
		String loginaccount = "8888888516673467";
		String borid = "LSX841011";

		StringBuffer ressb = new StringBuffer();
		ressb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		ressb.append("<p-file-20>");
		ressb.append("<patron-record>");
		ressb.append("<z303>");
		ressb.append("<record-action>X</record-action>");
		ressb.append("<match-id-type>00</match-id-type>");
		ressb.append("<match-id>" + borid + "</match-id>");
		ressb.append("</z303>");
		ressb.append("<z305>");
		ressb.append("<record-action>A</record-action>");
		ressb.append("<z305-id>" + borid + "</z305-id>");
		ressb.append("<z305-sub-library>WJCN</z305-sub-library>");
		ressb.append("<z305-bor-type>staff</z305-bor-type>");
		ressb.append("<z305-bor-status>21</z305-bor-status>");
		ressb.append("<z305-registration-date>00000000</z305-registration-date>");
		ressb.append("<z305-expiry-date>20170623</z305-expiry-date>");
		ressb.append("</z305>");
		ressb.append("</patron-record>");
		ressb.append("</p-file-20>");
		
		
		String res = ressb.toString();
		String _url = alephUrl + "/X";
		com.alibaba.fastjson.JSONObject jsonparam = new com.alibaba.fastjson.JSONObject();
		jsonparam.put("op", "update-bor");
		jsonparam.put("user_name", "www-app");
		jsonparam.put("user_password", "pwdapp");
		jsonparam.put("LIBRARY", "NLC50");
		jsonparam.put("UPDATE_FLAG", "Y");
		jsonparam.put("XML_FULL_REQ", res);
		String resstr = Common.sendPostForm(_url, jsonparam);
		logger.info("返回的信息：" + resstr);
		return resstr;
	}
	
}
