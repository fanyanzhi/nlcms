package cn.gov.nlc.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.OrderinfoMapper;
import cn.gov.nlc.pojo.Orderinfo;
import cn.gov.nlc.pojo.OrderinfoExample;
import cn.gov.nlc.pojo.OrderinfoExample.Criteria;
import cn.gov.nlc.pojo.OrderinfoExt;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.service.OrderinfoService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSONObject;

@Service
public class OrderinfoServiceImpl implements OrderinfoService {

	private static final String alephUrl = PropertiesUtils.getPropertyValue("aleph");
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.OrderinfoServiceImpl.class);

	@Autowired
	OrderinfoMapper orderinfoMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SysmessageService sysmessageService;

	@Override
	public void insertOrderinfo(Orderinfo orderinfo) {
		orderinfoMapper.insert(orderinfo);
	}

	/**
	 * 支付记录
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getOrderList(Integer page, Integer rows, String loginaccount, String name,
			String cardno, Date pstartDate, Date pendDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String sql = "select count(1) from orderinfo o where status = 1";
		List<Object> paralist = new ArrayList<Object>();
		if (StringUtils.isNotBlank(loginaccount)) {
			paralist.add(loginaccount + "%");
			sql += " and o.loginAccount like ? ";
		}

		if (StringUtils.isNotBlank(name)) {
			paralist.add(name + "%");
			sql += " and o.name like ? ";
		}

		if (StringUtils.isNotBlank(cardno)) {
			paralist.add(cardno + "%");
			sql += " and o.cardno like ? ";
		}

		if (null != pstartDate && null != pendDate) {
			paralist.add(sdf.format(pstartDate));
			paralist.add(sdf.format(pendDate));
			sql += " and o.time between ? and ? ";
		} else if (null != pstartDate) {
			paralist.add(sdf.format(pstartDate));
			sql += " and o.time >= ? ";
		} else if (null != pendDate) {
			paralist.add(sdf.format(pendDate));
			sql += " and o.time <= ? ";
		}
		Object[] param = paralist.toArray();
		int totalRow = jdbcTemplate.queryForInt(sql, param);

		int start = (page - 1) * rows;
		int limit = rows;
		String sql2 = "select o.*, u.rdRoleCode from orderinfo o left outer join nlcuser u on o.loginAccount = u.loginAccount where o.status = 1 ";
		if (StringUtils.isNotBlank(loginaccount)) {
			sql2 += " and o.loginAccount like ? ";
		}

		if (StringUtils.isNotBlank(name)) {
			sql2 += " and o.name like ? ";
		}

		if (StringUtils.isNotBlank(cardno)) {
			sql2 += " and o.cardno like ? ";
		}

		if (null != pstartDate && null != pendDate) {
			sql2 += " and o.time between ? and ? ";
		} else if (null != pstartDate) {
			sql2 += " and o.time >= ? ";
		} else if (null != pendDate) {
			sql2 += " and o.time <= ? ";
		}

		sql2 += " order by time desc limit " + start + " , " + limit;

		List<OrderinfoExt> list2 = jdbcTemplate.query(sql2, param,
				new BeanPropertyRowMapper<OrderinfoExt>(OrderinfoExt.class));

		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
		return result;
	}

	/**
	 * 导出excel用的list
	 */
	@Override
	public List<OrderinfoExt> getExportList(String loginaccount, String name, String cardno, Date pstartDate,
			Date pendDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select o.*, u.rdRoleCode from orderinfo o left outer join nlcuser u on o.loginAccount = u.loginAccount where o.status = 1 ";

		if (StringUtils.isNotBlank(loginaccount)) {
			sql += " and o.loginAccount like '" + loginaccount + "%' ";
		}

		if (StringUtils.isNotBlank(name)) {
			sql += " and o.name like '" + name + "%' ";
		}

		if (StringUtils.isNotBlank(cardno)) {
			sql += " and o.cardno like '" + cardno + "%' ";
		}

		if (null != pstartDate && null != pendDate) {
			sql += " and o.time between '" + sdf.format(pstartDate) + "' and '" + sdf.format(pendDate) + "' ";
		} else if (null != pstartDate) {
			sql += " and o.time >= '" + sdf.format(pstartDate) + "' ";
		} else if (null != pendDate) {
			sql += " and o.time <= '" + sdf.format(pendDate) + "' ";
		}

		sql += " order by time desc ";
		List<OrderinfoExt> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<OrderinfoExt>(OrderinfoExt.class));
		return list;
	}

	/**
	 * 通过订单号找订单
	 * 
	 * @param orderno
	 * @return
	 */
	@Override
	public Orderinfo selectByOrderno(String orderno) {
		OrderinfoExample example = new OrderinfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrdernoEqualTo(orderno);
		List<Orderinfo> list = orderinfoMapper.selectByExample(example);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更新
	 * 
	 * @param record
	 */
	@Override
	public void updateByPrimaryKeySelective(Orderinfo record) {
		orderinfoMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 更新aleph的押金状态
	 * 
	 * @param orderinfo
	 * @return
	 */
	@Override
	public JSONObject updateAlephPledge(Orderinfo orderinfo) {
		JSONObject result = new JSONObject();

		String loginAccount = orderinfo.getLoginaccount();
		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + loginAccount
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/xml");
		String resone = Common.sendGet(rdInfoUrl, headers);
		if (StringUtils.isBlank(resone)) {
			result.put("result", false);
			result.put("message", "aleph server wrong");
			return result;
		}

		String bor_type = "";
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(resone);
			Element root = doc.getRootElement();
			Element z305 = root.element("z305");
			if (null != z305) {
				bor_type = z305.elementText("z305-bor-type");
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "parse xml wrong");
			return result;
		}

		// 向aleph系统插入z305
		String borid = orderinfo.getBorid();
		String naOrforType = orderinfo.getType();
		String sub_library = "";
		String bor_status = "";
		String type = "";
		if ("national".equals(naOrforType)) {
			sub_library = "ZWWJ"; // 中文图书外借室读者
			bor_status = "15";
			type = "9021";
		} else if ("foreign".equals(naOrforType)) {
			sub_library = "WJCN"; // 外文图书外借读者
			bor_status = "21";
			type = "9013";
		}

		DateTime dt = new DateTime();
		dt = dt.plusYears(3);
		String expiry_date = dt.toString("yyyyMMdd");

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
		ressb.append("<z305-sub-library>" + sub_library + "</z305-sub-library>");
		ressb.append("<z305-bor-type>" + bor_type + "</z305-bor-type>");
		ressb.append("<z305-bor-status>" + bor_status + "</z305-bor-status>");
		ressb.append("<z305-registration-date>00000000</z305-registration-date>");
		ressb.append("<z305-expiry-date>" + expiry_date + "</z305-expiry-date>");
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
		logger.info("update-bor返回的信息：" + resstr);

		Document doc2 = null;
		try {
			doc2 = DocumentHelper.parseText(resstr);
			Element root = doc2.getRootElement();
			String error = root.elementText("error");
			if (error.indexOf("Succeeded") == -1) {
				result.put("result", false);
				result.put("message", "update-bor failure");
				return result;
			}

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "parse xml wrong");
			return result;
		}

		// update-cash
		String updateCashValue = "";
		String updateCashUrl = alephUrl + "/X?op=update-cash&UPDATE_FLAG=n&SUB_LIBRARY=NLC50&ID=" + borid + "&TYPE="
				+ type + "&STATUS=C&CREDIT_DEBIT=D&SUM=" + orderinfo.getSum().toPlainString()
				+ "&PAYMENT_CATALOGER=&PAYMENT_TARGET=NLC50&user_name=www-app&user_password=pwdapp";
		logger.info("【update-cash的url】" + updateCashUrl);

		URLConnection connection;
		URL url;
		try {
			url = new URL(updateCashUrl);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/xml");
			String sLine = "";
			InputStream l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sLine = l_reader.readLine()) != null) {
				updateCashValue += sLine + "\r\n";
			}
			l_urlStream.close();
			l_reader.close();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "aleph server wrong");
			return result;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "aleph server wrong");
			return result;
		}

		Document cashDoc = null;
		try {
			cashDoc = DocumentHelper.parseText(updateCashValue);
			Element root = cashDoc.getRootElement();
			Element replay = root.element("reply");
			if (null == replay) {
				result.put("result", false);
				result.put("message", "update-cash failure");
				return result;
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "parse xml wrong");
			return result;
		}

		try {
			Sysmessage sysmessage = new Sysmessage();
			JSONObject jsono = new JSONObject();
			jsono.put("orderno", orderinfo.getOrderno()); // 商户订单号
			jsono.put("tradeno", orderinfo.getTradeno()); // 支付宝订单号
			jsono.put("date", (new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));

			if ("national".equalsIgnoreCase(orderinfo.getType())) {
				sysmessage.setTitle("中文押金支付成功");
				jsono.put("item", "中文押金");
				jsono.put("sum", orderinfo.getSum().toPlainString());
			} else if ("foreign".equalsIgnoreCase(orderinfo.getType())) {
				sysmessage.setTitle("外文押金支付成功");
				jsono.put("item", "外文押金");
				jsono.put("sum", orderinfo.getSum().toPlainString());
			}
			sysmessage.setMessage(jsono.toString());
			sysmessage.setPubname("system");// 系统
			sysmessage.setType((byte) 1); // 交易记录
			sysmessage.setTime(new Date());
			sysmessage.setUsername(loginAccount);
			sysmessageService.insertMessage(sysmessage);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		result.put("result", true);
		return result;
	}

}
