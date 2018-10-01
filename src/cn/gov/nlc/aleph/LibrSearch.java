package cn.gov.nlc.aleph;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import cn.gov.nlc.util.Common;
import net.sf.json.JSONObject;

public class LibrSearch implements Callable<JSONObject> {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.aleph.LibrSearch.class);
	
	private String url;
	private String name;
	private String code;
	
	public LibrSearch(String url, String name, String code) {
		this.url = url;
		this.name = name;
		this.code = code;
	}

	@Override
	public JSONObject call() throws Exception {
		JSONObject resobj = new JSONObject();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/text");
		String resone = Common.sendGet(url, headers);
		if (StringUtils.isNotBlank(resone)) {
			Document doc = null;
			try {
				doc = DocumentHelper.parseText(resone);
				Element root = doc.getRootElement();// 获取根节点
				Element error = root.element("error");
				if (null == error) {
					String no_records = root.elementText("no_records");
					if (null != no_records) {
						int res = Integer.parseInt(no_records);
						if (res > 0) {
							resobj.put("name", name);
							resobj.put("code", code);
							resobj.put("num", res);
							return resobj;
						}
					}
				}
			} catch (DocumentException e) {
				logger.error("【查询opac分馆】" +e.getMessage(), e);
			}
		}
		
		return null;
	}
}
