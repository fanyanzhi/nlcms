package cn.gov.nlc.service.impl;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.gov.nlc.aleph.Aleph;
import cn.gov.nlc.pojo.Infosetup;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.Nlcnoticeannex;
import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.Nlcsubjectcatalog;
import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.pojo.Useralert;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.service.InfosetupService;
import cn.gov.nlc.service.NlcnewsService;
import cn.gov.nlc.service.NlcnoticeService;
import cn.gov.nlc.service.NlcnoticeannexService;
import cn.gov.nlc.service.NlcsubjectService;
import cn.gov.nlc.service.NlcsubjectcatalogService;
import cn.gov.nlc.service.NlctrailerService;
import cn.gov.nlc.service.NlcuserService;
import cn.gov.nlc.service.SysmessageService;
import cn.gov.nlc.service.UseralertService;
import cn.gov.nlc.service.WjreaderService;
import cn.gov.nlc.test.mobileapi;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.Jdpush;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.Owe;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ScheduleServiceImpl {

	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.ScheduleServiceImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private WjreaderService wjreaderService;

	@Autowired
	private NlcnewsService nlcnewsService;

	@Autowired
	private NlcnoticeService nlcnoticeService;

	@Autowired
	private NlcnoticeannexService nlcnoticeannexService;

	@Autowired
	private NlcsubjectService nlcsubjectService;

	@Autowired
	private NlcsubjectcatalogService nlcsubjectcatalogService;

	@Autowired
	private NlctrailerService nlctrailerService;

	@Autowired
	private UseralertService useralertService;

	@Autowired
	private InfosetupService infosetupService;

	@Autowired
	private NlcuserService nlcuserService;

	@Autowired
	private SysmessageService sysmessageService;

	/**
	 * 统计昨天新增的用户数(统计nlcuser)
	 */
	@SuppressWarnings("deprecation")
	public void newUserAccount() {
		String sql = "select count(1) from nlcuser where inserttime >= date_sub(curdate(),interval 1 day) and inserttime < curdate()";
		int result = jdbcTemplate.queryForInt(sql);

		String sql2 = "select count(1) from schenewuser where time = date_sub(curdate(),interval 1 day)";
		int result2 = jdbcTemplate.queryForInt(sql2);
		if (1 == result2) {
			logger.info("schenewuser已经插入过了");
			return;
		}

		String sql3 = "insert into schenewuser(time, count) values(date_sub(curdate(),interval 1 day), " + result + ")";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * 统计昨天的app启动次数，插入scheappstart表
	 */
	@SuppressWarnings("deprecation")
	public void appstart() {
		String sql = "select count(1) from appstart where time >= date_sub(curdate(),interval 1 day) and time < curdate()";
		int result = jdbcTemplate.queryForInt(sql);

		String sql2 = "select count(1) from scheappstart where time = date_sub(curdate(),interval 1 day)";
		int result2 = jdbcTemplate.queryForInt(sql2);
		if (1 == result2) {
			logger.info("scheappstart已经插入过了");
			return;
		}

		String sql3 = "insert into scheappstart(time, num) values(date_sub(curdate(),interval 1 day), " + result + ")";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * 插入或update虚拟用户数据
	 */
	@SuppressWarnings("deprecation")
	public void virtualUserNum() {
		// 计算昨天新增的虚拟用户的数量
		String sql = "SELECT count(1) FROM nlcuser where rdRoleCode = '0000' and inserttime >= date_sub(curdate(),interval 1 day) and inserttime < curdate()";
		int result = jdbcTemplate.queryForInt(sql);

		String sql2 = "select count(1) from schevirusernum where time = date_sub(curdate(),interval 1 day)";
		int result2 = jdbcTemplate.queryForInt(sql2);
		if (1 == result2) {
			logger.info("schevirusernum已经插入过了");
			return;
		}

		String sql3 = "insert into schevirusernum(time, num) values(date_sub(curdate(),interval 1 day), " + result
				+ ")";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * 插入省、市的新增用户数统计 schelocation
	 */
	public void locationNum() {
		String sql = "select count(1) from schelocation where time = date_sub(curdate(),interval 1 day)";
		int res = jdbcTemplate.queryForInt(sql);
		if (res > 0) {
			logger.info("schelocation已经插入过了");
			return;
		}

		// 插入省统计信息
		String sql2 = "insert into schelocation(num, location, time, type) SELECT count(1), IFNULL(province,'未知省') location, date_sub(curdate(),interval 1 day) time, 1 FROM `nlcuser` where inserttime >= date_sub(curdate(),interval 1 day) and inserttime < curdate() group by province";
		jdbcTemplate.execute(sql2);

		// 插入市统计信息
		String sql3 = "insert into schelocation(num, location, time, type) SELECT count(1), IFNULL(city,'未知市') location, date_sub(curdate(),interval 1 day) time, 0 FROM `nlcuser` where inserttime >= date_sub(curdate(),interval 1 day) and inserttime < curdate() group by city";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * 新增实名用户数据插入scherealnameusernum，通过统计昨日的nlcuser JS0001
	 */
	@SuppressWarnings("deprecation")
	public void realnameUserNum() {
		// 计算昨天新增的实名用户的数量
		String sql = "SELECT count(1) FROM nlcuser where rdRoleCode = 'JS0001' and inserttime >= date_sub(curdate(),interval 1 day) and inserttime < curdate()";
		int result = jdbcTemplate.queryForInt(sql);

		String sql2 = "select count(1) from scherealnameusernum where time = date_sub(curdate(),interval 1 day)";
		int result2 = jdbcTemplate.queryForInt(sql2);
		if (1 == result2) {
			logger.info("scherealnameusernum已经插入过了");
			return;
		}

		String sql3 = "insert into scherealnameusernum(time, num) values(date_sub(curdate(),interval 1 day), " + result
				+ ")";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * 新增持卡用户数据插入schecardusernum，通过统计昨日的nlcuser JS0002
	 */
	@SuppressWarnings("deprecation")
	public void cardUserNum() {
		// 计算昨天新增的持卡用户的数量
		String sql = "SELECT count(1) FROM nlcuser where rdRoleCode = 'JS0002' and inserttime >= date_sub(curdate(),interval 1 day) and inserttime < curdate()";
		int result = jdbcTemplate.queryForInt(sql);

		String sql2 = "select count(1) from schecardusernum where time = date_sub(curdate(),interval 1 day)";
		int result2 = jdbcTemplate.queryForInt(sql2);
		if (1 == result2) {
			logger.info("schecardusernum已经插入过了");
			return;
		}

		String sql3 = "insert into schecardusernum(time, num) values(date_sub(curdate(),interval 1 day), " + result
				+ ")";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * 统计昨天的预约数，插入scheholdrecord表
	 */
	@SuppressWarnings("deprecation")
	public void holdrecordnum() {
		String sql = "select count(1) from holdrecord where time >= date_sub(curdate(),interval 1 day) and time < curdate()";
		int result = jdbcTemplate.queryForInt(sql);

		String sql2 = "select count(1) from scheholdrecord where time = date_sub(curdate(),interval 1 day)";
		int result2 = jdbcTemplate.queryForInt(sql2);
		if (1 == result2) {
			logger.info("scheholdrecord已经插入过了");
			return;
		}

		String sql3 = "insert into scheholdrecord(time, num) values(date_sub(curdate(),interval 1 day), " + result
				+ ")";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * 统计昨天的续借数，插入scherenewrecord表
	 */
	@SuppressWarnings("deprecation")
	public void renewrecordnum() {
		String sql = "select count(1) from renewrecord where time >= date_sub(curdate(),interval 1 day) and time < curdate()";
		int result = jdbcTemplate.queryForInt(sql);

		String sql2 = "select count(1) from scherenewrecord where time = date_sub(curdate(),interval 1 day)";
		int result2 = jdbcTemplate.queryForInt(sql2);
		if (1 == result2) {
			logger.info("scheholdrecord已经插入过了");
			return;
		}

		String sql3 = "insert into scherenewrecord(time, num) values(date_sub(curdate(),interval 1 day), " + result
				+ ")";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * 统计昨天的app启动次数去重的，插入scheappstartqc表
	 */
	@SuppressWarnings("deprecation")
	public void appstartqc() {
		String sql2 = "select count(1) from scheappstartqc where time = date_sub(curdate(),interval 1 day)";
		int result2 = jdbcTemplate.queryForInt(sql2);
		if (1 == result2) {
			logger.info("scheappstartqc已经插入过了");
			return;
		}

		String sql = "select count(1) from (select clientid from  appstart where time >= date_sub(curdate(),interval 1 day) and time < curdate() group by clientid) temp";
		int result = jdbcTemplate.queryForInt(sql);

		String sql3 = "insert into scheappstartqc(time, num) values(date_sub(curdate(),interval 1 day), " + result
				+ ")";
		jdbcTemplate.execute(sql3);
	}

	/**
	 * app统计页面，新增装机的每日插入scheappxz，源自appstatist
	 */
	@SuppressWarnings("deprecation")
	public void appxz() {
		String sql = "select count(1) from scheappxz where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheappxz已经插入过了");
			return;
		}

		String sql2 = "select count(1) from appstatist where  time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'andro%'";
		int result2 = jdbcTemplate.queryForInt(sql2);

		String sql3 = "select count(1) from appstatist where  time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'iphone%'";
		int result3 = jdbcTemplate.queryForInt(sql3);

		String sql4 = "insert into scheappxz(time, num, type) values(date_sub(curdate(),interval 1 day), " + result2
				+ ", 'android')";
		jdbcTemplate.execute(sql4);

		String sql5 = "insert into scheappxz(time, num, type) values(date_sub(curdate(),interval 1 day), " + result3
				+ ", 'iphone')";
		jdbcTemplate.execute(sql5);
	}

	/**
	 * app统计页面，更新量的每日插入scheappgx，源自apprenew
	 */
	@SuppressWarnings("deprecation")
	public void appgx() {
		String sql = "select count(1) from scheappgx where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheappgx已经插入过了");
			return;
		}

		String sql2 = "select count(1) from apprenew where  time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'andro%'";
		int result2 = jdbcTemplate.queryForInt(sql2);

		String sql3 = "select count(1) from apprenew where  time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'iphone%'";
		int result3 = jdbcTemplate.queryForInt(sql3);

		String sql4 = "insert into scheappgx(time, num, type) values(date_sub(curdate(),interval 1 day), " + result2
				+ ", 'android')";
		jdbcTemplate.execute(sql4);

		String sql5 = "insert into scheappgx(time, num, type) values(date_sub(curdate(),interval 1 day), " + result3
				+ ", 'iphone')";
		jdbcTemplate.execute(sql5);
	}

	/**************************** 手机门户接口 *********************************************/

	/**
	 * 每天八点获取 task 12
	 */

	public void getTodayWenJin() {
		String wenjin = Common.sendGet("http://m.nlc.cn/client/subjectContentToday?id=ff80808151e7dfd10151eb8a288402e7",
				null);
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-shiju-yl\\\"><p>")
				+ "<div class=\\\"wjjdsd-shiju-yl\\\"><p>".length());
		String shiju = wenjin.substring(0, wenjin.indexOf("</p>"));

		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String sjsource = wenjin.substring(0, wenjin.indexOf("</p>"));

		wenjin = wenjin.substring(
				wenjin.indexOf("<div class=\\\"shiju-ywcont\\\">") + "<div class=\\\"shiju-ywcont\\\">".length());
		String sjyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));

		int idiv = 0;
		String quanshi = "";
		if (wenjin.indexOf("shiju-qscont") > 0 && wenjin.indexOf("shiju-qscont") < 200) {
			wenjin = wenjin.substring(
					wenjin.indexOf("<div class=\\\"shiju-qscont\\\">") + "<div class=\\\"shiju-qscont\\\">".length());
			idiv = wenjin.indexOf("</div>");
			quanshi = wenjin.substring(0, idiv);
		}

		wenjin = wenjin.substring(idiv + 6);
		String sjurl = "";
		if (wenjin.indexOf("http://") < 200) {
			wenjin = wenjin.substring(wenjin.indexOf("http://"));

			if (wenjin.indexOf(".mp3") > 0 && wenjin.indexOf(".mp3") < 100) {
				sjurl = wenjin.substring(0, wenjin.indexOf(".mp3") + 4);
			}
			if (wenjin.indexOf(".3gp") > 0 && wenjin.indexOf(".3gp") < 100) {
				sjurl = wenjin.substring(0, wenjin.indexOf(".3gp") + 4);
			}
		}
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-geyan-yl\\\"><p>")
				+ "<div class=\\\"wjjdsd-geyan-yl\\\"><p>".length());
		String geyan = wenjin.substring(0, wenjin.indexOf("</p>"));

		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String gysource = wenjin.substring(0, wenjin.indexOf("</p>"));

		wenjin = wenjin.substring(
				wenjin.indexOf("<div class=\\\"geyan-ywcont\\\">") + "<div class=\\\"geyan-ywcont\\\">".length());
		String gyyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));

		Wjreader wjreader = new Wjreader();
		wjreader.setPid("ff808081519e421901519edfb677013e");
		wjreader.setShiju(shiju);
		wjreader.setSjsource(sjsource);
		wjreader.setSjyiwen(sjyiwen);
		wjreader.setQuanshi(quanshi);
		wjreader.setSjurl(sjurl);
		wjreader.setGeyan(geyan);
		wjreader.setGysource(gysource);
		wjreader.setGyyiwen(gyyiwen);
		wjreader.setWjdate(new Date());
		Calendar cal = Calendar.getInstance();
		wjreader.setWjmonth((cal.get(Calendar.MONTH) + 1) + "");
		wjreader.setWjyear(cal.get(Calendar.YEAR) + "");
		wjreader.setSource("手机门户");
		wjreader.setStatus("已发布");
		wjreader.setBkpubtime(new Date());
		Jdpush.pushMessage(2, "wen_jin", "文津经典诵读：" + wjreader.getShiju(), "国家图书馆", "wenjin", "");
		wjreaderService.insertWjreader(wjreader);

	}

	/**
	 * 每天下午三点获取 task 13
	 */
	public void getNlcMobileNews() {
		int curpage = 1;
		boolean bContinue = true;
		while (bContinue) {
			String strNews = mobileapi.testRPC("getNewsTitles", "http://webservice.nlcm.neusoft.com",
					new Object[] { curpage, 10 });

			JSONArray arrNews = null;
			if (StringUtils.isNotBlank(strNews)) {
				arrNews = JSONArray.fromObject(strNews);
			} else {
				break;
			}

			if (null != arrNews && arrNews.size() > 0) {
				for (int i = 0; i < arrNews.size(); i++) {
					JSONObject jsonNews = JSONObject.fromObject(arrNews.get(i));
					String detail = mobileapi.testRPC("getNewsDetails", "http://webservice.nlcm.neusoft.com",
							new Object[] { jsonNews.get("id") });

					if (StringUtils.isBlank(detail)) {
						continue;
					}
					JSONObject detailNews = JSONObject.fromObject(detail);
					Nlcnews nlcnews = new Nlcnews();
					nlcnews.setNewsid(jsonNews.get("id").toString());
					nlcnews.setTitle(jsonNews.get("title").toString());
					nlcnews.setSrc(jsonNews.get("src").toString().indexOf("http://") > -1
							? jsonNews.get("src").toString() : "http://m.nlc.cn" + jsonNews.get("src").toString());
					if (!Common.IsNullOrEmpty(jsonNews.getString("pub_time")))
						nlcnews.setPubTime(
								Common.ConvertToDate(jsonNews.get("pub_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnews.setSource("手机门户");
					nlcnews.setStatus("已发布");
					nlcnews.setSubPerson(detailNews.get("sub_person").toString());
					nlcnews.setContent(detailNews.get("content").toString().replace("\n", "").replace("\r", "")
							.replace("\n\r", "").replace("'", "\\\'"));
					if (!Common.IsNullOrEmpty(detailNews.getString("sub_time")))
						nlcnews.setSubTime(
								Common.ConvertToDate(detailNews.get("sub_time").toString(), "yyyy-MM-dd HH:mm"));
					if (!Common.IsNullOrEmpty(detailNews.getString("upd_time")))
						nlcnews.setUpdTime(
								Common.ConvertToDate(detailNews.get("upd_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnews.setPraisecount(0);
					nlcnews.setCollectcount(0);
					nlcnews.setTime(new Date());
					nlcnews.setBkpbtime(new Date());

					Date subTime = nlcnews.getSubTime();
					Date pubTime = nlcnews.getPubTime();
					if (null == subTime) {
						nlcnews.setSubTime(new Date());
					}
					if (null == pubTime) {
						nlcnews.setPubTime(new Date());
					}
					try {
						if (nlcnewsService.getNlcnewsByNewsId(nlcnews.getNewsid()) != null) {
							bContinue = false;
							break;
						}
						nlcnews.setTops(0);
						nlcnewsService.insertNews(nlcnews);
					} catch (Exception e) {
						logger.error("nlcnews-->" + nlcnews.getNewsid() + "-->" + nlcnews.getTitle() + e.getMessage());
					}
				}
			} else {
				break;
			}
			curpage++;
		}
	}

	/**
	 * 公告 task 14
	 */
	public void getNlcMobileNotice() {
		int curpage = 1;
		boolean bcontinue = true;
		while (bcontinue) {
			String strNotice = mobileapi.testRPC("getNoticeTitles", "http://webservice.nlcm.neusoft.com",
					new Object[] { curpage, 10 });
			if (StringUtils.isBlank(strNotice)) {
				break;
			}

			JSONArray arrNotice = JSONArray.fromObject(strNotice);
			if (arrNotice.size() > 0) {
				for (int i = 0; i < arrNotice.size(); i++) {
					JSONObject jsonNotice = JSONObject.fromObject(arrNotice.get(i));
					String detail = mobileapi.testRPC("getNoticeDetails", "http://webservice.nlcm.neusoft.com",
							new Object[] { jsonNotice.get("id") });

					if (StringUtils.isBlank(detail)) {
						continue;
					}
					JSONObject detailNotice = JSONObject.fromObject(detail);
					Nlcnotice nlcnotice = new Nlcnotice();
					nlcnotice.setNoticeid(jsonNotice.get("id").toString());
					nlcnotice.setTitle(jsonNotice.get("title").toString());
					nlcnotice.setSrc(jsonNotice.get("src").toString().indexOf("http://") > -1
							? jsonNotice.get("src").toString() : "http://m.nlc.cn" + jsonNotice.get("src").toString());
					nlcnotice.setPubTime(
							Common.ConvertToDate(detailNotice.get("pub_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setSubTime(
							Common.ConvertToDate(detailNotice.get("sub_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setUpdTime(
							Common.ConvertToDate(detailNotice.get("upd_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setBoard(detailNotice.get("board").toString());
					nlcnotice.setSubPerson(detailNotice.get("sub_person").toString());
					nlcnotice.setStatus("已发布");
					nlcnotice.setBkpbtime(new Date());
					nlcnotice.setContent(detailNotice.get("content").toString().replace("\n", "").replace("\r", "")
							.replace("\n\r", "").replace("'", "\\\'"));
					nlcnotice.setSource("手机门户");
					nlcnotice.setPraisecount(0);
					nlcnotice.setCollectcount(0);
					nlcnotice.setTops(0);
					Date subTime = nlcnotice.getSubTime();
					Date pubTime = nlcnotice.getPubTime();
					if (null == subTime) {
						nlcnotice.setSubTime(new Date());
					}
					if (null == pubTime) {
						nlcnotice.setPubTime(new Date());
					}
					try {
						if (nlcnoticeService.getNlcnoticeByNoticeId(nlcnotice.getNoticeid()) > 0) {
							bcontinue = false;
							break;
						}
						nlcnoticeService.insertNotice(nlcnotice);
					} catch (Exception e) {
						logger.error(jsonNotice.get("id").toString() + "-->" + e.getMessage());
					}
					// {"annexs":[{"name":"民国时期文献整理出版项目实施方案.doc","url":"upload/20160118/63929dc4533b42f580ac6a5ef95a7d99.doc"},{"name":"民国时期文献整理出版项目申报表.doc","url":"upload/20160118/fd09107fb2af4e2f86d5bc2e804210b1.doc"}],
					try {
						JSONArray recordArray = JSONArray.fromObject(detailNotice.get("annexs"));
						if (recordArray.size() > 0) {
							for (int j = 0; j < recordArray.size(); j++) {
								JSONObject annexJson = JSONObject.fromObject(recordArray.get(j));
								Nlcnoticeannex nlcnoticeannex = new Nlcnoticeannex();
								nlcnoticeannex.setNoticeid(jsonNotice.get("id").toString());
								nlcnoticeannex.setTitle(annexJson.getString("name"));
								nlcnoticeannex.setUrl(annexJson.getString("url").indexOf("http://") > -1
										? annexJson.getString("url") : "http://m.nlc.cn/" + annexJson.getString("url"));
								try {
									nlcnoticeannexService.insertNoticeAnnex(nlcnoticeannex);
								} catch (Exception e) {
									logger.info(jsonNotice.get("id").toString() + "-->" + e.getMessage());
								}

							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}

			} else {
				break;
			}
			curpage++;
		}
	}

	/**
	 * 专题task 15
	 */
	public void getNlcSubject() {
		int curpage = 1;
		boolean bcontinue = true;
		while (bcontinue) {
			String strSubject = Common.sendPost("http://m.nlc.cn/nlcm/client/subjectL?page=" + curpage + "&rows=10",
					"");

			JSONArray arrSubject = null;
			if (StringUtils.isNotBlank(strSubject)) {
				arrSubject = JSONArray.fromObject(strSubject);
			} else {
				break;
			}

			if (arrSubject != null && arrSubject.size() > 0) {
				for (int i = 0; i < arrSubject.size(); i++) {
					JSONObject jsonSubject = JSONObject.fromObject(arrSubject.get(i));

					Nlcsubject nlcsubject = new Nlcsubject();
					nlcsubject.setSubjectid(jsonSubject.getString("id"));
					if (jsonSubject.getString("intro").length() > 5) {
						nlcsubject.setIntroduce(
								jsonSubject.getString("intro").replace("\n", "").replace("\r", "").replace("\n\r", ""));
					}
					nlcsubject.setTitle(jsonSubject.getString("title"));
					nlcsubject.setCreatetime(new Date());
					nlcsubject.setSource("手机门户");
					nlcsubject.setStatus("未发布");
					nlcsubject.setPraisecount(0);
					nlcsubject.setCollectcount(0);
					nlcsubject.setSort(10000);
					try {
						if (nlcsubjectService.getNlcSubjectBySubjectId(nlcsubject.getSubjectid()) != null) {
							bcontinue = false;
							break;
						}
						nlcsubjectService.inertNlcsubjectAndCatalogRoot(nlcsubject);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						continue;
					}

					Nlcsubjectcatalog nlcsubcata = new Nlcsubjectcatalog();
					nlcsubcata.setCseq(1);
					nlcsubcata.setSubjectid(jsonSubject.getString("id"));
					nlcsubcata.setCatalogid(UUIDGenerator.getUUID());
					nlcsubcata.setPid("root");
					nlcsubcata.setTitle("简介");
					nlcsubcata.setState("true");
					nlcsubcata.setIsdir("0");
					nlcsubcata.setStatus("已上架");
					String introduce = nlcsubject.getIntroduce();
					if (StringUtils.isNotBlank(introduce)) {
						nlcsubcata.setContent(introduce);
					} else {
						nlcsubcata.setContent("");
					}
					nlcsubjectcatalogService.insertCatalog(nlcsubcata);

					String strCatalog = Common.sendPost(
							"http://m.nlc.cn/nlcm/client/subjectCatalog?id=" + jsonSubject.getString("id"), "");
					JSONArray arrCatalog = null;
					if (StringUtils.isNotBlank(strCatalog)) {
						arrCatalog = JSONArray.fromObject(strCatalog);
					} else {
						continue;
					}

					if (null != arrCatalog && arrCatalog.size() > 0) {
						int k = 2;
						for (int j = 0; j < arrCatalog.size(); j++) {
							JSONObject jsonCatalog = JSONObject.fromObject(arrCatalog.get(j));
							Nlcsubjectcatalog nlcsubjectcatalog = new Nlcsubjectcatalog();
							nlcsubjectcatalog.setCseq(k);
							k++;
							nlcsubjectcatalog.setSubjectid(jsonSubject.getString("id"));
							nlcsubjectcatalog.setCatalogid(jsonCatalog.getString("id"));
							nlcsubjectcatalog.setPid(Common.IsNullOrEmpty(jsonCatalog.getString("pId")) ? "root"
									: jsonCatalog.getString("pId"));

							nlcsubjectcatalog.setTitle(jsonCatalog.getString("name"));
							nlcsubjectcatalog.setState(jsonCatalog.getString("open"));
							String strContent = Common.sendPost(
									"http://m.nlc.cn/nlcm/client/subjectContent?id=" + jsonCatalog.getString("id"), "");

							String con = "";
							if (StringUtils.isNotBlank(strContent)) {
								JSONObject jsonContent = JSONObject.fromObject(strContent);
								con = jsonContent.containsKey("content") ? jsonContent.getString("content") : "";
								con = con.replace("wap.nlc.gov.cn", "wap.nlc.cn");
							}

							nlcsubjectcatalog.setContent(con);
							nlcsubjectcatalog.setIsdir("0");
							nlcsubjectcatalog.setStatus("已上架");
							try {
								nlcsubjectcatalogService.insertCatalog(nlcsubjectcatalog);
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
				}
			} else {
				break;
			}

			curpage++;
		}

		updateSubChildDir();
		backSearch();
	}

	/**
	 * 讲座 task16
	 */
	public void getMobileJzyg() {
		int page = 1;
		while (true) {
			String strJzyg = mobileapi.testRPC("getJzygDetailsList", "http://webservice.nlcm.neusoft.com",
					new Object[] { page, 10 });
			if (StringUtils.isBlank(strJzyg)) {
				break;
			}

			JSONArray arrJzyg = JSONArray.fromObject(strJzyg);
			if (arrJzyg.size() > 0) {
				for (int i = 0; i < arrJzyg.size(); i++) {
					JSONObject jsonJzyg = JSONObject.fromObject(arrJzyg.get(i));
					Nlctrailer nlctrailer = new Nlctrailer();
					nlctrailer.setTrailerid(UUID.randomUUID().toString().replace("-", ""));
					nlctrailer.setSpeakername(jsonJzyg.getString("speaker_name"));
					nlctrailer.setSpeaktime(jsonJzyg.get("time").toString().toLowerCase().replace("monday", "周一")
							.replace("tuesday", "周二").replace("wednesday", "周三").replace("thursday", "周四")
							.replace("friday", "周五").replace("saturday", "周六").replace("sunday", "周日"));
					nlctrailer.setTitle(jsonJzyg.getString("title"));
					nlctrailer.setSpeaker(jsonJzyg.getString("speaker"));
					nlctrailer.setPlace(jsonJzyg.getString("place"));
					nlctrailer.setColumnid(jsonJzyg.getString("column_"));
					nlctrailer.setColumnname(jsonJzyg.getString("column_name"));
					nlctrailer.setGuanqu(jsonJzyg.getString("place").indexOf("国家图书馆古籍馆") != -1 ? "古籍馆" : "总馆");
					nlctrailer.setStarttime(Common.getDateFromString(
							jsonJzyg.get("time").toString().substring(0, jsonJzyg.get("time").toString().indexOf("("))
									+ jsonJzyg.get("time").toString().substring(
											jsonJzyg.get("time").toString().indexOf(")") + 1, jsonJzyg.get("time")
													.toString().indexOf("-"))));
					nlctrailer.setEndtime(Common.getDateFromString(
							jsonJzyg.get("time").toString().substring(0, jsonJzyg.get("time").toString().indexOf("("))
									+ " " + jsonJzyg.get("time").toString()
											.substring(jsonJzyg.get("time").toString().indexOf("-") + 1)));
					nlctrailer.setSource("手机门户");
					nlctrailer.setStatus("已发布");
					nlctrailer.setTime(new Date());
					nlctrailer.setPraisecount(0);
					nlctrailer.setCollectcount(0);
					try {
						if (nlctrailerService.selectByTrailerTitle(nlctrailer.getTitle()) != null) // 如果讲座已经存在就不添加
							continue;
						nlctrailerService.insertNlctrailer(nlctrailer);
					} catch (Exception e) {

					}
				}
			} else {
				break;
			}
			page++;
		}

	}

	/************************************** 推送 ***************************************************/

	/**
	 * 用户讲座提醒，针对单个用户
	 */
	public void trailerAlert() {
		List<Useralert> list = useralertService.getUseralert();
		if (list == null || list.size() == 0)
			return;

		Iterator<Useralert> it = list.iterator();
		while (it.hasNext()) {
			try {
				Useralert useralert = it.next();
				if (StringUtils.isBlank(useralert.getUsername())) {
					continue;
				}

				Nlctrailer nlctrailer = nlctrailerService.selectByTrailerId(useralert.getFileid());
				String status = nlctrailer.getStatus();
				if("未发布".equals(status)) {
					continue;
				}
				
				// 需要七天，三天，一天，当天，提醒
				if (getIntervalDays(getNowDateShort(), useralert.getEndtime()) == 7) {
					Jdpush.pushMessage(1, useralert.getUsername(), "讲座预告：" + useralert.getTitle(), "国家图书馆",
							useralert.getType(), useralert.getFileid());
					continue;
				}
				if (getIntervalDays(getNowDateShort(), useralert.getEndtime()) == 3) {
					Jdpush.pushMessage(1, useralert.getUsername(), "讲座预告：" + useralert.getTitle(), "国家图书馆",
							useralert.getType(), useralert.getFileid());
					continue;
				}
				if (getIntervalDays(getNowDateShort(), useralert.getEndtime()) == 1) {
					Jdpush.pushMessage(1, useralert.getUsername(), "讲座预告：" + useralert.getTitle(), "国家图书馆",
							useralert.getType(), useralert.getFileid());
					continue;
				}
				if (getIntervalDays(getNowDateShort(), useralert.getEndtime()) == 0) {
					Jdpush.pushMessage(1, useralert.getUsername(), "讲座预告：" + useralert.getTitle(), "国家图书馆",
							useralert.getType(), useralert.getFileid());
					continue;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}
	}

	/**
	 * 现在是跳到支付中心 这个其实是欠费金额的提醒，就是外借的时候没还，超过8天会生成滞纳金；基藏的不用管.
	 */
	public void oweWarn() {
		Infosetup infosetup = infosetupService.getInfoBytype(3);
		if (infosetup == null)
			return;

		String pushmethod = infosetup.getPushmethod();
		if (StringUtils.isBlank(pushmethod)) {
			return;
		}

		// 获取用户信息
		List<String> lst = nlcuserService.getCardUser();
		if (null != lst && lst.size() > 0) {
			for (String loginAccount : lst) {
				List<Owe> owelist = Aleph.getFirstOwe(loginAccount);
				if (null != owelist && owelist.size() > 0) {
					for (Owe owe : owelist) {
						if (pushmethod.indexOf("0") > -1) { // 开弹窗
							Jdpush.pushMessage(1, loginAccount, "您的借书 " + owe.getTitle() + ", 因" + owe.getDescription()
									+ "已生成欠款" + owe.getMoney() + "元。", "国家图书馆", "qftx", "");
						}

						if (pushmethod.indexOf("1") > -1) { // 站内信
							Sysmessage sysmessage = new Sysmessage();
							sysmessage.setType(Byte.valueOf("3"));
							sysmessage.setTitle("国家图书馆");
							sysmessage.setMessage("您的借书 " + owe.getTitle() + ", 因" + owe.getDescription() + "已生成欠款"
									+ owe.getMoney() + "元。");
							sysmessage.setUsername(loginAccount);
							sysmessage.setTime(new Date());
							sysmessageService.insertSysmessageObject(sysmessage);
						}
					}
				}
			}
		}
	}

	/**
	 * 图书催还、就是到期提醒，这本书在还之前提醒（并没有逾期）
	 */
	public void remindReturn() {
		Infosetup infosetup = infosetupService.getInfoBytype(2); // 取图书催还的记录
		if (infosetup == null)
			return;

		String pushmethod = infosetup.getPushmethod();
		if (StringUtils.isBlank(pushmethod)) {
			return;
		}

		// 获取用户信息
		List<String> lst = nlcuserService.getCardUser();
		if (lst != null && lst.size() > 0) {
			for (String loginAccount : lst) { // 每个人的循环
				JSON arr = Aleph.borinfo(loginAccount);

				if (!arr.isEmpty()) {
					try {
						JSONArray jsonary = JSONArray.fromObject(arr.toString());

						for (int i = 0; i < jsonary.size(); i++) {// 一个人的多本书
							JSONObject obj = jsonary.getJSONObject(i);
							String title = "";
							String due_date = "";
							if (getIntervalDays(getNowDateShort(),
									Common.ConvertToDate(obj.getString("due_date"), "yyyy/MM/dd")) == 7) {
								title = obj.getString("title");
								due_date = obj.getString("due_date");
							} else if (getIntervalDays(getNowDateShort(),
									Common.ConvertToDate(obj.getString("due_date"), "yyyy/MM/dd")) == 3) {
								title = obj.getString("title");
								due_date = obj.getString("due_date");
							} else if (getIntervalDays(getNowDateShort(),
									Common.ConvertToDate(obj.getString("due_date"), "yyyy/MM/dd")) == 1) {
								title = obj.getString("title");
								due_date = obj.getString("due_date");
							} else if (getIntervalDays(getNowDateShort(),
									Common.ConvertToDate(obj.getString("due_date"), "yyyy/MM/dd")) == 0) {
								title = obj.getString("title");
								due_date = obj.getString("due_date");
							} else {
								continue;
							}

							if (pushmethod.indexOf("0") > -1) { // 开弹窗
								Jdpush.pushMessage(1, loginAccount, "您借的图书 " + title + "，于" + due_date + "即将到期!",
										"国家图书馆", "tsch", "");
							}

							if (pushmethod.indexOf("1") > -1) { // 站内信
								Sysmessage sysmessage = new Sysmessage();
								sysmessage.setType(Byte.valueOf("2"));
								sysmessage.setTitle("国家图书馆");
								sysmessage.setMessage("您借的图书 " + title + "，于" + due_date + "即将到期!");
								sysmessage.setUsername(loginAccount);
								sysmessage.setTime(new Date());
								sysmessageService.insertSysmessageObject(sysmessage);
							}

						}

					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

				} // 完了
			}
		}
	}

	public static int getIntervalDays(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}

		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(getNowDateShort()); //
	 * System.out.println(Common.ConvertToDate("2017/08/14", "yyyy/MM/dd"));
	 * //System.out.println(getIntervalDays(getNowDateShort(),
	 * Common.ConvertToDate("2016-12-10 12:30:00", "yyyy-MM-dd HH:mm:ss"))); }
	 */

	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 昨天的opac推荐热词前100的插入
	 */
	@SuppressWarnings("deprecation")
	public void hotword() {
		String sql = "select count(1) from schehotword where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schehotword已经插入过了");
			return;
		}

		String sql2 = "insert into schehotword(time, hotword, seacount) (select date_sub(curdate(),interval 1 day), hotword, seacount from hotword order by sort asc, seacount desc limit 100)";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量新闻的访问量详情
	 */
	public void ymfwNewsdetail() {
		String sql = "select count(1) from schenewsdetail where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schenewsdetail已经插入过了");
			return;
		}

		String sql2 = "insert into schenewsdetail(uv, pv, detailid, time, title, share) "
				+ " select a.uv, b.pv, a.detailid detailid, date_sub(curdate(),interval 1 day) time, (select title from nlcnews n where n.newsid = a.detailid) title, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'news' and infoid = a.detailid) share "
				+ " from "
				+ " (select count(1) uv, detailid from (select count(1), detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 6 group by clientid, detailid) x group by x.detailid) a, "
				+ " (select count(1) pv, detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 6 group by detailid) b "
				+ " where a.detailid = b.detailid";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量新闻的访问量总量
	 */
	public void ymfwNewsAmount() {
		String sql = "select count(1) from schenewsamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schenewsamount已经插入过了");
			return;
		}

		String sql2 = "insert into schenewsamount(time, pv, uv, share) "
				+ " select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 6) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 6 group by clientid, detailid) x) uv, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'news') share ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量
	 */
	public void ymfm() {
		String sql = "select count(1) from schepageview where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schepageview已经插入过了");
			return;
		}

		String pvsql = "select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate()";
		int pv = jdbcTemplate.queryForInt(pvsql);

		String uvsql = "select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() group by kind, clientid, detailid) x";
		int uv = jdbcTemplate.queryForInt(uvsql);

		String resultsql = "insert into schepageview(time, pv, uv) values(date_sub(curdate(),interval 1 day), " + pv
				+ ", " + uv + ")";
		jdbcTemplate.execute(resultsql);
	}

	/**
	 * 昨天的页面访问量opac检索的访问量
	 */
	public void ymfwOpacAmount() {
		String sql = "select count(1) from scheopacamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheopacamount已经插入过了");
			return;
		}

		String sql2 = "insert into scheopacamount(time, pv, uv) select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 1) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 1 group by clientid, detailid) x	) uv ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的olcc推荐热词前100的插入
	 */
	@SuppressWarnings("deprecation")
	public void olccHotword() {
		String sql = "select count(1) from scheolcchotword where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheolcchotword已经插入过了");
			return;
		}

		String sql2 = "insert into scheolcchotword(time, hotword, seacount) (select date_sub(curdate(),interval 1 day), hotword, seacount from olcchotword order by sort asc, seacount desc limit 100)";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量olcc检索的访问量
	 */
	public void ymfwOlccAmount() {
		String sql = "select count(1) from scheolccamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheolccamount已经插入过了");
			return;
		}

		String sql2 = "insert into scheolccamount(time, pv, uv) select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 2) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 2 group by clientid, detailid) x	) uv ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量站内搜索的访问量
	 */
	public void ymfwZnssAmount() {
		String sql = "select count(1) from scheznssamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheznssamount已经插入过了");
			return;
		}

		String sql2 = "insert into scheznssamount(time, pv, uv) select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 3) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 3 group by clientid, detailid) x	) uv ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量扫一扫的访问量
	 */
	public void ymfwSysAmount() {
		String sql = "select count(1) from schesysamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schesysamount已经插入过了");
			return;
		}

		String sql2 = "insert into schesysamount(time, pv, uv) select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 4) pv, "
				+ " (select count(1) from (select clientid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 4 group by clientid) x	) uv ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量广告位的总访问量
	 */
	public void ymfwAdsAmount() {
		String sql = "select count(1) from scheadsamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheadsamount已经插入过了");
			return;
		}

		String sql2 = "insert into scheadsamount(time, pv, uv) select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 5) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 5 group by clientid, detailid) x	) uv ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量广告位的访问量详情
	 */
	public void ymfwGgwdetail() {
		String sql = "select count(1) from scheadsdetail where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheadsdetail已经插入过了");
			return;
		}

		String sql2 = "insert into scheadsdetail(time, pv, uv, adsid, title) select date_sub(curdate(),interval 1 day) time, a.pv, b.uv, a.detailid adsid, (select name from nlcads n where n.adsid = a.detailid) title "
				+ " from (select count(1) pv, detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 5 group by detailid) a, "
				+ " (select count(1) uv, detailid from (select count(1), detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 5 group by clientid, detailid) x group by x.detailid) b "
				+ " where a.detailid = b.detailid";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量公告的访问量总量
	 */
	public void ymfwNotieAmount() {
		String sql = "select count(1) from schenoticeamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schenoticeamount已经插入过了");
			return;
		}

		String sql2 = "insert into schenoticeamount(time, pv, uv, share) "
				+ " select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 7) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 7 group by clientid, detailid) x) uv, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'notice') share ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量公告的访问量详情
	 */
	public void ymfwNoticedetail() {
		String sql = "select count(1) from schenoticedetail where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schenoticedetail已经插入过了");
			return;
		}

		String sql2 = "insert into schenoticedetail(uv, pv, detailid, time, title, share) "
				+ " select a.uv, b.pv, a.detailid detailid, date_sub(curdate(),interval 1 day) time, (select title from nlcnotice n where n.noticeid = a.detailid) title, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'notice' and infoid = a.detailid) share "
				+ " from "
				+ " (select count(1) uv, detailid from (select count(1), detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 7 group by clientid, detailid) x group by x.detailid) a, "
				+ " (select count(1) pv, detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 7 group by detailid) b "
				+ " where a.detailid = b.detailid";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量讲座预告的访问量总量
	 */
	public void ymfwTrailerAmount() {
		String sql = "select count(1) from schetraileramount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schetraileramount已经插入过了");
			return;
		}

		String sql2 = "insert into schetraileramount(time, pv, uv, share) "
				+ " select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 8) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 8 group by clientid, detailid) x) uv, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'trailer') share ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量讲座预告的访问量详情
	 */
	public void ymfwTrailerdetail() {
		String sql = "select count(1) from schetrailerdetail where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schetrailerdetail已经插入过了");
			return;
		}

		String sql2 = "insert into schetrailerdetail(uv, pv, detailid, time, title, share) "
				+ " select a.uv, b.pv, a.detailid detailid, date_sub(curdate(),interval 1 day) time, (select title from nlctrailer n where n.trailerid = a.detailid) title, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'trailer' and infoid = a.detailid) share "
				+ " from "
				+ " (select count(1) uv, detailid from (select count(1), detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 8 group by clientid, detailid) x group by x.detailid) a, "
				+ " (select count(1) pv, detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 8 group by detailid) b "
				+ " where a.detailid = b.detailid";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量专题的访问量总量
	 */
	public void ymfwSubjectAmount() {
		String sql = "select count(1) from schesubjectamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schesubjectamount已经插入过了");
			return;
		}

		String sql2 = "insert into schesubjectamount(time, pv, uv, share) "
				+ " select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 10) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 10 group by clientid, detailid) x) uv, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'subject') share ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量专题的访问量详情
	 */
	public void ymfwSubjectdetail() {
		String sql = "select count(1) from schesubjectdetail where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schesubjectdetail已经插入过了");
			return;
		}

		String sql2 = "insert into schesubjectdetail(uv, pv, detailid, time, title, share) "
				+ " select a.uv, b.pv, a.detailid detailid, date_sub(curdate(),interval 1 day) time, (select title from nlcsubject n where n.subjectid = a.detailid) title, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'subject' and infoid = a.detailid) share "
				+ " from "
				+ " (select count(1) uv, detailid from (select count(1), detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 10 group by clientid, detailid) x group by x.detailid) a, "
				+ " (select count(1) pv, detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 10 group by detailid) b "
				+ " where a.detailid = b.detailid";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量书架的访问量
	 */
	public void ymfwSjAmount() {
		String sql = "select count(1) from schesjamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schesjamount已经插入过了");
			return;
		}

		String sql2 = "insert into schesjamount(time, pv, uv) select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and (kind = 11 or kind = 12)) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and (kind = 11 or kind = 12) group by kind, clientid, detailid) x	) uv ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量文津的访问量总量
	 */
	public void ymfwWjAmount() {
		String sql = "select count(1) from schewjamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schewjamount已经插入过了");
			return;
		}

		String sql2 = "insert into schewjamount(time, pv, uv, share, audio) "
				+ " select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 9) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 9 group by clientid, detailid) x) uv, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'wenjin') share, "
				+ " (select count(1) from audioinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate()) audio ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量文津的访问量详情
	 */
	public void ymfwWjdetail() {
		String sql = "select count(1) from schewjdetail where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schewjdetail已经插入过了");
			return;
		}

		String sql2 = "insert into schewjdetail(uv, pv, detailid, time, title, share, audio) "
				+ " select a.uv, b.pv, a.detailid detailid, date_sub(curdate(),interval 1 day) time, "
				+ " (select shiju from wjreader n where n.wjdate = a.detailid) title, "
				+ " (select count(1) from shareinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and type = 'wenjin' and infoid = a.detailid) share,"
				+ " (select count(1) from audioinfo where time >= date_sub(curdate(),interval 1 day) and time < curdate() and uid = a.detailid) audio "
				+ " from "
				+ " (select count(1) uv, detailid from (select count(1), detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 9 group by clientid, detailid) x group by x.detailid) a, "
				+ " (select count(1) pv, detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 9 group by detailid) b "
				+ " where a.detailid = b.detailid";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量个人的访问量总量
	 */
	public void ymfwPersonAmount() {
		String sql = "select count(1) from schepersonamount where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schepersonamount已经插入过了");
			return;
		}

		String sql2 = "insert into schepersonamount(time, pv, uv) select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 13) pv, "
				+ " (select count(1) from (select detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 13 group by clientid, detailid) x ) uv ";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的页面访问量个人的访问量详情
	 */
	public void ymfwPersonDetail() {
		String sql = "select count(1) from schepersondetail where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("schepersondetail已经插入过了");
			return;
		}

		String sql2 = "insert into schepersondetail(time, pv, uv, detailid) "
				+ " select date_sub(curdate(),interval 1 day) time, a.pv, b.uv, a.detailid " + " FROM "
				+ " (select count(1) pv, detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 13 group by detailid) a, "
				+ " (select count(1) uv, detailid from (select count(1), detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and kind = 13 group by clientid, detailid) x group by x.detailid) b "
				+ " where a.detailid = b.detailid";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的用户画像数据
	 */
	public void userPaint() {
		String sql = "select count(1) from scheuserpaint where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheuserpaint已经插入过了");
			return;
		}

		String sql2 = "insert into scheuserpaint(time, username, cs, sc, pv, uv, ds) "
				+ " select date_sub(curdate(),interval 1 day) time, x.username, x.cs, rc.sc, rc.pv, xx.uv, ux.ds FROM "
				+ " (select username, count(1) cs from nlcuserloginlog where time >= date_sub(curdate(),interval 1 day) and time < curdate() group by username) x "
				+ " left outer JOIN "
				+ " (select username, sum(waittime) sc, count(1) pv from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() group by username) rc "
				+ " on x.username=rc.username " + " left outer JOIN " + " (select username, count(1) uv from "
				+ " (select username, clientid, kind, detailid from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() group by username, clientid, kind, detailid) xs "
				+ " group by xs.username) xx " + " ON x.username=xx.username " + " left outer JOIN ( "
				+ " select username, sum(s) ds FROM ( "
				+ " select username, count(1) s from downlistenbook where time >= date_sub(curdate(),interval 1 day) and time < curdate() group by username "
				+ " union all select username, count(1) s from downreadtx where time >= date_sub(curdate(),interval 1 day) and time < curdate() group by username "
				+ " ) x group by username " + " ) ux on x.username=ux.username";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 昨天的app统计
	 */
	public void appsta() {
		String sql = "select count(1) from scheapp where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheapp已经插入过了");
			return;
		}

		String sql2 = "insert into scheapp(time, iosnew, andnew, iosqd, andqd, iosxzl, andxzl, iosgx, andgx, iossc, andsc) "
				+ " select date_sub(curdate(),interval 1 day) time, "
				+ " (select count(1) from nlcuser where inserttime >= date_sub(curdate(),interval 1 day) and inserttime < curdate() and baseos like 'i%') iosnew, "
				+ " (select count(1) from nlcuser where inserttime >= date_sub(curdate(),interval 1 day) and inserttime < curdate() and baseos like 'and%') andnew, "
				+ " (select count(1) from appstart where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'i%') iosqd, "
				+ " (select count(1) from appstart where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'and%') andqd, "
				+ " (select count(1) from appinstall where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'i%') iosxzl, "
				+ " (select count(1) from appinstall where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'and%') andxzl, "
				+ " (select count(1) from apprenew where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'i%') iosgx, "
				+ " (select count(1) from apprenew where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'and%') andgx, "
				+ " (select sum(waittime) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'i%') iossc, "
				+ " (select sum(waittime) from accesslog where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'and%') andsc";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 版本安装量
	 */
	public void appedition() {
		String sql = "select count(1) from scheappedition where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheappedition已经插入过了");
			return;
		}

		String sql2 = "insert into scheappedition(time, version, num, type) "
				+ " SELECT date_sub(curdate(),interval 1 day) time, version, count(1) num, 'android' type FROM `appinstall` where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'and%' group by version "
				+ " union all "
				+ " SELECT date_sub(curdate(),interval 1 day) time, version, count(1) num, 'iphone' type FROM `appinstall` where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'i%' group by version";
		jdbcTemplate.execute(sql2);
	}

	/**
	 * 装机详情 昨天的，按手机型号统计
	 */
	public void model() {
		String sql = "select count(1) from scheappmodel where time = date_sub(curdate(),interval 1 day)";
		int result = jdbcTemplate.queryForInt(sql);
		if (result > 0) {
			logger.info("scheappmodel已经插入过了");
			return;
		}

		String sql2 = "insert into scheappmodel(time, model, addInstallNum, startNum) "
				+ " select date_sub(curdate(),interval 1 day) time, b.model, a.addInstallNum, b.startNum " + " from "
				+ " (SELECT client model, count(1) addInstallNum FROM `appinstall` "
				+ " where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'i%' "
				+ " group by client) a " + " right outer join "
				+ " (SELECT client model, count(1) startNum FROM `appstart` "
				+ " where time >= date_sub(curdate(),interval 1 day) and time < curdate() and baseos like 'i%' "
				+ " group by client) b " + " on a.model = b.model";
		jdbcTemplate.execute(sql2);
	}

	// 修改专题的isdir状态
	public void updateSubChildDir() {
		List<Nlcsubjectcatalog> catalist = nlcsubjectcatalogService.getAllWithoutRoot();
		if (null != catalist && catalist.size() > 0) {
			for (Nlcsubjectcatalog nlcsubjectcatalog : catalist) {
				nlcsubjectcatalogService.judgeIsDir(nlcsubjectcatalog.getSubjectid(), nlcsubjectcatalog.getCatalogid());
			}
		}
	}

	// 专题leaforder
	public void backSearch() {
		nlcsubjectcatalogService.backSearch();
	}

	/**
	 * 逾期没还，属于 违规，就是这本书到期了还没有还，要提醒他们
	 */
	public void overReturn() {
		Infosetup infosetup = infosetupService.getInfoBytype(3); // 消息通知设置，新增违约记录的状态
		if (infosetup == null)
			return;

		String pushmethod = infosetup.getPushmethod();
		if (StringUtils.isBlank(pushmethod)) {
			return;
		}

		// 获取用户信息
		List<String> lst = nlcuserService.getCardUser();
		if (lst != null && lst.size() > 0) {
			for (String loginAccount : lst) { // 每个人的循环
				JSON arr = Aleph.borinfo(loginAccount);

				if (!arr.isEmpty()) {
					try {
						JSONArray jsonary = JSONArray.fromObject(arr.toString());

						for (int i = 0; i < jsonary.size(); i++) {// 一个人的多本书
							JSONObject obj = jsonary.getJSONObject(i);
							String title = "";
							String due_date = "";
							if (getIntervalDays(getNowDateShort(),
									Common.ConvertToDate(obj.getString("due_date"), "yyyy/MM/dd")) == -1) {
								title = obj.getString("title");
								due_date = obj.getString("due_date");
							} else if (getIntervalDays(getNowDateShort(),
									Common.ConvertToDate(obj.getString("due_date"), "yyyy/MM/dd")) == -30) {
								title = obj.getString("title");
								due_date = obj.getString("due_date");
							} else if (getIntervalDays(getNowDateShort(),
									Common.ConvertToDate(obj.getString("due_date"), "yyyy/MM/dd")) == -90) {
								title = obj.getString("title");
								due_date = obj.getString("due_date");
							} else {
								continue;
							}

							if (pushmethod.indexOf("0") > -1) { // 开弹窗
								Jdpush.pushMessage(1, loginAccount, "您借的图书 " + title + "，于" + due_date + "已经到期，请及时处理!",
										"国家图书馆", "tout", "");
							}

							if (pushmethod.indexOf("1") > -1) { // 站内信
								Sysmessage sysmessage = new Sysmessage();
								sysmessage.setType(Byte.valueOf("3"));
								sysmessage.setTitle("国家图书馆");
								sysmessage.setMessage("您借的图书 " + title + "，于" + due_date + "已经到期，请及时处理!");
								sysmessage.setUsername(loginAccount);
								sysmessage.setTime(new Date());
								sysmessageService.insertSysmessageObject(sysmessage);
							}

						}

					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

				} // 完了
			}
		}
	}

	/**
	 * 预约待取
	 */
	public void appointment() {

	}
	
	//每天清空userinfoqrcode 用户信息二维码表
	public void clearUserinfoqrcode() {
		String sql = "truncate table `userinfoqrcode`";
		jdbcTemplate.execute(sql);
	}

}
