package cn.gov.nlc.aleph;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import cn.gov.nlc.controller.app.OpacSearchController;
import cn.gov.nlc.util.PropertiesUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EveryOpacSearch {

	public static final List<Map<String, String>> librarycnList = new ArrayList<Map<String, String>>();
	public static final List<Map<String, String>> libraryabList = new ArrayList<Map<String, String>>();

	static {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = OpacSearchController.class.getResourceAsStream("/properties/librarycn.properties");
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (null != props) {
			Set<Entry<Object, Object>> entrySet = props.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", (String) entry.getKey());
				map.put("code", (String) entry.getValue());
				librarycnList.add(map);
			}
		}

		Properties propsab = new Properties();
		InputStream inab = null;
		try {
			inab = OpacSearchController.class.getResourceAsStream("/properties/libraryab.properties");
			propsab.load(inab);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != inab) {
				try {
					inab.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (null != propsab) {
			Set<Entry<Object, Object>> entrySet = propsab.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", (String) entry.getKey());
				map.put("code", (String) entry.getValue());
				libraryabList.add(map);
			}
		}

	}

	private static String alephUrl = PropertiesUtil.getPropValue("properties/configuration.properties", "aleph");
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.aleph.EveryOpacSearch.class);

	/**
	 * 查所有分馆
	 * 
	 * @param query
	 * @param find_base
	 * @param adjacent
	 * @return
	 */
	public static JSONArray everyFind(JSONObject query, String find_base, String adjacent) {
		JSONArray resArray = new JSONArray();
		List<Map<String, String>> libraryList = new ArrayList<Map<String, String>>();

		if (find_base.contains("1")) {
			libraryList.addAll(librarycnList);	//中文
		}else {
			libraryList.addAll(libraryabList);
		}

		List<Map<String, String>> param = new ArrayList<Map<String, String>>();
		for (Map<String, String> library : libraryList) {
			StringBuilder request = new StringBuilder();
			String codes = library.get("code");
			if (codes.indexOf(",") > -1) {
				codes = codes.replace(",", " or ");
			}
			request.append("WSL=(" + codes + ") and ");

			if (null != query) {
				for (Object obj : query.keySet()) {
					String key = (String) obj;
					String value = query.getString(key);
					if (StringUtils.isBlank(value)) {
						continue;
					}
					if (key.equalsIgnoreCase("WYR")) { // 年份
						request.append(key + "=" + value + " and ");
					} else if (key.equalsIgnoreCase("WSL")) {
						continue;
					} else {
						request.append(key + "=(" + value + ") and ");
					}
				}
			}
			request.delete(request.length() - 5, request.length());

			String _url = "";
			try {
				_url = alephUrl + "/X?op=find&request=" + java.net.URLEncoder.encode(request.toString(), "utf-8")
						+ "&base=" + find_base + "&user_name=www-app&user_password=pwdapp" + "&adjacent=" + adjacent;
				
				Map<String, String> para = new HashMap<String, String>();
				para.put("name", library.get("name"));
				para.put("code", codes);
				para.put("url", _url);
				param.add(para);
			} catch (Exception e) {
			}
		}
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<JSONObject>> resultList = new ArrayList<Future<JSONObject>>(); 
		
		for (Map<String, String> paras : param) {
			String urls = paras.get("url");
			String names = paras.get("name");
			String codes = paras.get("code");
			LibrSearch search = new LibrSearch(urls, names, codes);
			Future<JSONObject> future = executorService.submit(search);
			resultList.add(future);
		}
		
		executorService.shutdown();  
		
		for (Future<JSONObject> fs : resultList) {
			try {
				JSONObject js = fs.get();
				if(null != js) {
					resArray.add(js);
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			} catch (ExecutionException e) {
				executorService.shutdownNow();  
				logger.error(e.getMessage(), e);
			}
		}
		
		return resArray;
	}

}
