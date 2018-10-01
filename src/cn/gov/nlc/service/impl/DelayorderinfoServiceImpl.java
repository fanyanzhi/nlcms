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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.DelayorderinfoMapper;
import cn.gov.nlc.pojo.DelayOrderinfoExt;
import cn.gov.nlc.pojo.Delayorderinfo;
import cn.gov.nlc.pojo.DelayorderinfoExample;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.pojo.DelayorderinfoExample.Criteria;
import cn.gov.nlc.pojo.Delayorderinfodetail;
import cn.gov.nlc.pojo.OrderinfoExt;
import cn.gov.nlc.service.DelayorderinfoService;
import cn.gov.nlc.service.DelayorderinfodetailService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.util.PropertiesUtils;
import cn.gov.nlc.vo.EasyUiDataGridResult;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class DelayorderinfoServiceImpl implements DelayorderinfoService {

	private static final String alephUrl = PropertiesUtils.getPropertyValue("aleph");
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.DelayorderinfoServiceImpl.class);

	@Autowired
	private DelayorderinfoMapper delayorderinfoMapper;
	@Autowired
	private SysmessageService sysmessageService;
	@Autowired
	private DelayorderinfodetailService delayorderinfodetailService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 插入
	 */
	@Override
	public void insert(Delayorderinfo delayorderinfo) {
		delayorderinfoMapper.insert(delayorderinfo);
	}

	/**
	 * 更改订单的状态
	 * 
	 * @param orderno
	 * @param tradeno
	 */
	@Override
	public void updateStatus(String orderno, String tradeno) {
		Delayorderinfo info = new Delayorderinfo();
		info.setTradeno(tradeno);
		info.setStatus((byte) 1);
		DelayorderinfoExample example = new DelayorderinfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrdernoEqualTo(orderno);
		delayorderinfoMapper.updateByExampleSelective(info, example);
	}

	/**
	 * 获取用户欠滞纳金的详情
	 * 
	 * @param loginAccount
	 * @return
	 */
	@Override
	public JSONObject delayCashDetail(String loginAccount) {
		JSONObject result = new JSONObject();

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
			result.put("result", false);
			result.put("message", "aleph server wrong");
			return result;

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "aleph server wrong");
			return result;

		}
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
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
				String z31_description = z31.elementText("z31-description");
				String z31_sum = z31.elementText("z31-sum");
				String z31_date = z31.elementText("z31-date");
				String z31_status = z31.elementText("z31-status");
				String z31_sequence = z31.elementText("z31-sequence");
				if ("Paid".equals(z31_status)) // 已付
					continue;

				Date mydue = sdf2.parse(z31_date);
				z31_date = sdf1.format(mydue);
				Element z30 = item.element("z30");
				if (z30 == null) {
					map.put("z31_sequence", z31_sequence==null?"":z31_sequence);
					map.put("due_date", z31_date==null?"":z31_date);
					map.put("call_no", "");
					map.put("tag", "");
					map.put("year", "");
					map.put("author", "");
					map.put("title", "");
					map.put("imprint", "");
					map.put("xxx_tag", "");
					map.put("z30_barcode", "");
					map.put("z36_doc_number", "");
					map.put("z31_description", z31_description==null?"":z31_description);
					map.put("z31_sum", z31_sum==null?"":z31_sum);
					map.put("z31_status", z31_status==null?"":z31_status);
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

				map.put("z31_sequence", z31_sequence==null?"":z31_sequence);
				map.put("due_date", z31_date==null?"":z31_date);
				map.put("call_no", call_no==null?"":call_no);
				map.put("tag", tag==null?"":tag);
				map.put("year", year==null?"":year);
				map.put("author", author==null?"":author);
				map.put("title", title==null?"":title);
				map.put("imprint", imprint==null?"":imprint);
				map.put("xxx_tag", tag==null?"":tag);
				map.put("z30_barcode", z30_barcode==null?"":z30_barcode);
				map.put("z36_doc_number", z13_doc_number==null?"":z13_doc_number);
				map.put("z31_status", z31_status==null?"":z31_status);
				map.put("z31_description", z31_description==null?"":z31_description);
				map.put("z31_sum", z31_sum==null?"":z31_sum);
				l.add(map);
			}

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "parse xml wrong");
			return result;

		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "parse xml wrong");
			return result;

		}
		JSON j = JSONArray.fromObject(l);
		result.put("result", true);
		result.put("data", j);
		return result;
	}

	/**
	 * 更新滞纳金状态
	 * 
	 * @param borId
	 * @param ip
	 * @param sum
	 * @return
	 */
	@Override
	public JSONObject updateDelayCash(String loginAccount, String borId, String ip, String orderno, String tradeno, String allSum) {
		JSONObject result = new JSONObject();

		String seqvalue = "";
		String rdInfoUrl = alephUrl + "/X?op=bor-info&bor_id=" + loginAccount
				+ "&bor_id_type=00&user_name=www-app&user_password=pwdapp";
		URLConnection reconnection;
		URL reurl;
		try {
			reurl = new URL(rdInfoUrl);
			reconnection = reurl.openConnection();
			reconnection.setRequestProperty("Content-Type", "application/xml");
			String sLine = "";
			InputStream l_urlStream = reconnection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream, "UTF-8"));
			while ((sLine = l_reader.readLine()) != null) {
				seqvalue += sLine + "\r\n";
			}
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

		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(seqvalue);
			Element root = doc.getRootElement();
			Iterator itemIter = root.elementIterator("fine");
			while (itemIter.hasNext()) {
				Element item = (Element) itemIter.next();
				Map<String, String> map = new HashMap<String, String>();
				Element z31 = item.element("z31");
				String z31_sum = z31.elementText("z31-sum");
				String z31_status = z31.elementText("z31-status");
				String z31_sequence = z31.elementText("z31-sequence");
				String z31_date = z31.elementText("z31-date");
				if ("Paid".equals(z31_status)) // 已付
					continue;

				try {
					Date mydue = sdf2.parse(z31_date);
					z31_date = sdf1.format(mydue);
					map.put("z31_date", z31_date);
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
				map.put("z31_sequence", z31_sequence);
				map.put("z31_sum", z31_sum);
				
				Element z13 = item.element("z13");
				String title = z13.elementText("z13-title");
				map.put("title", title);
				l.add(map);
			}

		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
			result.put("result", false);
			result.put("message", "parse xml wrong");
			return result;
		}
		

		if (l.size() > 0) {
			for (Map<String, String> map : l) {
				String sequence = map.get("z31_sequence");
				String sum = map.get("z31_sum");
				String date = map.get("z31_date");
				String title = map.get("title");
				
				String dsurl = alephUrl + "/X?" + "op=update-cash&update_flag=U&id=" + borId + "&sequence=" + sequence
						+ "&type=0003&status=C&credit_debit=D&sum=" + sum + "&payment_ip=" + ip
						+ "&sub_library=NLC50&payment_target=NLC50&user_name=www-app&user_password=pwdapp";

				URLConnection connection;
				URL url;
				String value = "";
				try {
					url = new URL(dsurl);
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
					result.put("result", false);
					result.put("message", "aleph update fail, please connect administrator");
					return result;
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					result.put("result", false);
					result.put("message", "aleph update fail, please connect administrator");
					return result;
				}
				
				Document docx = null;
				try {
					docx = DocumentHelper.parseText(value);
					Element root = docx.getRootElement();
					String reply = root.elementText("reply");
					if("ok".equalsIgnoreCase(reply)) {
						Delayorderinfodetail vo = new Delayorderinfodetail();
						vo.setOrderno(orderno);
						vo.setZ31Sequence(sequence);
						vo.setBook(title);
						vo.setAmount(sum);
						
						try {
							Date due_date = sdf1.parse(date);
							vo.setDueDate(due_date);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						delayorderinfodetailService.insert(vo);
					}else {
						logger.info("【解析更新滞纳金状态返回值时，返回错误】");
						result.put("result", false);
						result.put("message", "aleph update fail, please connect administrator");
						return result;
					}

				} catch (DocumentException e) {
					logger.error(e.getMessage(), e);
					result.put("result", false);
					result.put("message", "parse xml wrong");
					return result;
				}
				
			}
			
			
			try {
				Sysmessage sysmessage = new Sysmessage();
				sysmessage.setType((byte) 1); // 交易记录
				sysmessage.setTitle("滞纳金支付成功");
				JSONObject jsono = new JSONObject();
				jsono.put("orderno", orderno);	//商户订单号
				jsono.put("tradeno", tradeno);	//支付宝订单号
				jsono.put("sum", allSum);		//金额
				List<Delayorderinfodetail> detailList = delayorderinfodetailService.getListByOrderno(orderno);
				JSONArray array = JSONArray.fromObject(detailList);
				jsono.put("data", array);
				sysmessage.setMessage(jsono.toString());
				
				sysmessage.setPubname("system");// 系统
				sysmessage.setUsername(loginAccount);
				sysmessage.setTime(new Date());
				sysmessageService.insertMessage(sysmessage);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			
			result.put("result", true);
			return result;
		} else { // 无欠费
			result.put("result", true);
			result.put("message", "client no debt");
			return result;
		}
	}

	/**
	 * 支付记录
	 */
	@Override
	public EasyUiDataGridResult getOrderList(Integer page, Integer rows, String loginaccount, String name, String cardno, Date pstartDate, Date pendDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select count(1) from delayorderinfo o where status = 1";
		List<Object> paralist = new ArrayList<Object>();
		
		if (StringUtils.isNotBlank(loginaccount)) {
			paralist.add(loginaccount+"%");
			sql += " and o.loginAccount like ? ";
		}
		
		if (StringUtils.isNotBlank(name)) {
			paralist.add(name+"%");
			sql += " and o.name like ? ";
		}
		
		if (StringUtils.isNotBlank(cardno)) {
			paralist.add(cardno+"%");
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
		String sql2 = "select o.*, u.rdRoleCode from delayorderinfo o left outer join nlcuser u on o.loginAccount = u.loginAccount where o.status = 1 ";
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
		
		List<DelayOrderinfoExt> list2 = jdbcTemplate.query(sql2, param, new BeanPropertyRowMapper<DelayOrderinfoExt>(DelayOrderinfoExt.class));
		
		EasyUiDataGridResult result = new EasyUiDataGridResult(totalRow, list2);
		return result;
	}

	/**
	 * 导出excel
	 */
	@Override
	public List<DelayOrderinfoExt> getExportList(String loginaccount, String name, String cardno, Date pstartDate, Date pendDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select o.*, u.rdRoleCode from delayorderinfo o left outer join nlcuser u on o.loginAccount = u.loginAccount where o.status = 1 ";

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
		List<DelayOrderinfoExt> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<DelayOrderinfoExt>(DelayOrderinfoExt.class));
		return list;
	}

	/**
	 * 通过商户订单号取得
	 * @param orderno
	 * @return
	 */
	@Override
	public Delayorderinfo getByOrderno(String orderno) {
		DelayorderinfoExample example = new DelayorderinfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrdernoEqualTo(orderno);
		List<Delayorderinfo> list = delayorderinfoMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
}
