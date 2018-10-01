package cn.gov.nlc.aleph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.GlobalConstant;
import cn.gov.nlc.util.PropertiesUtil;
import cn.gov.nlc.util.PropertiesUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OpacSearch {

	private static String alephUrl = PropertiesUtil.getPropValue("properties/configuration.properties", "aleph");
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.aleph.OpacSearch.class);

	public static List<Find> searchMoreBase(JSONObject jsonQuery, String find_base, String adjacent, int pageSize,
			int page) {
		String[] bases = find_base.split(",");
		List<Find> l = new ArrayList<Find>();
		for (int i = 0; i < bases.length; i++) {
			Find f = find(jsonQuery, bases[i], adjacent);
			if(!f.success) {
				continue;
			}
//			logger.info("opac书的条数：" + f.getNo_records());
			sort_codes(f.getBase(), f.getSet_number());
			l.add(f);
		}
		return l;
	}
	
	public static int getAllLibCount(JSONObject jsonQuery) {
		int result = 0;
		String[] bases = {"nlc01s", "nlc09s"};
		for(int i = 0; i < bases.length; i++) {
			Find f = find(jsonQuery, bases[i], "Y");
			int no_records = f.getNo_records();
			result += no_records;
		}
		
		return result;
	}
	

	//opac
	public static Find find(JSONObject jsonQuery, String find_base, String adjacent) {
		StringBuilder request = new StringBuilder();
		Iterator iterator = jsonQuery.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = jsonQuery.getString(key);
			if (Common.IsNullOrEmpty(value))
				continue;
			if (key.equalsIgnoreCase("WYR")) {
				request.append(key).append("=").append(value).append(" and ");
			} else {
				request.append(key).append("=(").append(value).append(") and ");
			}
		}
		if (request.length() > 0) {
			request.delete(request.length() - 5, request.length());
		}
		Find f = new Find();
		String _url = "";
		
		try {
			_url = alephUrl + "/X?op=find&request=" + java.net.URLEncoder.encode(request.toString(), "utf-8")
						+ "&base=" + find_base + "&user_name=www-app&user_password=pwdapp" + "&adjacent=" + adjacent;
		} catch (Exception e) {

		}
		//logger.info("【opac获取set_num的URL：】"+_url);
		
		URLConnection connection;
		URL url;
		String value = "";
		try {
			url = new URL(_url);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/text");

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
			f.success = false;
			return f;
		} catch (IOException e) {
			e.printStackTrace();
			f.success = false;
			return f;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();// 获取根节点
			Element error = root.element("error");
			if (null != error) {
				f.success = false;
			} else {
				String set_number = root.elementText("set_number");
				String no_records = root.elementText("no_records");
				String no_entries = root.elementText("no_entries");
				String session_id = root.elementText("session-id");

				if(null != set_number) {
					f.setSet_number(Integer.parseInt(set_number));
				}
				f.setBase(find_base);
				f.setBase_cn(GlobalConstant.baseList.get(find_base.toUpperCase()));
				
				if(null != no_entries) {
					f.setNo_entries(Integer.parseInt(no_entries));
				}
				
				if(null != no_records) {
					f.setNo_records(Integer.parseInt(no_records));
				}
				
				f.setSession_id(session_id);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return f;

	}

	// opac排序
	public static void sort_codes(String base, int set_number) {
		if(null == base) {
			return ;
		}
		String _url = alephUrl + "/X?op=sort-set&library=" + base + "&set_number=" + set_number
					+ "&sort_code_1=01&sort_order_1=D&sort_code_2=02&sort_order_2=A&user_name=www-app&user_password=pwdapp";
//		logger.info("【opac排序的url：】"+_url);
		URLConnection connection;
		URL url;
		String value = "";
		Map<String, String> map = new HashMap<String, String>();
		try {
			url = new URL(_url);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/text");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static JSON opacPresent(Find f, int pageSize, int page) {
		String value = present(f, pageSize, page);
		List<PresentRecord> list = null;
		if (f.getBase().contains("1")) {
			list = getResultCN(f.getBase(), value);
		} else {
			list = getResultEn(f.getBase(), value);
		}
		JSONObject job = new JSONObject();
		job.put("count", f.getNo_records());
		job.put("data", JSONArray.fromObject(list));
		return job;

	}

	//opac获取列表及其内容
	public static String present(Find f, int pageSize, int page) {
		int set_number = f.getSet_number();
		int set_entry = f.getNo_entries();
		int start = (page - 1) * pageSize + 1;// 开始索引数
		int endnum = ((page - 1) * pageSize + 1) + pageSize - 1;
		if (endnum > set_entry) {
			endnum = set_entry;
		}
		String end = String.valueOf(endnum);// 结束索引数
		String _url = alephUrl + "/X?op=present&set_entry=" + start + "-" + end + "&set_number=" + set_number + "&base="
					+ f.getBase() + "&user_name=www-app&user_password=pwdapp";
//		logger.info("【opac获取列表及其内容的URL：】"+_url);
		URLConnection connection;
		URL url;
		String value = "";
		try {
			url = new URL(_url);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/text");

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
		return value;
	}

	public static List<PresentRecord> getResultCN(String base, String value) {
		List<PresentRecord> l = new ArrayList<PresentRecord>();
		
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();// 获取根节点
			Iterator record_iter = root.elementIterator("record");// 获得根节点的子节点record
			while (record_iter.hasNext()) {
				Element record = (Element) record_iter.next();
				PresentRecord pr = new PresentRecord();
				String doc_number = record.elementText("doc_number");
				pr.doc_number = doc_number;
				Element metadata = record.element("metadata");
				Element oai_marc = metadata.element("oai_marc");
				
				Iterator varfield_iter = oai_marc.elementIterator("varfield");
				while (varfield_iter.hasNext()) {
					Element varfield = (Element) varfield_iter.next();
					
					if("010".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						
						String isbn = "";
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								isbn += subfield.getText();	//isbn
							}
							if ("d".equals(subfield.attributeValue("label"))) {
								isbn += " : " + subfield.getText();	//isbn
							}
						}
						pr.setIsbn(isbn);
					}
					
					if ("200".equals(varfield.attributeValue("id"))) {
						String title = "";
						Iterator subfield_iter = varfield.elementIterator();

						while (subfield_iter.hasNext()) {
							
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								title += subfield.getText();
							}
							if ("h".equals(subfield.attributeValue("label"))) {
								title += "." + subfield.getText();
							}
							if ("b".equals(subfield.attributeValue("label"))) {
								title += " [" + subfield.getText() + "] ";
								pr.type = subfield.getText();
							}
							if ("e".equals(subfield.attributeValue("label"))) {
								title += " : " + subfield.getText();
							}
							if ("d".equals(subfield.attributeValue("label"))) {
								title += " = " + subfield.getText();
								pr.setTiming(subfield.getText());
							}
							if ("f".equals(subfield.attributeValue("label"))) {
								title += "/ " + subfield.getText() + " ; ";
								pr.setAuthor(subfield.getText());

							}
							if ("g".equals(subfield.attributeValue("label"))) {
								title += subfield.getText();

							}
						}
						pr.setTitle(title);
					}
					
					if("330".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								pr.setNeirongtiyao(subfield.getText());	//内容提要
							}
						}
					}
					
					if("606".equals(varfield.attributeValue("id"))) {
						String zhuti = "";
						Iterator subfield_iter = varfield.elementIterator();
						
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							
							if ("a".equals(subfield.attributeValue("label"))) {
								zhuti += subfield.getText();
							}
							if ("x".equals(subfield.attributeValue("label"))) {
								zhuti += "--"+subfield.getText();
							}
						}
						pr.setZhuti(zhuti);
					}
					
					if("090".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								pr.setZhongtufenleihao(subfield.getText());
							}
						}
					}
					
					
					if ("210".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							
							if ("c".equals(subfield.attributeValue("label"))) {
								pr.publishHouse = subfield.getText(); // 出版项
																		// ：出版社
							}
							if ("d".equals(subfield.attributeValue("label"))) {
								 String pro = subfield.getText(); // ===>出版项 ：年
								 if(pro.indexOf("[") > -1) {
									 pr.publishYear = "\""+pro+"\"";
								 }else {
									 pr.publishYear = pro;
								 }
							}
						}
					}
				}
				pr.setBase(base);
				l.add(pr);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return l;
	}

	public static List<PresentRecord> getResultEn(String base, String value) {
		List<PresentRecord> l = new ArrayList<PresentRecord>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();// 获取根节点
			Iterator record_iter = root.elementIterator("record");// 获得根节点的子节点record
			while (record_iter.hasNext()) {
				Element record = (Element) record_iter.next();
				
				PresentRecord pr = new PresentRecord();
				pr.base = base;
				pr.doc_number = record.elementText("doc_number");
				Element metadata = record.element("metadata");
				Element oai_marc = metadata.element("oai_marc");
				Iterator varfield_iter = oai_marc.elementIterator("varfield");
				List<String> zt = new ArrayList<String>();
				
				while (varfield_iter.hasNext()) {
					Element varfield = (Element) varfield_iter.next();
					
					if("020".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						
						String isbn = "";
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								isbn += subfield.getText();	//isbn
							}
							if ("c".equals(subfield.attributeValue("label"))) {
								isbn += " : " + subfield.getText();	//isbn
							}
						}
						pr.setIsbn(isbn);
					}
					
					if("090".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								pr.setZhongtufenleihao(subfield.getText());
							}
						}
					}
					
					if ("100".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								pr.author = subfield.getText();
							}
						}
					}
					if ("245".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						String title = "";
						String type = "";
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if (subfield.attributeValue("label").matches("[abc]")) {
								title += subfield.getText();
							}
							if (subfield.attributeValue("label").matches("[kh]")) {
								type += subfield.getText();
							}
						}
						pr.type = type;
						pr.title = title + ("".equals(pr.type) ? "" : "[" + pr.type + "]");
						pr.setTiming(title);
					}
					
					if("650".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								zt.add(subfield.getText());
							}
						}
					}
					
					if("505".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								pr.setNeirongtiyao(subfield.getText());	//内容提要
							}
						}
					}
					
					if ("260".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("b".equals(subfield.attributeValue("label"))) {
								pr.publishHouse = subfield.getText();
							}
							if ("c".equals(subfield.attributeValue("label"))) {
								pr.publishYear = subfield.getText();
							}
						}
					}

				}
				pr.setZhuti(zt.toString());
				l.add(pr);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return l;
	}
	
	// 得到馆藏信息
	public static JSON doitem(String base, String doc_number) {
		String _url = alephUrl + "/X?op=item-data-nlc&base=" + base + "&doc-number=" + doc_number
					+ "&user_name=www-app&user_password=pwdapp";
		
		logger.info("【opac单本书的详情：】"+_url);
		URLConnection connection;
		URL url;
		String value = "";
		try {
			url = new URL(_url);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/text");

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
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		Document doc = null;
		List<Item> l = new ArrayList<Item>();
		Set<String> s = new TreeSet<String>();
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();// 获取根节点
			Iterator item_iter = root.elementIterator("item");// 获得根节点的子节点record
			while (item_iter.hasNext()) {
				Element itemE = (Element) item_iter.next();
				Item item = new Item();
				
				String hold_allowed = itemE.elementText("hold-allowed");
				// 能否被预约
				item.hold_allowed = "Y".equalsIgnoreCase(hold_allowed) ? true : false;
				// 预约
				item.yuyue = "";
				// 单册状态
				String itemStatus = itemE.elementText("item-status");
				item.item_status_code = itemStatus;
				String item_status = PropertiesUtil.getPropValue("properties/domain.properties", itemStatus);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(item_status)) {
					item.item_status = item_status;
				} else {
					item.item_status = itemStatus;
				}
				// 索取号
				item.call_no_1 = formatSuoquhao(itemE.elementText("call-no-1"));
				// 应还日期
				String loan_due_date_str = itemE.elementText("loan-due-date");
				if(org.apache.commons.lang3.StringUtils.isNotBlank(loan_due_date_str)) {
					item.loan_due_date = loan_due_date_str;
				}else {
					String item_process_status = itemE.elementText("item-process-status");
					if("DS".equalsIgnoreCase(item_process_status)) {
						continue;
					}
					if(org.apache.commons.lang3.StringUtils.isNotBlank(item_process_status)) {
						String item_process_status_convert = PropertiesUtil.getPropValue("properties/domain.properties", item_process_status);
						if(org.apache.commons.lang3.StringUtils.isNotBlank(item_process_status_convert)) {
							item.loan_due_date = item_process_status_convert;
						}else {
							item.loan_due_date = "";
						}
					}else {
						item.loan_due_date = "";
					}
				}
				// 子库
				String sub_library = itemE.elementText("sub-library");
				item.sub_library_code = sub_library;
				String sublibrary = PropertiesUtil.getPropValue("properties/domain.properties", sub_library);
				if (null != sublibrary && !"".equals(sublibrary)) {
					item.sub_library = sublibrary;
				} else {
					item.sub_library = sub_library;
				}

				// 库
				item.library = itemE.elementText("library");
				// 架位导航
				item.call_no_2 = itemE.elementText("call-no-2");
				// 条码
				item.barcode = itemE.elementText("barcode");

				// 描述
				item.description = itemE.elementText("description");
				// 馆藏信息
				StringBuffer bf = new StringBuffer();
				if (!StringUtils.isBlank(item.call_no_1)) {
					bf.append(item.call_no_1).append("\\");
				}
				if (!StringUtils.isBlank(item.sub_library_desc)) {
					bf.append(item.sub_library_desc).append("\\");
				}
				if (!StringUtils.isBlank(item.call_no_2)) {
					bf.append(item.call_no_2);
				}
				item.guancang = bf.toString();
				l.add(item);
				if (null != item.getSub_library() && !"".equals(item.getSub_library()))
					s.add(item.getSub_library());

			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("iteml", l);
		m.put("items", s);
		return JSONObject.fromObject(m);
	}
	
	/**
	 * 预约
	 * 
	 * @param barcode
	 * @param bor_id
	 * @return
	 */
	public static Json hold_req(String barcode, String bor_id, String placecode) {
		Json j = new Json();
		String _url = "";
		if(org.apache.commons.lang3.StringUtils.isNotBlank(placecode)) {
			_url = alephUrl + "/X?op=hold-req-nlc&item_barcode=" + barcode + "&bor_id=" + bor_id
					+ "&library=nlc50&USER_NAME=www-app&USER_PASSWORD=pwdapp&con_lng=chi&location="+placecode;
		}else {
			_url = alephUrl + "/X?op=hold-req-nlc&item_barcode=" + barcode + "&bor_id=" + bor_id
					+ "&library=nlc50&USER_NAME=www-app&USER_PASSWORD=pwdapp&con_lng=chi";
		}
		
		logger.info(_url);
		URLConnection connection;
		URL url;
		String value = "";
		try {
			url = new URL(_url);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/text");

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
		
		logger.info("【预约结果】"+value);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();// 获取根节点
			String reply = root.elementText("reply");
			if ("ok".equalsIgnoreCase(reply)) {
				j.setSuccess(true);
			} else {
				String errorMsg = "";
				String error = root.elementText("error");

				int i = 1;
				while (true) {
					String target = "error-text-" + i;
					String oneItem = root.elementText(target);
					if (StringUtils.isBlank(oneItem)) {
						break;
					}
					errorMsg = errorMsg + oneItem + ";";
					i++;
				}
				if (!StringUtils.isBlank(error)) {
					errorMsg = errorMsg + error;
				}
				j.setMsg(errorMsg);
			}
		} catch (Exception e) {
		}
		return j;
	}

	private static String formatSuoquhao(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		str = str.replace("$$k", "");
		str = str.replace("$$h", "\\");
		str = str.replace("$$i", "\\");
		str = str.replace("$$j", "");
		str = str.replaceAll("\\$\\$\\w", "");
		return str;
	}
}
