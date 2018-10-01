package cn.gov.nlc.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class ProxyHttpClient {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.util.ProxyHttpClient.class);
	private static final String httpproxy = PropertiesUtils.getPropertyValue("httpproxy");
	private static final String httpproxyport = PropertiesUtils.getPropertyValue("httpproxyport");

	public static String sendGet(String url) {
		String str = "";
		HttpClient httpClient = new HttpClient();
		GetMethod method = null;
		try {
			int port = new Integer(httpproxyport);
			httpClient.getHostConfiguration().setProxy(httpproxy, port);
			method = new GetMethod(url);
			int statusCode = httpClient.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				logger.info("Method failed code=" + statusCode + ": " + method.getStatusLine());

			} else {
				str = new String(method.getResponseBody(), "utf-8");
//				logger.info(str);
			}
		} catch (Exception e) {
			logger.error("【proxyhttpclient】" + e);
		} finally {
			if (null != method)
				method.releaseConnection();
		}
		return str;
	}
	
	public static String sendGetWithHeaders(String url, Map<String, String> headers) {
		String str = "";
		HttpClient httpClient = new HttpClient();
		GetMethod method = null;
		try {
			int port = new Integer(httpproxyport);
			httpClient.getHostConfiguration().setProxy(httpproxy, port);
			method = new GetMethod(url);
			if (headers != null && headers.size() > 0) {
				Set<String> keys = headers.keySet();
				for (Iterator<String> i = keys.iterator(); i.hasNext();) {
					String key = (String) i.next();
					method.addRequestHeader(key, headers.get(key));
				}
			}
			
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.info("Method failed code=" + statusCode + ": " + method.getStatusLine());

			} else {
				str = new String(method.getResponseBody(), "utf-8");
			}
		} catch (Exception e) {
			logger.error("【proxyhttpclient】" + e);
		} finally {
			if (null != method)
				method.releaseConnection();
		}
		return str;
	}
}
