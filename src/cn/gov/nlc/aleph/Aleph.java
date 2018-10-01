package cn.gov.nlc.aleph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import cn.gov.nlc.util.PropertiesUtil;
import cn.gov.nlc.vo.Owe;
import cn.gov.nlc.aleph.Json;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Aleph {

	private static String alephUrl = PropertiesUtil.getPropValue("properties/configuration.properties", "aleph");
	public static final String Request_Pre_Day = "Request_Pre_Day";
	public static final String Waiting_in_queue = "Waiting in queue";
	public static final String In_processz38_status = "In processz38-status";
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.aleph.Aleph.class);

	//获取外借信息
	public static JSONObject outBorrowInfo(String borId, String barcode, String staff_id, String station_id) {
		JSONObject json = new JSONObject();
		if(StringUtils.isBlank(borId) || StringUtils.isBlank(barcode)) {
			json.put("result", false);
			json.put("data", "parameter error");
			return json;
		}
		
		String value = "";
		String rdInfoUrl = alephUrl+"/X?op=lcl_loan&bor_id="+borId+"&barcode="+barcode+"&staff_id="+staff_id+"&station_id="+station_id
				+ "&user_name=www-app&user_password=pwdapp&con_lng=chi";
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
				value += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			json.put("result", false);
			json.put("data", "aleph error");
			return json;

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			json.put("result", false);
			json.put("data", "aleph error");
			return json;
		}
		
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	// 在借信息
	public static JSON borinfo(String bor_id) {
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		JSON j = new JSONArray();

		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + bor_id
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp&con_lng=chi";
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
				value += sLine + "\r\n";
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			return j;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return j;
		}

		Document doc = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Iterator itemIter = root.elementIterator("item-l");
			while (itemIter.hasNext()) {
				Element item = (Element) itemIter.next();
				Map<String, String> map = new HashMap<String, String>();
				Element z36 = item.element("z36");
				String z36_doc_number = z36.elementText("z36-doc-number");
				String loan_date = z36.elementText("z36-loan-date");
				String loan_hour = z36.elementText("z36-loan-hour");
				String due_date = z36.elementText("z36-due-date"); // 到期日期
				String due_hour = z36.elementText("z36-due-hour");// 到期时间
				String no_renew = z36.elementText("z36-no-renewal");

				Element z30 = item.element("z30");
				String z30_barcode = z30.elementText("z30-barcode");
				String call_no = z30.elementText("z30-call-no");
				// String library = z30.elementText("z30-sub-library");
				String tag = z30.elementText("xxx-tag");
				Element z13 = item.element("z13");
				String year = z13.elementText("z13-year");
				String author = z13.elementText("z13-author");
				String title = z13.elementText("z13-title");
				String imprint = z13.elementText("z13-imprint");
				Date mydue = sdf2.parse(due_date);
				due_date = sdf1.format(mydue);
				Date myloandue = sdf2.parse(loan_date);
				loan_date = sdf1.format(myloandue);

				map.put("due_date", due_date == null ? "" : due_date);
				map.put("due_hour", due_hour == null ? "" : due_hour);
				map.put("loan_date", loan_date == null ? "" : loan_date);
				map.put("loan_hour", loan_hour == null ? "" : loan_hour);
				map.put("no_renewal", no_renew == null ? "" : no_renew);
				map.put("call_no", call_no == null ? "" : call_no);
				map.put("tag", tag == null ? "" : tag);
				map.put("year", year == null ? "" : year);
				map.put("author", author == null ? "" : author);
				map.put("title", title == null ? "" : title);
				map.put("imprint", imprint == null ? "" : imprint);
				map.put("xxx_tag", tag == null ? "" : tag);
				map.put("z30_barcode", z30_barcode == null ? "" : z30_barcode);
				map.put("z36_doc_number", z36_doc_number == null ? "" : z36_doc_number);
				// map.put("library", library);
				l.add(map);
			}

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			return j;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return j;
		}

		j = JSONArray.fromObject(l);
		return j;
	}

	/**
	 * 滞纳金记录
	 * 
	 * @param bor_id
	 * @return
	 */
	public static String delayCash(String bor_id) {
		String value = "";

		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + bor_id
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp&con_lng=chi";
		logger.info(rdInfoUrl);
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
				value += sLine + "\r\n";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("aleph链接失败");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("aleph链接失败");
		}
		String balance = "0.0";
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Node n = doc.selectSingleNode("/bor-info/balance");
			if (n != null) {
				balance = n.getText();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("国图返回的xml解析有问题");
		}
		return balance;
	}

	/**
	 * 滞纳金记录
	 * 
	 * @param bor_id
	 * @return
	 */
	public static JSON delayCashDetail(String bor_id) {
		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + bor_id
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp&con_lng=chi";
		logger.info(rdInfoUrl);

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
				value += sLine + "\r\n";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		Document doc = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Iterator itemIter = root.elementIterator("fine");
			while (itemIter.hasNext()) {
				Element item = (Element) itemIter.next();
				Map<String, String> map = new HashMap<String, String>();
				Element z31 = item.element("z31");
				String z31_description = z31.elementText("z31-description");
				String z31_sum = z31.elementText("z31-sum");
				String z31_date = z31.elementText("z31-date");
				String z31_status = z31.elementText("z31-status");
				Element z30 = item.element("z30");
				if (z30 == null) {
					map.put("due_date", z31_date == null ? "" : z31_date);
					map.put("call_no", "");
					map.put("tag", "");
					map.put("year", "");
					map.put("author", "");
					map.put("title", "");
					map.put("imprint", "");
					map.put("xxx_tag", "");
					map.put("z30_barcode", "");
					map.put("z36_doc_number", "");
					map.put("z31_description", z31_description == null ? "" : z31_description);
					map.put("z31_sum", z31_sum == null ? "" : z31_sum);
					map.put("z31_status", z31_status == null ? "" : z31_status);
					l.add(map);
					continue;
				}
				String z30_barcode = z30.elementText("z30-barcode");
				String call_no = z30.elementText("z30-call-no");
				String tag = z30.elementText("xxx-tag");

				Element z13 = item.element("z13");
				String z13_doc_number = z13.elementText("z13-doc-number");
				String year = z13.elementText("z13-year");
				String author = z13.elementText("z13-author");
				String title = z13.elementText("z13-title");
				String imprint = z13.elementText("z13-imprint");
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
				Date mydue = sdf2.parse(z31_date);
				z31_date = sdf1.format(mydue);
				map.put("due_date", z31_date == null ? "" : z31_date);
				map.put("call_no", call_no == null ? "" : call_no);
				map.put("tag", tag == null ? "" : tag);
				map.put("year", year == null ? "" : year);
				map.put("author", author == null ? "" : author);
				map.put("title", title == null ? "" : title);
				map.put("imprint", imprint == null ? "" : imprint);
				map.put("xxx_tag", tag == null ? "" : tag);
				map.put("z30_barcode", z30_barcode == null ? "" : z30_barcode);
				map.put("z36_doc_number", z13_doc_number == null ? "" : z13_doc_number);
				map.put("z31_status", z31_status == null ? "" : z31_status);
				map.put("z31_description", z31_description == null ? "" : z31_description);
				map.put("z31_sum", z31_sum == null ? "" : z31_sum);
				// map.put("library", library);

				l.add(map);
			}

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		JSON j = JSONArray.fromObject(l);
		return j;
	}

	// 预约结果
	public static JSON getHoldBooks(String bor_id) {
		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + bor_id
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp&con_lng=chi";
		logger.info(rdInfoUrl);
		URLConnection connection;
		URL url;
		try {
			url = new URL(rdInfoUrl);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/xml");

			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String sLine = "";

			InputStream l_urlStream = connection.getInputStream();
			// 传说中的三层包装！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sLine = l_reader.readLine()) != null) {
				value += sLine + "\r\n";
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		Document doc = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");

		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Iterator itemIter = root.elementIterator("item-h");
			while (itemIter.hasNext()) {
				Element item = (Element) itemIter.next();
				Map<String, Object> map = new HashMap<String, Object>();
				Element z37 = item.element("z37");
				String z37_doc_number = z37.elementText("z37-doc-number");
				Element z30 = item.element("z30");
				String call_no = z30.elementText("z30-call-no");
				String library = z30.elementText("z30-sub-library");
				String tag = z30.elementText("xxx-tag");
				Element z13 = item.element("z13");
				String year = z13.elementText("z13-year");//
				String author = z13.elementText("z13-author");//
				String title = z13.elementText("z13-title");//
				String z37_end_request_date = z37.elementText("z37-end-request-date");
				String z37_item_sequence = z37.elementText("z37-item-sequence");
				String z37_request_date = z37.elementText("z37-request-date");
				String z37_sequence = z37.elementText("z37-sequence");
				String z37_status = z37.elementText("z37-status");
				String pick_up_loc = z37.elementText("z37-pickup-location");
				map.put("pick_up_loc", pick_up_loc == null ? "" : pick_up_loc);
				map.put("call_no", call_no == null ? "" : call_no);
				map.put("tag", tag == null ? "" : tag);
				map.put("year", year == null ? "" : year);
				map.put("author", author == null ? "" : author);
				map.put("title", title == null ? "" : title);
				map.put("xxx_tag", tag == null ? "" : tag);
				map.put("library", library == null ? "" : library);
				map.put("end_request_date", z37_end_request_date == null ? "" : z37_end_request_date);
				map.put("z37_item_sequence", z37_item_sequence == null ? "" : z37_item_sequence);
				map.put("z37_request_date", z37_request_date == null ? "" : z37_request_date);
				map.put("z37_sequence", z37_sequence == null ? "" : z37_sequence);
				map.put("z37_doc_number", z37_doc_number == null ? "" : z37_doc_number);
				if (Waiting_in_queue.equalsIgnoreCase(z37_status)) {
					String z37_status_sequence = "未到达，等候位置";
					map.put("arrive", false);
					map.put("z37_status_sequence", z37_status_sequence);
				} else if (In_processz38_status.equalsIgnoreCase(z37_status)) {
					String z37_status_sequence = "正在处理中";
					map.put("arrive", false);
					map.put("z37_status_sequence", z37_status_sequence);
				} else if (StringUtils.isBlank(z37_status)
						&& (!StringUtils.isBlank(z37.elementText("z37-pickup-location"))
								|| (StringUtils.isBlank(z37.elementText("z37-end-hold-date"))))) {
					String z37_end_hold_date = z37.elementText("z37-end-hold-date");
					String z37_status_sequence = "到待取，保留到";
					map.put("arrive", true);
					map.put("z37_status_sequence", z37_status_sequence);
				} else {
					String z37_status_sequence = z37_status;
					map.put("z37_status_sequence", z37_status_sequence);

				}
				l.add(map);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		JSON j = JSONArray.fromObject(l);
		return j;
	}

	// 续借 注意library
	public static Json renew(String barcode, String bor_id) {
		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=renew&bor_id=" + bor_id + "&item_barcode=" + barcode
				+ "&library=nlc50&user_name=www-app&user_password=pwdapp&con_lng=chi";
		logger.info(rdInfoUrl);
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
				value += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = null;
		Json j = new Json();
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();

			String reply = root.elementText("reply");
			if ("ok".equals(reply)) {
				j.setSuccess(true);
				return j;
			}

			j.setSuccess(false);
			String errorMsg = root.elementText("error");
			if (StringUtils.isBlank(errorMsg)) {
				StringBuffer errorMsgBuf = new StringBuffer();
				for (int i = 1; i <= 30; i++) {
					String tempText = root.elementText("error-text-" + i);
					if (!StringUtils.isBlank(tempText)) {
						tempText = tempText.replaceAll("Limit date", "最后续借日期");
						tempText = tempText.replaceAll("Overdue loans", "过期外借").replaceAll("Earliest due date",
								"最早的应还日期是");
						tempText = tempText.replaceAll("Renewal limit reached - No. of renews", "已达到续借限制 - 续借数");
						tempText = tempText.replaceAll("Limit:", "限制:");
						tempText = tempText.replaceAll("Renew date is earlier than today", "已达到续借周期限制 ");
						tempText = tempText.replaceAll("Renewal period limit reached - Renew date", "最后续借日期已到");
						errorMsgBuf.append(tempText + "\r\n");
					} else {
						break;
					}
				}
				errorMsg = errorMsgBuf.toString();
			}
			j.setMsg(errorMsg);
			return j;

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return j;
	}

	/**
	 * 读者使用读者卡登录后，能够进行图书预约。在此模块提供OPAC检索入口，读者检索到能够预约的图书后，能够对图书进行预约；
	 *
	 * @param bookId
	 * @return
	 */
	public static Json request(String bor_id, String barcode) {
		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=hold-req-nlc&bor_id=" + bor_id + "&item_barcode=" + barcode
				+ "&library=nlc50&user_name=www-app&user_password=pwdapp&con_lng=chi";
		logger.info(rdInfoUrl);
		URLConnection connection;
		URL url;
		try {
			url = new URL(rdInfoUrl);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/xml");

			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String sLine = "";

			InputStream l_urlStream = connection.getInputStream();
			// 传说中的三层包装！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sLine = l_reader.readLine()) != null) {
				value += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = null;
		Json j = new Json();
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			String reply = root.elementText("reply");
			if ("ok".equals(reply)) {
				j.setSuccess(true);
				return j;
			}
			j.setSuccess(false);
			int i = 1;
			String errorMsg = "";
			while (true) {
				String temp = root.elementText("error-text-" + i);
				if (StringUtils.isBlank(temp)) {
					break;
				}
				errorMsg += (temp + ";");
				i++;
			}
			j.setMsg(errorMsg);
			return j;
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}

		return j;

	}

	/**
	 * 取消预约 doc_num:z37-doc-number item_seq : z37-item-sequence seq
	 * :z37-sequence
	 * 
	 * @param bor_id
	 * @param doc_num
	 * @param item_seq
	 * @param seq
	 * @return
	 */
	public static Json cancelrequest(String bor_id, String doc_num, String item_seq, String seq) {
		String value = "";

		String rdInfoUrl = alephUrl + "/X?op=hold-req-cancel&doc_num=" + doc_num + "&item_seq=" + item_seq + "&seq="
				+ seq + "&bor_id=" + bor_id + "&library=nlc50&user_name=www-app&user_password=pwdapp&con_lng=chi";
		logger.info(rdInfoUrl);
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
				value += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = null;
		Json j = new Json();
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			String reply = root.elementText("reply");
			if ("ok".equals(reply)) {
				j.setSuccess(true);
				return j;
			}
			j.setSuccess(false);
			String errorMsg = root.elementText("error");
			j.setMsg(errorMsg);
			return j;
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}

		return j;
	}

	/**
	 * 判断用户的押金权限，bcashRight[0]为中文外借，bcashRight[1]为外文外借
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public static int[] pledgeCashRight(String account) {
		int[] bcashRight = { 0, 0 };
		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=bor-info-nlc&library=NLC50&bor_id=" + account + "&verification=123456"
				+ "&user_name=www-app&user_password=pwdapp";
		logger.info(rdInfoUrl);

		URLConnection connection;
		URL url;
		try {
			url = new URL(rdInfoUrl);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/xml");
			String sLine = "";
			InputStream l_urlStream = connection.getInputStream();
			// 传说中的三层包装！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sLine = l_reader.readLine()) != null) {
				value += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			bcashRight[0] = -1;
			bcashRight[1] = -1;
			logger.error(e.getMessage(), e);
			return bcashRight;
		} catch (IOException e) {
			bcashRight[0] = -1;
			bcashRight[1] = -1;
			logger.error(e.getMessage(), e);
			return bcashRight;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Iterator bor_z305 = root.elementIterator("z305");
			if (bor_z305 != null) {
				while (bor_z305.hasNext()) {
					Element item = (Element) bor_z305.next();
					String z305_sub_library = item.elementText("z305-sub-library");
					String z305_bor_status = item.elementText("z305-bor-status");
					if (z305_bor_status.indexOf("馆员") != -1 || z305_bor_status.indexOf("高管") != -1) {
						bcashRight[0] = 1;
						bcashRight[1] = 1;
						break;
					}
					if (z305_sub_library.equalsIgnoreCase("WJCN")) {
						bcashRight[1] = 1;
						continue;
					}
					if (z305_sub_library.equalsIgnoreCase("ZWWJ") || z305_sub_library.equalsIgnoreCase("WJDY")
							|| z305_sub_library.equalsIgnoreCase("WJDR") || z305_sub_library.equalsIgnoreCase("FGJY")) {
						bcashRight[0] = 1;
						continue;
					}
				}
			}
		} catch (DocumentException e) {
			bcashRight[0] = -1;
			bcashRight[1] = -1;
			logger.error(e.getMessage(), e);
		}
		return bcashRight;
	}

	/**
	 * 回写押金记录
	 * 
	 * @param bor_id
	 * @param type
	 * @param sum
	 * @return
	 */
	public static JSONObject updatePledgeCash(String bor_id, String type, String sum) {
		String value = "";

		String rdInfoUrl = alephUrl + "/X?op=update-cash&UPDATE_FLAG=n&SUB_LIBRARY=NLC50&ID=" + bor_id + "&TYPE=" + type
				+ "&STATUS=C&CREDIT_DEBIT=D&SUM=" + sum
				+ "&PAYMENT_CATALOGER=&PAYMENT_TARGET=NLC50&user_name=www-app&user_password=pwdapp";
		logger.info(rdInfoUrl);
		URLConnection connection;
		URL url;
		try {
			url = new URL(rdInfoUrl);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/xml");

			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String sLine = "";

			InputStream l_urlStream = connection.getInputStream();
			// 传说中的三层包装！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sLine = l_reader.readLine()) != null) {
				value += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Element replay = root.element("reply");
			if (replay != null) {
				map.put("result", true);
				map.put("reply", replay.getText() == null ? "" : replay.getText());
				map.put("Z31-ID", root.element("Z31_ID").getText() == null ? "" : root.element("Z31_ID").getText());
				map.put("Z31-SEQUENCE",
						root.element("Z31_SEQUENCE").getText() == null ? "" : root.element("Z31_SEQUENCE").getText());
			} else {
				map.put("result", false);
				map.put("replay", root.element("error").getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		JSONObject j = JSONObject.fromObject(map);
		return j;
	}

	// 物理卡登录
	public static String kalogin(String account, String password) {
		String value = "";

		String rdInfoUrl = alephUrl + "/X?op=bor-auth&Library=NLC50&bor_id=" + account + "&verification=" + password
				+ "&Sub_Library=NLC50&user_name=www-app&user_password=pwdapp";
		logger.info(rdInfoUrl);
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
				value += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = null;
		String z303_id = "";
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Element z303 = root.element("z303");
			if (null == z303) {
				return "";
			}
			z303_id = z303.elementText("z303-id");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return z303_id;
	}

	// 借阅历史
	public static JSONObject loan_history(String bor_id) {
		JSONObject json = new JSONObject();
		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=loan-history&bor_id=" + bor_id
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp&con_lng=chi";
		logger.info(rdInfoUrl);
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
				value += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			json.put("result", false);
			json.put("data", "aleph error");
			return json;

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			json.put("result", false);
			json.put("data", "aleph error");
			return json;
		}

		Document doc = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Iterator loan_item = root.elementIterator("loan-item");
			while (loan_item.hasNext()) {
				Element item = (Element) loan_item.next();

				Map<String, Object> map = new HashMap<String, Object>();
				String title = item.elementText("z13-title");
				String year = item.elementText("z13-year");
				String author = item.elementText("z13-author");
				String call_no = item.elementText("call-no");

				String returned_date = item.elementText("returned-date"); // 归还日期
				String returned_hour = item.elementText("returned-hour"); // 归还时间
				String due_date = item.elementText("due-date"); // 应还日期
				String due_hour = item.elementText("due-hour"); // 应还时间
				String sub_library = item.elementText("sub-library"); // 分馆

				map.put("title", title == null ? "" : title); // 题名
				map.put("author", author == null ? "" : author);
				map.put("year", year == null ? "" : year);
				map.put("call_no", call_no == null ? "" : call_no);

				map.put("returned_date", returned_date == null ? "" : returned_date);
				map.put("returned_hour", returned_hour == null ? "" : returned_hour);

				String s1 = due_date == null ? "" : due_date;
				String s2 = due_hour == null ? "" : due_hour;
				map.put("due_date", s1);
				map.put("due_hour", s2);
				map.put("sub_library", sub_library == null ? "" : sub_library);
				list.add(map);
			}

			json.put("result", true);
			json.put("data", JSONArray.fromObject(list));
			return json;

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			json.put("result", false);
			json.put("data", "xml parse error");
			return json;
		}
	}
	
	//查询借书地点接口
	public static JSONObject enHoldSelectLocation(String loginAccount, String item_status_code) {
		JSONObject res = new JSONObject();
		List<Map<String, String>> content = new ArrayList<Map<String, String>>();
		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + loginAccount
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp&con_lng=chi";
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
				value += sLine + "\r\n";
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", "WWYL");
			map.put("text", "外文文献阅览区");
			content.add(map);
			res.put("data", content);
			return res;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", "WWYL");
			map.put("text", "外文文献阅览区");
			content.add(map);
			res.put("data", content);
			return res;
		}
		
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Element z305 = root.element("z305");
			String statusText = z305.elementText("z305-bor-status");	//<z305-bor-status>新馆馆员</z305-bor-status>
			String readerStatusCode = PropertiesUtil.getPropValue("properties/readerstatus.properties", statusText);	//新馆馆员对应61
			
			if(StringUtils.isNotBlank(readerStatusCode) && StringUtils.isNotBlank(item_status_code)) {
				String readerPlaceCodeArrStr = "";
				if("09".equals(item_status_code) || "9".equals(item_status_code)) {
					readerPlaceCodeArrStr = PropertiesUtil.getPropValue("properties/pickupplace09.properties", readerStatusCode);
				}else if("10".equals(item_status_code)) {
					readerPlaceCodeArrStr = PropertiesUtil.getPropValue("properties/pickupplace10.properties", readerStatusCode);
				}else if("12".equals(item_status_code)) {
					readerPlaceCodeArrStr = PropertiesUtil.getPropValue("properties/pickupplace12.properties", readerStatusCode);
				}
				
				if(StringUtils.isNotBlank(readerPlaceCodeArrStr)) {
					String[] readerPlaceCodeArr = readerPlaceCodeArrStr.split(",");	//例如 ILLCN,WWYL
					for (String readerPlaceCode : readerPlaceCodeArr) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("code", readerPlaceCode);
						String text = PropertiesUtil.getPropValue("properties/domain.properties", readerPlaceCode);
						map.put("text", text);
						content.add(map);
					}
					res.put("data", content);
					return res;
				}
			}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "WWYL");
		map.put("text", "外文文献阅览区");
		content.add(map);
		res.put("data", content);
		return res;
	}

	// 预约结果
	public static JSON currentHold(String bor_id) {
		String value = "";

		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + bor_id
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp&con_lng=chi";
		logger.info(rdInfoUrl);
		URLConnection connection;
		URL url;
		try {
			url = new URL(rdInfoUrl);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/xml");

			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String sLine = "";

			InputStream l_urlStream = connection.getInputStream();
			// 传说中的三层包装！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sLine = l_reader.readLine()) != null) {
				value += sLine + "\r\n";
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		Document doc = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");

		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Iterator itemIter = root.elementIterator("item-h");
			while (itemIter.hasNext()) {
				Element item = (Element) itemIter.next();
				Map<String, Object> map = new HashMap<String, Object>();
				Element z37 = item.element("z37");
				String z37_doc_number = z37.elementText("z37-doc-number");
				Element z30 = item.element("z30");
				String call_no = z30.elementText("z30-call-no");
				String library = z30.elementText("z30-sub-library");
				String tag = z30.elementText("xxx-tag");
				Element z13 = item.element("z13");
				String year = z13.elementText("z13-year");//
				String author = z13.elementText("z13-author");//
				String title = z13.elementText("z13-title");//
				String z37_end_request_date = z37.elementText("z37-end-request-date");
				String z37_item_sequence = z37.elementText("z37-item-sequence");
				String z37_request_date = z37.elementText("z37-request-date");
				String z37_sequence = z37.elementText("z37-sequence");
				String z37_status = z37.elementText("z37-status");
				String pick_up_loc = z37.elementText("z37-pickup-location");
				map.put("pick_up_loc", pick_up_loc == null ? "" : pick_up_loc);
				map.put("call_no", call_no == null ? "" : call_no);
				map.put("tag", tag == null ? "" : tag);
				map.put("year", year == null ? "" : year);
				map.put("author", author == null ? "" : author);
				map.put("title", title == null ? "" : title);
				map.put("xxx_tag", tag == null ? "" : tag);
				map.put("library", library == null ? "" : library);
				map.put("end_request_date", z37_end_request_date == null ? "" : z37_end_request_date);
				map.put("z37_item_sequence", z37_item_sequence == null ? "" : z37_item_sequence);
				map.put("z37_request_date", z37_request_date == null ? "" : z37_request_date);
				map.put("z37_sequence", z37_sequence == null ? "" : z37_sequence);
				map.put("z37_doc_number", z37_doc_number == null ? "" : z37_doc_number);

				if (Waiting_in_queue.equalsIgnoreCase(z37_status)) {
					String z13_doc_number = z13.elementText("z13-doc-number");
					String z30_barcode = z30.elementText("z30-barcode");

					String loan_due_date = "";
					if (org.apache.commons.lang3.StringUtils.isNotBlank(z30_barcode)) {
						String _url = alephUrl + "/X?op=item-data-nlc&base=nlc01&doc-number=" + z13_doc_number
								+ "&user_name=www-app&user_password=pwdapp";
						logger.info("【排队的时候查询单册的信息】" + _url);
						URLConnection connection2;
						URL url2;
						String value2 = "";
						try {
							url2 = new URL(_url);
							connection2 = url2.openConnection();
							connection2.setRequestProperty("Content-Type", "application/xml");

							String sLine = "";
							InputStream l_urlStream = connection2.getInputStream();
							BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
							while ((sLine = l_reader.readLine()) != null) {
								value2 += sLine + "\r\n";
							}
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

						Document doc2 = null;
						try {
							doc2 = DocumentHelper.parseText(value2);
							Element root2 = doc2.getRootElement();
							Iterator itemIter2 = root2.elementIterator("item");
							while (itemIter2.hasNext()) {
								Element itemx = (Element) itemIter2.next();
								String barcode = itemx.elementText("barcode");
								if(z30_barcode.equals(barcode)) {
									loan_due_date = itemx.elementText("loan-due-date");
									break;
								}
							}

						} catch (Exception e) {
						}
					}

					String z37_send_action = z37.elementText("z37-send-action");
					String z37_status_sequence = "未到达，在排队中，等候位置" + z37_send_action;
					
					if(org.apache.commons.lang3.StringUtils.isNotBlank(loan_due_date)) {
						try {
							Date d3 = sdf3.parse(loan_due_date);
							loan_due_date = sdf2.format(d3);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						map.put("loan_due_date", loan_due_date);
					}
					map.put("arrive", false);
					map.put("z37_status_sequence", z37_status_sequence);
				} else if (In_processz38_status.equalsIgnoreCase(z37_status)) {
					String z37_status_sequence = "正在处理中";
					map.put("arrive", false);
					map.put("z37_status_sequence", z37_status_sequence);
				} else if (StringUtils.isBlank(z37_status)
						&& (!StringUtils.isBlank(z37.elementText("z37-pickup-location"))
								|| (StringUtils.isBlank(z37.elementText("z37-end-hold-date"))))) {

					String z37_end_hold_date = z37.elementText("z37-end-hold-date");
					try {
						Date end_hold_date = sdf2.parse(z37_end_hold_date);
						String end_hold_datestr = sdf1.format(end_hold_date);
						map.put("end_hold_datestr", end_hold_datestr);
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
					String z37_status_sequence = "书到待取";
					map.put("arrive", true);
					map.put("z37_status_sequence", z37_status_sequence);
				} else {
					String z37_status_sequence = z37_status == null ? "" : z37_status;
					map.put("z37_status_sequence", z37_status_sequence);

				}
				l.add(map);
			}

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		Collections.reverse(l);
		JSON j = JSONArray.fromObject(l);
		return j;
	}

	// 首次生成欠费
	public static List<Owe> getFirstOwe(String loginAccount) {
		String value = "";
		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + loginAccount
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp";

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
				value += sLine + "\r\n";
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			return null;

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;

		}

		List<Owe> result = new ArrayList<Owe>();
		Document doc = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();
			Iterator itemIter = root.elementIterator("fine");
			while (itemIter.hasNext()) {
				Element item = (Element) itemIter.next();
				Map<String, String> map = new HashMap<String, String>();
				Element z31 = item.element("z31");
				String z31_status = z31.elementText("z31-status");
				if ("Paid".equals(z31_status)) // 已付
					continue;

				String z31_date = z31.elementText("z31-date");
				Date mydue = sdf2.parse(z31_date);
				if (!getNowDateShort().equals(mydue)) {
					continue;
				}

				String z31_description = z31.elementText("z31-description"); // 预约未取
				if (StringUtils.isNotBlank(z31_description)) {
					z31_description = z31_description.trim();
				}
				String z31_sum = z31.elementText("z31-sum"); // (0.30)
				// z31_date = sdf1.format(mydue);
				Element z13 = item.element("z13");
				String title = z13.elementText("z13-title");

				Owe owe = new Owe();
				owe.setTitle(title);
				owe.setDescription(z31_description);
				owe.setMoney(z31_sum);
				result.add(owe);
			}

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			return null;

		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		return result;
	}

	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

}
