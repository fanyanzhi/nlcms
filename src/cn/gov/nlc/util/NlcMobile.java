package cn.gov.nlc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gov.nlc.mapper.NlcnoticeMapper;
import cn.gov.nlc.mapper.WjreaderMapper;
import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.Nlcnoticeannex;
import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.Nlcsubjectcatalog;
import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.service.NlcnewsService;
import cn.gov.nlc.service.NlcnoticeService;
import cn.gov.nlc.service.NlcnoticeannexService;
import cn.gov.nlc.service.NlcsubjectService;
import cn.gov.nlc.service.NlcsubjectcatalogService;
import cn.gov.nlc.service.NlctrailerService;
import cn.gov.nlc.service.WjreaderService;
import cn.gov.nlc.test.mobileapi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class NlcMobile {
	@Autowired
	private NlcnewsService nlcnewsService;

	@Autowired
	private NlcnoticeService nlcnoticeService;

	@Autowired
	private NlcnoticeannexService nlcnoticeannexService;

	@Autowired
	private NlctrailerService nlctrailerService;

	@Autowired
	private NlcsubjectService nlcsubjectService;

	@Autowired
	private NlcsubjectcatalogService nlcsubjectcatalogService;

	@Autowired
	private WjreaderService wjreaderService;

	private static Logger logger = Logger.getLogger(NlcMobile.class);

	public static void main(String[] args) {
		String[] riqi = "2016.03.27".split("\\.");
		System.out.println(riqi.length);
		System.out.println("2016.03.27".indexOf("."));
		// System.out.println(Common.ConvertToDate("2016-07-11 15:11",
		// "yyyy-MM-dd HH:mm"));// .ConvertToDateTime("2016-07-11
		// 15:11",
		// "yyyy-MM-dd
		// HH:mm"));
		// getNlcMobileNotice();
		// getMobileJzyg();

		/*
		 * System.out .println(string2Date("2016年09月04日() 14:30-15:30"
		 * .substring(0, "2016年09月04日() 14:30-15:30".indexOf("(")) +
		 * "2016年09月04日() 14:30-15:30".substring("2016年09月04日() 14:30-15:30"
		 * .indexOf(")") + 1, "2016年09月04日() 14:30-15:30".indexOf("-"))));
		 */
		// Common.ConvertToDate(new Date(), "yyyy-MM-dd");

		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("今天的日期：" + df.format(d));
		System.out.println("两天前的日期：" + df.format(new Date(d.getTime() - (long) 2 * 24 * 60 * 60 * 1000)));
		System.out.println("三天后的日期：" + df.format(new Date(d.getTime() + (long) 3 * 24 * 60 * 60 * 1000)));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date time = null;

		try {
			time = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, 3);
		System.out.println(now.getTime());
		// getNlcSubjectTest();
		// getSubjectCatalog();
		// getShowList();
		// getShowCatalog();
		// getShowCatalogContent();
//		getTodayWenJin();
		// getWenJinCatalog();
		// getWenJinContent("ff808081519e421901519edfb677013e");
		// getSubjectContent();
		// criteria.andStarttimeBetween(new Date(), now.getTime());
	}

	/**
	 * 新闻
	 */
	public void getNlcMobileNews() {
		int curpage = 1;
		int count = 1;
		while (true) {
			String strNews = mobileapi.testRPC("getNewsTitles", "http://webservice.nlcm.neusoft.com",
					new Object[] { curpage, 10 });
			JSONArray arrNews = JSONArray.fromObject(strNews);
			if (arrNews.size() > 0) {
				for (int i = 0; i < arrNews.size(); i++) {
					JSONObject jsonNews = JSONObject.fromObject(arrNews.get(i));
					JSONObject detailNews = JSONObject.fromObject(mobileapi.testRPC("getNewsDetails",
							"http://webservice.nlcm.neusoft.com", new Object[] { jsonNews.get("id") }));
					/*
					 * System.out.println((count++) + "-->" +
					 * jsonNews.get("title").toString() + jsonNews.get("id") +
					 * jsonNews.get("pub_time") + detailNews.get("content"));
					 */
					Nlcnews nlcnews = new Nlcnews();
					nlcnews.setNewsid(jsonNews.get("id").toString());
					nlcnews.setTitle(jsonNews.get("title").toString());
					nlcnews.setSrc(jsonNews.get("src").toString().indexOf("http://")>-1?jsonNews.get("src").toString():"http://m.nlc.cn"+jsonNews.get("src").toString());
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
					try {
						nlcnews.setTops(0);
						nlcnewsService.insertNews(nlcnews);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						logger.debug("nlcnews-->" + nlcnews.getNewsid() + "-->" + nlcnews.getTitle() + e.getMessage());
					}
				}
			} else {
				break;
			}
			curpage++;
		}
	}

	/*
	 * 专题
	 */
	public void getNlcSubject() {
		int curpage = 1;
		while (true) {
			String strSubject = Common.sendPost("http://m.nlc.cn/nlcm/client/subjectL?page=" + curpage + "&rows=10",
					"");
			System.out.println(strSubject);
			JSONArray arrSubject = JSONArray.fromObject(strSubject);

			if (arrSubject.size() > 0) {
				for (int i = 0; i < arrSubject.size(); i++) {
					JSONObject jsonSubject = JSONObject.fromObject(arrSubject.get(i));

					Nlcsubject nlcsubject = new Nlcsubject();
					nlcsubject.setSubjectid(jsonSubject.getString("id"));
					nlcsubject.setIntroduce(
							jsonSubject.getString("intro").replace("\n", "").replace("\r", "").replace("\n\r", ""));
					nlcsubject.setTitle(jsonSubject.getString("title"));
					nlcsubject.setCreatetime(new Date());
					nlcsubject.setSource("手机门户");
					nlcsubject.setStatus("未发布");
					nlcsubject.setPraisecount(0);
					nlcsubject.setCollectcount(0);

					try {
						nlcsubjectService.inertNlcsubjectAndCatalogRoot(nlcsubject);
					} catch (Exception e) {
						System.out.println(e.getStackTrace());
					}

					String strCatalog = Common.sendPost(
							"http://m.nlc.cn/nlcm/client/subjectCatalog?id=" + jsonSubject.getString("id"), "");
					JSONArray arrCatalog = JSONArray.fromObject(strCatalog);
					if (arrCatalog.size() > 0) {
						for (int j = 0; j < arrCatalog.size(); j++) {
							JSONObject jsonCatalog = JSONObject.fromObject(arrCatalog.get(j));
							Nlcsubjectcatalog nlcsubjectcatalog = new Nlcsubjectcatalog();
							nlcsubjectcatalog.setSubjectid(jsonSubject.getString("id"));
							nlcsubjectcatalog.setCatalogid(jsonCatalog.getString("id"));
							nlcsubjectcatalog.setPid(Common.IsNullOrEmpty(jsonCatalog.getString("pId")) ? "root"
									: jsonCatalog.getString("pId"));

							nlcsubjectcatalog.setTitle(jsonCatalog.getString("name"));
							nlcsubjectcatalog.setState(jsonCatalog.getString("open"));
							nlcsubjectcatalog.setStatus("已上架");
							String strContent = Common.sendPost(
									"http://m.nlc.cn/nlcm/client/subjectContent?id=" + jsonCatalog.getString("id"), "");
							JSONObject jsonContent = JSONObject.fromObject(strContent);
							nlcsubjectcatalog.setContent(
									jsonContent.containsKey("content") ? jsonContent.getString("content") : "");
							try {
								nlcsubjectcatalogService.insertCatalog(nlcsubjectcatalog);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

						}

					}

					/*
					 * System.out.println((count++) + "-->" +
					 * jsonNews.get("title").toString() + jsonNews.get("id") +
					 * jsonNews.get("pub_time") + detailNews.get("content"));
					 */

					/*
					 * nlcnews.setNewsid(jsonNews.get("id").toString());
					 * nlcnews.setTitle(jsonNews.get("title").toString());
					 * nlcnews.setSrc(jsonNews.get("src").toString());
					 * nlcnews.setPubTime(Common.ConvertToDate(jsonNews.get(
					 * "pub_time").toString(), "yyyy-MM-dd HH:mm"));
					 * nlcnews.setSource("手机门户"); nlcnews.setStatus("已发布");
					 * nlcnews.setSubPerson(detailNews.get("sub_person").
					 * toString());
					 * nlcnews.setContent(detailNews.get("content").toString());
					 * nlcnews.setSubTime(new Date());
					 * nlcsubjectService..insertNews(nlcnews);
					 */
				}
			} else {
				break;
			}
			curpage++;
		}

	}

	public static void getNlcSubjectTest() {
		String strSubject = Common.sendPost("http://m.nlc.cn/nlcm/client/subjectL?page=1&rows=10", "");
		System.out.println(strSubject);
	}

	/**
	 * 专题目录
	 */
	public static void getSubjectCatalog() {
		System.out.println(
				Common.sendPost("http://m.nlc.cn/nlcm/client/subjectCatalog?id=ff808081515612b00151563ebd040017", ""));
	}

	public void getTodayWenJin() {
		String wenjin = Common.sendGet("http://m.nlc.cn/client/subjectContent?id=ff80808151d363540151d6df1d38020d",
				null);
		System.out.println(wenjin);
		wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-shiju-yl\\\"><p>")
				+ "<div class=\\\"wjjdsd-shiju-yl\\\"><p>".length());
		String shiju = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(shiju);
		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String sjsource = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(sjsource);
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
		// System.out.println(geyan);
		wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
				+ "<p style=\\\"text-align: right;\\\">".length() + 2);
		String gysource = wenjin.substring(0, wenjin.indexOf("</p>"));
		// System.out.println(gysource);
		wenjin = wenjin.substring(
				wenjin.indexOf("<div class=\\\"geyan-ywcont\\\">") + "<div class=\\\"geyan-ywcont\\\">".length());
		String gyyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));
		Calendar cal = Calendar.getInstance();
		System.out.println((cal.get(Calendar.MONTH) + 1)+" ");
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
		//Calendar cal = Calendar.getInstance();
		wjreader.setWjmonth(cal.get(Calendar.MONTH + 1) + "");
		wjreader.setWjyear(cal.get(Calendar.YEAR) + "");

		wjreaderService.insertWjreader(wjreader);

	}

	public void getWenJinCatalog() {
		System.out.println(
				Common.sendPost("http://m.nlc.cn/nlcm/client/subjectCatalog?id=ff808081515612b00151563ebd040017", ""));
	}

	public void getWenJinContent(String id) {
		int i = 365;
		String wenjin = Common.sendGet("http://m.nlc.cn/nlcm/client/subjectContent?id="+id,
				null);
		System.out.println(wenjin);
		while (i > 0) {
			try {
				wenjin = wenjin.substring(wenjin.indexOf("【") + 1);
				String riqi = wenjin.substring(0, wenjin.indexOf("】"));
				wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"wjjdsd-shiju-yl\\\"><p>")
						+ "<div class=\\\"wjjdsd-shiju-yl\\\"><p>".length());
				String shiju = wenjin.substring(0, wenjin.indexOf("</p>"));
				wenjin = wenjin.substring(wenjin.indexOf("<p style=\\\"text-align: right;\\\">")
						+ "<p style=\\\"text-align: right;\\\">".length() + 2);
				String sjsource = wenjin.substring(0, wenjin.indexOf("</p>"));
				wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"shiju-ywcont\\\">")
						+ "<div class=\\\"shiju-ywcont\\\">".length());
				String sjyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));
				int idiv = 0;
				String quanshi = "";
				if (wenjin.indexOf("shiju-qscont") > 0 && wenjin.indexOf("shiju-qscont") < 200) {
					wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"shiju-qscont\\\">")
							+ "<div class=\\\"shiju-qscont\\\">".length());
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
				wenjin = wenjin.substring(wenjin.indexOf("<div class=\\\"geyan-ywcont\\\">")
						+ "<div class=\\\"geyan-ywcont\\\">".length());
				String gyyiwen = wenjin.substring(0, wenjin.indexOf("</div>"));
				Wjreader wjreader = new Wjreader();
				wjreader.setPid(id);
				wjreader.setShiju(shiju);
				wjreader.setSjsource(sjsource);
				wjreader.setSjyiwen(sjyiwen);
				wjreader.setQuanshi(quanshi);
				wjreader.setSjurl(sjurl);
				wjreader.setGeyan(geyan);
				wjreader.setGysource(gysource);
				wjreader.setGyyiwen(gyyiwen);

				// wjreader.setPid(df1);
				try {
					wjreader.setWjmonth(riqi.split("\\.")[1]);
					wjreader.setWjyear(riqi.split("\\.")[0]);
					wjreader.setWjdate(Common.ConvertToDate(riqi.replace(".", "-") + " 00:00:00", "yyyy-MM-dd"));

					wjreaderService.insertWjreader(wjreader);
				} catch (Exception e) {
					System.out.println(quanshi);
					System.out.println(gyyiwen);
				}

				// System.out.println(wjreader.getShiju());
				i--;
			} catch (Exception e) {
				break;
			}
		}

	}

	public static void getSubjectContent() {
		System.out.println(
				Common.sendGet("http://m.nlc.cn/nlcm/client/subjectContent?id=4aeafd24564946c30156a1788154298a", null));
	}

	/**
	 * 
	 */
	public static void getShowList() {
		System.out.println(Common.sendGet("http://m.nlc.cn/nlcm/client/showL?page=1&rows=10", null));
	}

	public static void getShowCatalog() {
		System.out.println(
				Common.sendPost("http://m.nlc.cn/nlcm/client/showCatalog?id=ff8080815060667701506538ff3b006b", ""));
	}

	public static void getShowCatalogContent() {
		System.out.println(
				Common.sendGet("http://m.nlc.cn/nlcm/client/showContent?id=ff808081506066770150653937a5006d", null));
	}

	/**
	 * 公告
	 */
	public void getNlcMobileNotice() {
		int curpage = 1;
		while (true) {
			String strNotice = mobileapi.testRPC("getNoticeTitles", "http://webservice.nlcm.neusoft.com",
					new Object[] { curpage, 10 });
			JSONArray arrNotice = JSONArray.fromObject(strNotice);
			if (arrNotice.size() > 0) {
				for (int i = 0; i < arrNotice.size(); i++) {
					JSONObject jsonNotice = JSONObject.fromObject(arrNotice.get(i));
					// System.out.println(jsonNotice.toString());
					JSONObject detailNotice = JSONObject.fromObject(mobileapi.testRPC("getNoticeDetails",
							"http://webservice.nlcm.neusoft.com", new Object[] { jsonNotice.get("id") }));
					// System.out.println(detailNotice.toString());
					Nlcnotice nlcnotice = new Nlcnotice();
					nlcnotice.setNoticeid(jsonNotice.get("id").toString());
					// nlcnotice.setPubTime(jsonNotice.get("pub_time").toString());
					nlcnotice.setTitle(jsonNotice.get("title").toString());
					nlcnotice.setSrc(jsonNotice.get("src").toString().indexOf("http://")>-1?jsonNotice.get("src").toString():"http://m.nlc.cn"+jsonNotice.get("src").toString());
					nlcnotice.setPubTime(
							Common.ConvertToDate(detailNotice.get("pub_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setSubTime(
							Common.ConvertToDate(detailNotice.get("sub_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setUpdTime(
							Common.ConvertToDate(detailNotice.get("upd_time").toString(), "yyyy-MM-dd HH:mm"));
					nlcnotice.setBoard(detailNotice.get("board").toString());
					nlcnotice.setSubPerson(detailNotice.get("sub_person").toString());
					nlcnotice.setStatus("已发布");
					nlcnotice.setContent(detailNotice.get("content").toString().replace("\n", "").replace("\r", "")
							.replace("\n\r", "").replace("'", "\\\'"));
					nlcnotice.setSource("手机门户");
					nlcnotice.setPraisecount(0);
					nlcnotice.setCollectcount(0);
					nlcnotice.setTops(0);
					Date updTime = nlcnotice.getUpdTime();
					Date pubTime = nlcnotice.getPubTime();
					Date subTime = nlcnotice.getSubTime();
					if(null == subTime) {
						nlcnotice.setSubTime(new Date());
					}
					if(null == pubTime) {
						nlcnotice.setPubTime(new Date());
					}
					if(null == updTime) {
						nlcnotice.setUpdTime(new Date());
					}
					try{
					nlcnoticeService.insertNotice(nlcnotice);
					}catch(Exception e){
						logger.info(jsonNotice.get("id").toString()+"-->"+e.getMessage());
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
								nlcnoticeannex.setUrl(annexJson.getString("url").indexOf("http://")>-1?annexJson.getString("url"):"http://m.nlc.cn/"+annexJson.getString("url"));
							try{
								nlcnoticeannexService.insertNoticeAnnex(nlcnoticeannex);
							}catch(Exception e){
								logger.info(jsonNotice.get("id").toString()+"-->"+e.getMessage());
							}
								// ArrayObject ab =
								// detailNotice.get("annexs").toString();
							}
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}

			} else {
				break;
			}
			curpage++;
		}
	}

	public static Date string2Date(String ymd) {
		Date date3 = null;
		try {
			DateFormat df3 = new SimpleDateFormat("yyyy年MM月dd日 hh:mm", Locale.CHINA); // 产生一个指定国家指定长度的日期格式，长度不同，显示的日期完整性也不同
			date3 = df3.parse(ymd);
		} catch (Exception e) {
			System.out.println(e);
		}
		return date3;
	}

	public void getMobileJzyg() {
		int page = 1;
		while (true) {
			String strJzyg = mobileapi.testRPC("getJzygDetailsList", "http://webservice.nlcm.neusoft.com",
					new Object[] { page, 10 });
			JSONArray arrJzyg = JSONArray.fromObject(strJzyg);
			if (arrJzyg.size() > 0) {
				for (int i = 0; i < arrJzyg.size(); i++) {
					JSONObject jsonJzyg = JSONObject.fromObject(arrJzyg.get(i));
					System.out.println(jsonJzyg.toString());
					Nlctrailer nlctrailer = new Nlctrailer();
					nlctrailer.setTrailerid(UUID.randomUUID().toString().replace("-", ""));
					nlctrailer.setSpeakername(jsonJzyg.getString("speaker_name"));
					nlctrailer.setSpeaktime(jsonJzyg.get("time").toString());
					nlctrailer.setTitle(jsonJzyg.getString("title"));
					nlctrailer.setSpeaker(jsonJzyg.getString("speaker"));
					nlctrailer.setPlace(jsonJzyg.getString("place"));
					nlctrailer.setColumnid(jsonJzyg.getString("column_"));
					nlctrailer.setColumnname(jsonJzyg.getString("column_name"));
					nlctrailer.setGuanqu(jsonJzyg.getString("place").indexOf("国家图书馆古籍馆") != -1 ? "古籍馆" : "总馆");
					nlctrailer.setStarttime(Common.getDateFromString(
							jsonJzyg.get("time").toString().substring(0, jsonJzyg.get("time").toString().indexOf("("))
									+ jsonJzyg.get("time").toString().substring(
											jsonJzyg.get("time").toString().indexOf(")") + 1,
											jsonJzyg.get("time").toString().indexOf("-"))));
					nlctrailer.setEndtime(Common.getDateFromString(
							jsonJzyg.get("time").toString().substring(0, jsonJzyg.get("time").toString().indexOf("("))
									+ " " + jsonJzyg.get("time").toString()
											.substring(jsonJzyg.get("time").toString().indexOf("-") + 1)));
					nlctrailer.setSource("手机门户");
					nlctrailer.setTime(new Date());
					nlctrailer.setPraisecount(0);
					nlctrailer.setCollectcount(0);
					try {
						nlctrailerService.insertNlctrailer(nlctrailer);
					} catch (Exception e) {
						System.out.println("fff-->" + e.getMessage());
					}
				}
			} else {
				break;
			}
			page++;
		}

	}

	/**
	 * 书刊推介
	 */
	public void getNlcMobileBook() {
		String strBook = mobileapi.testRPC("getBookTitles", "http://webservice.nlcm.neusoft.com",
				new Object[] { 1, 10 });
		JSONArray arrBook = JSONArray.fromObject(strBook);
		if (arrBook.size() > 0) {
			for (int i = 0; i < arrBook.size(); i++) {
				JSONObject jsonBook = JSONObject.fromObject(arrBook.get(i));
				JSONObject detailBook = JSONObject.fromObject(mobileapi.testRPC("getNoticeDetails",
						"http://webservice.nlcm.neusoft.com", new Object[] { jsonBook.get("id") }));
				System.out.println(jsonBook.get("title").toString() + jsonBook.get("id") + jsonBook.get("pub_time")
						+ detailBook.get("content"));

			}
		}
	}
}
