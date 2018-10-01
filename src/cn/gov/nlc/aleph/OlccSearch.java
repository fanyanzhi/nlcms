/**
 *  Copyright (c) 2015 Neusoft.com corporation All Rights Reserved.
 */

package cn.gov.nlc.aleph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import cn.gov.nlc.pojo.Olcclib;
import cn.gov.nlc.util.AppOlcc;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.PropertiesUtils;

/**
 * 此处进行功能描述。
 *
 * @author Hp
 * @version 1.0
 *
 *          <pre>
 *  使用范例：
 *  创建时间:2015-2-9 下午02:23:46
 *  修改历史：
 *     ver     修改日期     修改人  修改内容
 * ──────────────────────────────────
 *   V1.00   2015-2-9   Hp  初版
 *
 *          </pre>
 *
 */
public class OlccSearch {
	private static String ucsUrl = PropertiesUtils.getPropertyValue("ucsurl");
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.aleph.OpacSearch.class);

	public static List<Map<String, String>> searchMoreBase(JSONObject jsonQuery, String find_base, String adjacent, int pageSize, int page) {
		String[] bases = find_base.split(",");
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		for (int i = 0; i < bases.length; i++) {
			Map<String, String> map = find(jsonQuery, bases[i], adjacent);
				if(map != null && map.containsKey("set_number")) {
					sort_codes(bases[i], map.get("set_number"));
					l.add(map);
				}
		}
		return l;
	}

	// 检索，支持对单个库某个字段进行查询
	public static Map<String, String> find(JSONObject jsonQuery, String find_base, String adjacent) {
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
			_url = ucsUrl + "/X?op=find&request=" + java.net.URLEncoder.encode(request.toString(), "utf-8") + "&base="
					+ find_base + "&user_name=WWW-IOS&user_password=PWDIOS" + "&adjacent=" + adjacent;
			logger.info(_url);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		URLConnection connection;
		URL url;
		String value = "";
		Map<String, String> map = new HashMap<String, String>();
		try {
			url = new URL(_url);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/text");
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
		
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();// 获取根节点
			String set_number = root.elementText("set_number");
			String no_records = root.elementText("no_records");
			String no_entries = root.elementText("no_entries");
			String session_id = root.elementText("session-id");
			if(StringUtils.isNotBlank(set_number)) {
				map.put("set_number", set_number);
			}
			map.put("no_records", no_records);
			map.put("no_entries", no_entries);
			map.put("session_id", session_id);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return map;
	}

	// 排序 权限不足&user_password=
	public static void sort_codes(String base, String set_number) {
		String _url = "http://ucs.nlc.cn/X?op=sort-set&library=" + base
				+ "&set_number="+ set_number +"&sort_code_1=01&sort_order_1=D&sort_code_2=02&sort_order_2=A&user_name=WWW-IOS&user_password=PWDIOS";
		URLConnection connection;
		URL url;
		String value = "";
		Map<String, String> map = new HashMap<String, String>();
		try {
			url = new URL(_url);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/text");
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
	}
	
	public static int getAllLibCount(JSONObject jsonQuery) {
		int result = 0;
		String[] bases = {"ucs01", "ucs09"};
		for(int i = 0; i < bases.length; i++) {
			Map<String, String> map = find(jsonQuery, bases[i], "Y");
			if(null != map) {
				String count = map.get("no_records");
				if(StringUtils.isNotBlank(count)) {
					int r = Integer.valueOf(count);
					result += r;
				}
			}
		}
		
		return result;
	}

	// 返回检索结果
	public static JSON showRedults(JSONObject request, String find_base, String adjacent, int pageSize, int page) {
		Map<String, String> map = find(request, find_base, adjacent);
		logger.info("【olcc返回的map：】"+map);
		
		if (map == null || !map.containsKey("set_number")) {
			return null;
		}
		// sort_codes();
		String set_number = map.get("set_number");
		String set_entry = map.get("no_entries");// 记录数
		// int pageCount = Integer.parseInt(set_entry) / pageSize + 1;// 总页数
		String start = String.valueOf((page - 1) * pageSize + 1);// 开始索引数
		int endnum = ((page - 1) * pageSize + 1) + pageSize - 1;
		if (endnum > Integer.parseInt(set_entry)) {
			endnum = Integer.parseInt(set_entry);
		}
		String end = String.valueOf(endnum);// 结束索引数
		String _url = ucsUrl + "/X?op=present&set_entry=" + start + "-" + end + "&set_number=" + set_number + "&base="
				+ find_base + "&user_name=www-ios&user_password=pwdios";
		logger.info(_url);
		
		URLConnection connection;
		URL url;
		String value = "";
		try {
			url = new URL(_url);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/text");
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

		Map<String, String> bookmap = null;
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(value);
			Element root = doc.getRootElement();// 获取根节点
			Iterator record_iter = root.elementIterator("record");// 获得根节点的子节点record
			while (record_iter.hasNext()) {
				Element record = (Element) record_iter.next();
				bookmap = new HashMap<String, String>();
				Element metadata = record.element("metadata");
				Element oai_marc = metadata.element("oai_marc");
				Iterator fixfield_iter = oai_marc.elementIterator("fixfield");
				while (fixfield_iter.hasNext()) {
					Element fixfield = (Element) fixfield_iter.next();
					if ("LDR".equals(fixfield.attributeValue("id"))) {
						String toubiaoqu = fixfield.getText(); // toubiaoqu===>头标区
						bookmap.put("toubiaoqu", toubiaoqu);
					}
					if ("001".equals(fixfield.attributeValue("id"))) {
						String IDhao = fixfield.getText();// IDhao ID号 标识号
						bookmap.put("IDhao", IDhao);
					}
					if ("FMT".equals(fixfield.attributeValue("id"))) {
						String fileType = fixfield.getText();// 数目类型
						bookmap.put("FileType", fileType);
					}
				}
				Iterator varfield_iter = oai_marc.elementIterator("varfield");
				Map<String, List<JSONObject>> maplib = new HashMap<String, List<JSONObject>>();
				List<String> libcode = new ArrayList<String>();
				int i = 1;
				int j = 1;
				while (varfield_iter.hasNext()) {
					Element varfield = (Element) varfield_iter.next();
					if ("100".equals(varfield.attributeValue("id"))) {
						String tongyong = varfield.elementText("subfield"); // 通用数据
						bookmap.put("tongyong", tongyong);
					}
					if ("101".equals(varfield.attributeValue("id"))) {
						String language = varfield.elementText("subfield");// 语言
						bookmap.put("language", language);
					}
					if ("200".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								String name = subfield.getText(); // name===>书名
								bookmap.put("name", name);
							}
							if ("9".equals(subfield.attributeValue("label"))) {
								String title = subfield.getText(); // 题名
								bookmap.put("title", title);
							}
							if ("b".equals(subfield.attributeValue("label"))) {
								String type = subfield.getText(); // ===>资料类型
								bookmap.put("type", type);
							}
							if ("f".equals(subfield.attributeValue("label"))) {
								String author = subfield.getText(); // ===>作者
								bookmap.put("author", author);
							}
							if ("e".equals(subfield.attributeValue("label"))) {
								String example = subfield.getText(); // ===>作者
								if (!bookmap.containsKey("example")) {
									bookmap.put("example", example);
								} else {
									bookmap.put("example2", example);
								}

							}
							if ("d".equals(subfield.attributeValue("label"))) {
								String yiwen = subfield.getText(); // ===>作者
								bookmap.put("yiwen", yiwen);
							}
						}
					}
					// 西文
					if ("245".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								String name = subfield.getText(); // name===>书名
								bookmap.put("name", name);
							}
							if ("k".equals(subfield.attributeValue("label"))) {
								String type = subfield.getText(); // ===>资料类型
								bookmap.put("type", type);
							}
							if ("c".equals(subfield.attributeValue("label"))) {
								String author = subfield.getText(); // ===>作者
								bookmap.put("author", author);
							}
						}
					}
					if ("210".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								String place = subfield.getText(); // 出版项 ：地点
								bookmap.put("place", place);
							}
							if ("c".equals(subfield.attributeValue("label"))) {
								String press = subfield.getText(); // 出版项 ：出版社
								bookmap.put("press", press);
							}
							if ("d".equals(subfield.attributeValue("label"))) {
								String year = subfield.getText(); // ===>出版项 年
								if(year.indexOf("[") > -1) {
									bookmap.put("year", "\""+year+"\"");
								}else {
									bookmap.put("year", year);
								}
							}

						}
					}
					// 西文
					if ("260".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								String place = subfield.getText(); // 出版项 ：地点
								bookmap.put("place", place);
							}
							if ("b".equals(subfield.attributeValue("label"))) {
								String press = subfield.getText(); // 出版项 ：出版社
								bookmap.put("press", press);
							}
							if ("c".equals(subfield.attributeValue("label"))) {
								String year = subfield.getText(); // ===>出版项 年
								if(year.indexOf("[") > -1) {
									bookmap.put("year", "\""+year+"\"");
								}else {
									bookmap.put("year", year);
								}
							}

						}
					}
					if ("215".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								String yeshu = subfield.getText(); // 载体形态项 ：页数
								bookmap.put("yeshu", yeshu);
							}

							if ("d".equals(subfield.attributeValue("label"))) {
								String cm = subfield.getText(); // ===>载体形态项
								bookmap.put("cm", cm);
							}
							
							if("e".equals(subfield.attributeValue("label"))) {
								String attachsize = subfield.getText();
								bookmap.put("attachsize", attachsize);
							}

						}
					}
					if ("300".equals(varfield.attributeValue("id"))) {
						String nolremark = varfield.elementText("subfield");
						bookmap.put("remark", nolremark);
					}
					if ("330".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								String Introduction = subfield.getText(); // 内容提要
								bookmap.put("Introduction", Introduction);
							}
						}
					}
					
					if ("606".equals(varfield.attributeValue("id"))) {
						Iterator subject_iter = varfield.elementIterator();
						StringBuilder sbSubject = new StringBuilder();
						while (subject_iter.hasNext()) {
							Element subfield = (Element) subject_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								sbSubject.append(subfield.getText()).append("--");
							}
							if ("y".equals(subfield.attributeValue("label"))) {
								sbSubject.append(subfield.getText()).append("--");
							}
							if ("z".equals(subfield.attributeValue("label"))) {
								sbSubject.append(subfield.getText()).append("--");
							}
							if ("x".equals(subfield.attributeValue("label"))) {
								sbSubject.append(subfield.getText()).append("--");
							}
							if ("j".equals(subfield.attributeValue("label"))) {
								sbSubject.append(subfield.getText()).append("--");
							}
						}
						String str = sbSubject.toString();
						if(str.length() > 2) 
							str = str.substring(0, str.length()-2);
						bookmap.put("subject"+i, str);
						i++;
					}
					if ("690".equals(varfield.attributeValue("id"))) {
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								String fenleihao = subfield.getText(); // 中图分类号
								bookmap.put("fenleihao", fenleihao);
							}
						}
					}
					
					if ("701".equals(varfield.attributeValue("id"))) {
						StringBuilder sbauthor = new StringBuilder();
						Iterator subject_iter = varfield.elementIterator();
						while (subject_iter.hasNext()) {
							Element subfield = (Element) subject_iter.next();
							if ("c".equals(subfield.attributeValue("label"))) {
								sbauthor.append(subfield.getText());
							}
							if ("a".equals(subfield.attributeValue("label"))) {
								sbauthor.append(subfield.getText());
							}
							if ("f".equals(subfield.attributeValue("label"))) {
								sbauthor.append(subfield.getText());
							}
							if ("4".equals(subfield.attributeValue("label"))) {
								sbauthor.append(subfield.getText());
							}
						}
						bookmap.put("authordetail"+j, sbauthor.toString());
						j++;
					}
					
					if ("702".equals(varfield.attributeValue("id"))) {
						String fjkm = "";
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								fjkm += subfield.getText();
							}
							if ("4".equals(subfield.attributeValue("label"))) {
								fjkm += subfield.getText();
							}
							bookmap.put("fjkm", fjkm);// 附加条款
						}
					}
					Olcclib sinlib = null;
					if ("SID".equals(varfield.attributeValue("id"))) {
						String d = "";
						String c = "";
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("b".equals(subfield.attributeValue("label"))) {
								sinlib = AppOlcc.mapOlcc.get(subfield.getText()) != null
										? (Olcclib) AppOlcc.mapOlcc.get(subfield.getText()).clone() : null;
								/*
								 * if (sinlib.getName().equals("国家图书馆")) {
								 * System.out.println("123"); }
								 */
							}
							if ("c".equals(subfield.attributeValue("label"))) {
								c = subfield.getText();
							}
							if ("d".equals(subfield.attributeValue("label"))) {
								d = subfield.getText();
							}
						}
						if (sinlib != null) {
							switch (sinlib.getType()) {
							case 1:
								sinlib.setUrl(
										sinlib.getUrl() + "/F?func=item-global&doc_library=" + c + "&doc_number=" + d);
								break;
							case 2:
								sinlib.setUrl(sinlib.getUrl()
										+ "/bookSearch?cmdACT=detailmarc&xsl=listdetailmarc.xsl&bookrecno=" + d);
								break;
							case 3:
								sinlib.setUrl(sinlib.getUrl() + "/cgi-bin/DispBibDetail?v_recno=" + d + "&v_curdbno=0");
								break;
							case 4:
								sinlib.setUrl(sinlib.getUrl() + "/websearch.asp?WCI=ShowInfo&ID=" + d + "&NETID=0");
								break;
							case 5:
								sinlib.setUrl(sinlib.getUrl() + "/opac/item.php?marc_no=" + d);
								break;
							case 6:
								sinlib.setUrl(sinlib.getUrl() + "uhtbin/cgisirsi/x/0/0/57/5?searchdata1=" + d
										+ "+%7bckey%7d&user_id=WEBSERVER&password=WEBSERVER");
								break;
							case 7:
								sinlib.setUrl(sinlib.getUrl() + "bookdetail.aspx?id=" + d);
								break;
							case 8:
								sinlib.setUrl(sinlib.getUrl() + "/search*chx/o?SEARCH=" + d);
								break;
							case 9:
								sinlib.setUrl(sinlib.getUrl() + "/HoldingRetr.aspx?BIBNO=" + d
										+ "&TABNAME=ILASBIBLIOS&DISP=Holding");
								break;
							case 10:
								sinlib.setUrl(sinlib.getUrl() + "/NTRdrBookRetrInfo.aspx?BookRecno=" + d);
								break;
							default:
								sinlib.setUrl("");
								break;
							}

							List<JSONObject> lstOlcc = null;
							if (maplib.containsKey(sinlib.getProvince())) {
								if(!libcode.contains(sinlib.getCode())) {
									lstOlcc = maplib.get(sinlib.getProvince());
									lstOlcc.add(JSONObject.fromObject(sinlib));
								}
							} else {
								lstOlcc = new ArrayList<JSONObject>();
								libcode.add(sinlib.getCode());
								lstOlcc.add(JSONObject.fromObject(sinlib));
								maplib.put(sinlib.getProvince(), lstOlcc);
							}
						}
					}
					if ("049".equals(varfield.attributeValue("id"))) {
						String d = "";
						String c = "";
						Iterator subfield_iter = varfield.elementIterator();
						while (subfield_iter.hasNext()) {
							Element subfield = (Element) subfield_iter.next();
							if ("a".equals(subfield.attributeValue("label"))) {
								sinlib = AppOlcc.mapOlcc.get(subfield.getText()) != null
										? (Olcclib) AppOlcc.mapOlcc.get(subfield.getText()).clone() : null;
							}
							if ("c".equals(subfield.attributeValue("label"))) {
								d = subfield.getText();
							}
							if ("d".equals(subfield.attributeValue("label"))) {
								c = subfield.getText();
							}
						}
						if (sinlib != null) {
							switch (sinlib.getType()) {
							case 1:
								sinlib.setUrl(
										sinlib.getUrl() + "/F?func=item-global&doc_library=" + c + "&doc_number=" + d);
								break;
							case 2:
								sinlib.setUrl(sinlib.getUrl()
										+ "/bookSearch?cmdACT=detailmarc&xsl=listdetailmarc.xsl&bookrecno=" + d);
								break;
							case 3:
								sinlib.setUrl(sinlib.getUrl() + "/cgi-bin/DispBibDetail?v_recno=" + d + "&v_curdbno=0");
								break;
							case 4:
								sinlib.setUrl(sinlib.getUrl() + "/websearch.asp?WCI=ShowInfo&ID=" + d + "&NETID=0");
								break;
							case 5:
								sinlib.setUrl(sinlib.getUrl() + "/opac/item.php?marc_no=" + d);
								break;
							case 6:
								sinlib.setUrl(sinlib.getUrl() + "uhtbin/cgisirsi/x/0/0/57/5?searchdata1=" + d
										+ "+%7bckey%7d&user_id=WEBSERVER&password=WEBSERVER");
								break;
							case 7:
								sinlib.setUrl(sinlib.getUrl() + "bookdetail.aspx?id=" + d);
								break;
							case 8:
								sinlib.setUrl(sinlib.getUrl() + "/search*chx/o?SEARCH=" + d);
								break;
							case 9:
								sinlib.setUrl(sinlib.getUrl() + "/HoldingRetr.aspx?BIBNO=" + d
										+ "&TABNAME=ILASBIBLIOS&DISP=Holding");
								break;
							case 10:
								sinlib.setUrl(sinlib.getUrl() + "/NTRdrBookRetrInfo.aspx?BookRecno=" + d);
								break;
							default:
								sinlib.setUrl("");
								break;
							}

							List<JSONObject> lstOlcc = null;
							if (maplib.containsKey(sinlib.getProvince())) {
								if(!libcode.contains(sinlib.getCode())) {
									lstOlcc = maplib.get(sinlib.getProvince());
									lstOlcc.add(JSONObject.fromObject(sinlib));
								}
							} else {
								lstOlcc = new ArrayList<JSONObject>();
								libcode.add(sinlib.getCode());
								lstOlcc.add(JSONObject.fromObject(sinlib));
								maplib.put(sinlib.getProvince(), lstOlcc);
							}
						}
					}
				}
				bookmap.put("guancang", JSONObject.fromObject(maplib).toString());
				l.add(bookmap);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		JSONObject job = new JSONObject();
		String count = map.get("no_records");
		if(StringUtils.isBlank(count)) {
			job.put("count", 0);
		}else {
			job.put("count", count);
		}
		job.put("data", JSONArray.fromObject(l));
		logger.info("【olcc/books单个库的响应：】" + job);
		return job;
	}

	public static JSON item_date(String base, String doc) {

		String _url = ucsUrl + "/X?op=item-data&base=" + base + "&doc-number=" + doc
				+ "&user_name=WWW-IOS&user_password=PWDIOS";
		System.out.println(_url);
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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(value);
		return null;
	}

	public static JSON getholding(String base, String doc) {

		String _url = "http://ucs.nlc.cn/F/?func=find-b&find_code=SYS&request=004425646&local_base=UCS01";

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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(value);
		return null;
	}

	public static String getUrl(String base, String doc, String url, int type) {
		String resUrl = "";
		switch (type) {
		case 1:
			resUrl = url + "/F?func=item-global&doc_library=" + base + "&doc_number=" + doc;
			break;
		case 2:
			resUrl = url + "/bookSearch?cmdACT=detailmarc&xsl=listdetailmarc.xsl&bookrecno=" + doc;
			break;
		case 3:
			resUrl = url + "/cgi-bin/DispBibDetail?v_recno=" + doc + "&v_curdbno=0";
			break;
		case 4:
			resUrl = url + "/websearch.asp?WCI=ShowInfo&ID=" + doc + "&NETID=0";
			break;
		case 5:
			resUrl = url + "/opac/item.php?marc_no=" + doc;
			break;
		case 6:
			resUrl = url + "uhtbin/cgisirsi/x/0/0/57/5?searchdata1=" + doc
					+ "+%7bckey%7d&user_id=WEBSERVER&password=WEBSERVER";
			break;
		case 7:
			resUrl = url + "bookdetail.aspx?id=" + doc;
			break;
		case 8:
			resUrl = url + "/search*chx/o?SEARCH=" + doc;
			break;
		case 9:
			resUrl = url + "/HoldingRetr.aspx?BIBNO=" + doc + "&TABNAME=ILASBIBLIOS&DISP=Holding";
			break;
		case 10:
			resUrl = url + "/NTRdrBookRetrInfo.aspx?BookRecno=" + doc;
			break;
		case 11:
			break;
		}
		return resUrl;
	}

}
